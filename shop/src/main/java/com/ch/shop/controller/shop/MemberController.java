package com.ch.shop.controller.shop;

import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.ch.shop.dto.GoogleUser;
import com.ch.shop.dto.KakaoUser;
import com.ch.shop.dto.KakaoUserResponse;
import com.ch.shop.dto.Member;
import com.ch.shop.dto.NaverUser;
import com.ch.shop.dto.NaverUserResponse;
import com.ch.shop.dto.OAuthClient;
import com.ch.shop.dto.OAuthTokenResponse;
import com.ch.shop.dto.Provider;
import com.ch.shop.model.member.MemberService;
import com.ch.shop.model.member.ProviderService;
import com.ch.shop.model.topcategory.TopCategoryService;

import lombok.extern.slf4j.Slf4j;

//일반 유저가 사용하게 될 쇼핑몰 쪽의 회원관련 요청을 처리하는 컨트롤러
@Controller
@Slf4j
public class MemberController {

	@Autowired
	private TopCategoryService topCategoryService;

	@Autowired
	private Map<String, OAuthClient> oauthClients;

	@Autowired
	private RestTemplate restTemplate;// Http요청능력+응답결과를 자동으로 java객체로 매핑(마치 Jackson처럼)

	@Autowired
	private MemberService memberService;

	@Autowired
	private ProviderService providerService;

	// 회원로그인 폼 요청 처리
	@GetMapping("/member/loginform")
	public String getLoginForm(Model model) {

		List topList = topCategoryService.getList();// 3단계: 일 시키기
		// 4단계: 결과 페이지로 가져갈 것이다...
		model.addAttribute("topList", topList);
		return "shop/member/login";
	}

	// sns 로그인을 희망하는 유저들의 로그인 인증 요청 url을 알려주는 컨트롤러 메서드
	// @PathVarivable("provider")는 url의 일부를 파라미터화 시키는 기법, REST API에 사용됨
	@GetMapping("/oauth2/authorize/{provider}")
	@ResponseBody // dispatcherservlet은 jsp와 매핑 시도 안하고 그대로 보냄.
	public String getAuthUrl(@PathVariable("provider") String provider) throws Exception {
		OAuthClient oAuthClient = oauthClients.get(provider);
		log.debug(provider + "의 로그인 요청 url은 {}", oAuthClient.getAuthorizeUrl());// console.log

		// 이 주소를 이용하여, 브라우저 사용자는 프로바이더에게 로그인을 요청해야하는데, 이 때 요청 파라미터를 갖추어야
		// 로그인 절차가 성공할 수 있음.
		// 요청 시 지참할 파라미터에는 clientId, callback url, scope..
		// https://accounts.google.com/o/oauth2/v2/auth?response_type=code&clientId=sdfjsaklfjsa&scope=profile
		// email
		StringBuffer sb = new StringBuffer();
		sb.append(oAuthClient.getAuthorizeUrl()).append("?").append("response_type=code") // 이 요청에 의해 인가 code를 받을 것임을
																							// 알린다
				.append("&client_id=").append(urlEncode(oAuthClient.getClientId())).append("&redirect_uri=")
				.append(urlEncode(oAuthClient.getRedirectUri())).append("&scope=")
				.append(urlEncode(oAuthClient.getScope()));

		return sb.toString();
	}

	// 웹을 통해 파라미터 전송 시 문자열이 깨지지 않도록 인코딩 처리해주는 메서드
	private String urlEncode(String s) throws Exception {
		return URLEncoder.encode(s, "UTF-8");
	}

	/*------------------------------------------------------------
	클라이언트가 동의화면(최초 사용자) 또는 로그인(기존) 요청이 들어오게되고,
	Provider가 이를 처리하는 과정에서
	개발자가 등록해놓은 callback 주소로 임시코드(Authorize code)를 발급한다
	------------------------------------------------------------*/

	@GetMapping("/login/callback/google")
	public String handleCallback(String code, HttpSession session) {
		// 구글이 보내온 인증 코드와, 나의 clientId, client Secret를 조합하여, token을 요청하자!
		// 결국 개발자가 원하는 것은 사용자의 정보이므로, 이 정보를 얻기 위해서는 토큰이 필요하므로....
		log.debug("구글이 발급한 임시코드는 {}", code);

		OAuthClient google = oauthClients.get("google");

		// 구글로부터 받은 임시코드와 나의 정보(client_id, client secret)을 조합하여 구글에게 보내자..(토큰 받으려고..)
		// 이때, 구글과 같은 프로바이더와 데이터를 주고받기 위해서는 HTTP 통신규약을 지켜서 말을 걸때는 머리, 몸을 구성하여 요청을 시도해야 함
		MultiValueMap<String, String> param = new LinkedMultiValueMap<String, String>();// 몸체
		param.add("grant_type", "authorization_code"); // 임시코드를 이용하여 토큰을 요청하겠다는 것을 명시
		param.add("code", code); // 구글로부터 발급받은 임시코드를 그대로 추가
		param.add("client_id", google.getClientId());
		param.add("client_secret", google.getClientSecret());
		param.add("redirect_uri", google.getRedirectUri());// callback url

		HttpHeaders headers = new HttpHeaders();// 머리
		// 아래와 같이 전송파라미터에 대한 contentType을 명시하면, key=value&key=value 방식의 데이터쌍으로 자동으로
		// 변환...
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		// 머리와 몸을 합쳐서 하나의 HTTP 요청 엔티티로 결합
		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(param, headers);

		// 구글에 요청 시작..~ 스프링에서는 Http 요청 후 그 응답 정보를 Java 객체와 자동으로 매핑해주는 편리한 객체를 지원해주는데,
		// 그 객체가 바로 RestTemplate( http요청능력 + jack 능력)
		ResponseEntity<OAuthTokenResponse> response = restTemplate.postForEntity(google.getTokenUrl(), request,
				OAuthTokenResponse.class);
		log.debug("구글로부터 받은 응답정보는 {}", response.getBody());

		// 얻어진 토큰으로 구글에 회원정보를 요청해보기
		OAuthTokenResponse responseBody = response.getBody();
		String access_token = responseBody.getAccess_token();

		log.debug("구글로부터 받은 엑세스 토큰은 {}", access_token);

		// 회원정보 가져오기
		// 구글에 요청을 시도하려면 역시나 이번에도 Http프로토콜의 형식을 갖추어야 함
		HttpHeaders userInfoHeaders = new HttpHeaders();
		// 내가 바로 토큰을 가진 자임을 알리는 헤더 속성값을 넣어야 함
		userInfoHeaders.add("Authorization", "Bearer " + access_token);
		HttpEntity<String> userInfoRequest = new HttpEntity<>("", userInfoHeaders);
		ResponseEntity<GoogleUser> userInfoResponse = restTemplate.exchange(google.getUserInfoUrl(), HttpMethod.GET,
				userInfoRequest, GoogleUser.class); // 서버로부터 데이터를 가져오기이므로, exchange() 사용
		log.debug("사용자 정보는 {}" + userInfoResponse);

		GoogleUser user = userInfoResponse.getBody();

		/*------------------------------------------------------------
		얻어진 유저 정보를 이용하여 할일
		얻어진 회원이 우리의 mysql 존재하는지 따져서,
		있다면? 로그인 세션만 부여하고 홈페이지 메인으로 보내기
		없다면? member테이블에 insert하고 세션부여하고 홈페이지 메인으로 보내기
		------------------------------------------------------------*/

		Member member = new Member(); // empty
		member.setProvider_userid(user.getId());
		member.setName(user.getName());
		member.setEmail(user.getEmail());

		// select * form provider where provider_name = 'google';
		Provider provider = providerService.selectByName("google");
		member.setProvider(provider);
		memberService.registOrUpdate(member);

		// List topList = topCategoryService.getList();//3단계: 일 시키기
		// 4단계: 결과 페이지로 가져갈 것이다...
		// model.addAttribute("topList", topList);

		// 로그인에 성공하면, 브라우저를 종료할 때까지는 자신의 정보를 접근할 수 있는 혜택을 부여해야 하므로,
		// 세션에 회원 정보를 담아둬야 한다..
		// jsp에서의 세션 내장객체 이름 session이고,서블릿에서 자료형은 HttpSession이다.
		// 요청정보를 담당하는 내장객체명은 request, 서블릿에서 자료형은 HttpSession이다.

		session.setAttribute("member", member);

		return "redirect:/"; // 회원 로그인이 처리되면, 쇼핑몰의 메인으로 보내기
	}

	// 네이버 로그인 요청 처리(콜백 함수 처리)
	@GetMapping("/login/callback/naver")
	public String handleNaverLogin(String code, HttpSession session) {
		log.debug("네이버에서 발급한 임시 코드는 {}", code);

		OAuthClient client = oauthClients.get("naver");

		/*----------------------------------------
		1) code, client id, client secret을 구성하여 토큰 발급을 요청
		------------------------------------------*/
		// 몸체 구성
		MultiValueMap<String, String> param = new LinkedMultiValueMap<String, String>();// 몸체
		param.add("grant_type", "authorization_code"); // 임시코드를 이용하여 토큰을 요청하겠다는 것을 명시
		param.add("code", code); // 구글로부터 발급받은 임시코드를 그대로 추가
		param.add("client_id", client.getClientId());
		param.add("client_secret", client.getClientSecret());
		param.add("redirect_uri", client.getRedirectUri());// callback url

		// 머리 구성
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		// 몸+머리
		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(param, headers);

		// 토큰 달라고 요청하기(구글, 네이버, 카카오, 메타 등 모든 프로바이더가 토큰을 포함한 응답정보의 내용이 같다...)
		ResponseEntity<OAuthTokenResponse> response = restTemplate.postForEntity(client.getTokenUrl(), request,
				OAuthTokenResponse.class);
		log.debug("네이버가 응답한 토큰 포함 정보는 ... {}", response);

		OAuthTokenResponse responseBody = response.getBody();

		/*----------------------------------------
		2) 발급된 토큰을 이용하여 회원 정보 조회하기
		------------------------------------------*/
		String access_token = responseBody.getAccess_token();
		log.debug("네이버 토큰은 {}", access_token);

		HttpHeaders userInfoHeaders = new HttpHeaders();
		userInfoHeaders.add("Authorization", "Bearer " + access_token);

		HttpEntity<String> userInfoRequest = new HttpEntity<>("", userInfoHeaders); // 몸은 비워넣고, 몸과머리합쳐 요청 보내기

		ResponseEntity<NaverUserResponse> userInfoResponse = restTemplate.exchange(client.getUserInfoUrl(),
				HttpMethod.GET, userInfoRequest, NaverUserResponse.class);
		NaverUserResponse naverUserResponse = userInfoResponse.getBody();
		NaverUser naverUser = naverUserResponse.getResponse();

		log.debug("고유 id={}", naverUser.getId());
		log.debug("이름은 {}", naverUser.getName());
		log.debug("이메일은 {}", naverUser.getEmail());

		Member member = new Member(); // empty
		member.setProvider_userid(naverUser.getId());
		member.setName(naverUser.getName());
		member.setEmail(naverUser.getEmail());

		Provider provider = providerService.selectByName(client.getProvider());
		member.setProvider(provider);
		memberService.registOrUpdate(member);

		/*----------------------------------------
		3) 로그인 처리
		- 최초의 로그인 시도자는 회원가입 처리
		- 기존 가입자는 로그인만 처리(업데이트) 
		- 세션에 회원정보 저장
		------------------------------------------*/
		session.setAttribute("member", member);

		return "redirect:/";
	}

	// 카카오 로그인 요청 처리(콜백 함수 처리)
	@GetMapping("/login/callback/kakao")
	public String handleKakaoLogin(String code, HttpSession session) {
		log.debug("카카오에서 발급한 임시 코드는 {}", code);
		OAuthClient kakao = oauthClients.get("kakao");

		/*----------------------------------------
		1) code, client id, client secret을 구성하여 토큰 발급을 요청
		------------------------------------------*/
		// 몸체 구성
		MultiValueMap<String, String> param = new LinkedMultiValueMap<String, String>();// 몸체
		param.add("grant_type", "authorization_code"); 			// 임시코드를 이용하여 토큰을 요청하겠다는 것을 명시
		param.add("code", code); 											// 구글로부터 발급받은 임시코드를 그대로 추가
		param.add("client_id", kakao.getClientId());
		param.add("client_secret", kakao.getClientSecret());
		param.add("redirect_uri", kakao.getRedirectUri());  		// callback url

		// 머리 구성
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		// 몸+머리
		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(param, headers);
		// 토큰 달라고 요청하기(구글, 네이버, 카카오, 메타 등 모든 프로바이더가 토큰을 포함한 응답정보의 내용이 같다...)
		ResponseEntity<OAuthTokenResponse> response = restTemplate.postForEntity(
				kakao.getTokenUrl(), request, OAuthTokenResponse.class);
		log.debug("카카오가가 응답한 토큰 포함 정보는 ... {}", response);

		String access_token = response.getBody().getAccess_token();
		// OAuthTokenResponse responseBody = response.getBody();
		// String access_token = responseBody.getAccess_token();
		log.debug("카카오 토큰은 {}", access_token);

		/*----------------------------------------
		2) (사용자 정보 요청) 발급된 토큰을 이용하여 회원 정보 조회하기 
		------------------------------------------*/
		HttpHeaders userInfoHeaders = new HttpHeaders();
		userInfoHeaders.add("Authorization", "Bearer " + access_token);
		HttpEntity<String> userInfoRequest = new HttpEntity<>("", userInfoHeaders); // 몸은 비워넣고, 몸과머리합쳐 요청 보내기
		
		// KakaoUserResponse 데이터 누락방지
		ResponseEntity<KakaoUserResponse> userInfoResponse = restTemplate.exchange(
				kakao.getUserInfoUrl(), HttpMethod.GET, userInfoRequest, KakaoUserResponse.class);
		KakaoUserResponse kakaoUserResponse = userInfoResponse.getBody();

		/*----------------------------------------
		3) 데이터 매핑
		------------------------------------------*/
		Member member = new Member(); // empty
		// 3-1 고유 id 넣기
		member.setProvider_userid(String.valueOf(kakaoUserResponse.getId()));

		// 3-2 이름과 이메일은 따로 카카오 응답 객체에서 직접 추출
		if (kakaoUserResponse.getKakao_account() != null) {
			KakaoUserResponse.KakaoAccount account = kakaoUserResponse.getKakao_account();
			member.setEmail(account.getEmail()); // 이메일 매핑
			if (account.getProfile() != null) {
				member.setName(account.getProfile().getNickname()); // 닉네임을 이름으로 사용
			}
		}

		// 3-3 어느 회사 인지 설정 (카카오)
		Provider provider = providerService.selectByName("kakao");
		member.setProvider(provider);

		// 3-4 서비스 호출(DB 저장 및 메일 발송 시작점!!!!)
		memberService.registOrUpdate(member);

		/*----------------------------------------
		4) 로그인
		- 최초의 로그인 시도자는 회원가입 처리
		- 기존 가입자는 로그인만 처리(업데이트) 
		- 세션에 회원정보 저장
		------------------------------------------*/
		session.setAttribute("member", member);

		return "redirect:/";
	}

}
