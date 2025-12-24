package com.ch.shop.controller.shop;

import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

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
import com.ch.shop.dto.OAuthClient;
import com.ch.shop.dto.OAuthTokenResponse;
import com.ch.shop.model.topcategory.TopCategoryService;

import lombok.extern.slf4j.Slf4j;

/* 일반 유저가 사용하게 될 쇼핑몰쪽의 회원관련 요청을 처리하는 컨트롤러 */
@Slf4j
@Controller
public class MemberController {
	
	@Autowired
	private TopCategoryService topCategoryService;

	@Autowired
    private Map<String, OAuthClient> oauthClients;  // 주입받기
	
	@Autowired
	private RestTemplate restTemplate;
	
	//회원로그인 폼 요청 처리 
	@GetMapping("/member/loginform")
	public String getLoginForm(Model model) {
		
		List topList=topCategoryService.getList();//3단계: 일시키기
		model.addAttribute("topList", topList);//4단계: 결과 페이지로 가져갈 것이 있다..
		
		return "shop/member/login";
	}
	
	// sns 로그인을 희망하는 유저들의 로그인 인증 요청 url 주소를 알려주는 컨트롤러 메서드
	// @PathVariable 는 url 의 일부를 파라미터화 시키는 기법, REST API 에 사용됨
    // SNS 로그인 인증 URL 반환
	@GetMapping("/oauth2/authorize/{provider}")
	@ResponseBody // 이 어노테에션을 설정하면, DispatcherServlet 은 jsp 와의 매핑을 시도하지 않고, 반환값 그대로를 응답 정보로 보냄
	//                                변하는 애가 있어"provider"         String  아무거나
	public String getAuthUrl(@PathVariable("provider") String provider) throws Exception{
		OAuthClient oAuthClient=oauthClients.get(provider);

		log.debug(provider+"의 로그인 요청 url은 {}", oAuthClient.getAuthorizeUrl());
		
		// 이 주소를 이용하여, 브라우저 사용자는 프로바이더에게 로그인을 요청해야하는데, 이때 요청 파라미터를 갖추어야 
		// 로그인 절차가 성공할 수 있다.
		// 그림의 4번째, 요청시 지참할 파라미터 에는 clientId , callback url, scope, .. 가져가야한다.
		// https://accounts.google.com/o/oauth2/v2/auth?clientId=sdlfjlkasjdfsadf?scope=profile email
		StringBuffer sb=new StringBuffer();
		sb.append(oAuthClient.getAuthorizeUrl()).append("?")
		.append("response_type=code") // 이요청에 의해 인가 code를 받을 것임을 알린다.
		.append("&client_id=").append(urlEncode(oAuthClient.getClientId())) // 클라이언트 id 추가
		.append("&redirect_uri=").append(urlEncode(oAuthClient.getRedirectUri())) // 콕백받을 주소추가
		.append("&scope=").append(urlEncode(oAuthClient.getScope())); // 사용자 정보의 접근 범위 추가
		
		log.debug("sb is {} ", sb.toString());
		
		return sb.toString();
	}
	
	// 웹을 통해 파라미터 전송시 문자열이 깨지지 않도록 인코딩 처리해주는 메서드
	private String urlEncode(String s) throws Exception{
		return URLEncoder.encode(s, "UTF-8");
	}
	
	/*------------------------------------------------------------------
	클라이언트가 동의화면(최초 사용자) 또는 로그인(기존) 요청이 들어오게 되고, Provider 가 이를 처리하는 과정에서
	개발자가 등록해놓은 callback 주소로 임시코드(Authorize code)를 발급힌다
	------------------------------------------------------------------*/
	@GetMapping("/login/callback/google")
	public String handleCallback(String code) {
		
		/*------------------------------------------------------------------
		구글이 보내온  인증코드와, 나의 CLIENT ID , CLIENT Secret 를 조합하ㅕㅇ, token 을 요청하자!
		결국 개발자가 원하는 것은 사용자의 정보 이므로, 이 정보를 덛기 위해서는 토큰이 필요하므로,
		------------------------------------------------------------------*/
		log.debug("구글이 발급한 임시코드는{}", code);
		
		OAuthClient google=oauthClients.get("google");
		
		// 구글로부터 받은 임시코드와 나의 정보(client id, client secret)를 조합하여 구글에게 보내자...(토큰받으려고)
		// 이때, 구글과 같은 프로바이더와 데이터를 주고 받기 위해서는 HTTP 통신 규약을 지켜서 말을 걸떄는  머리, 몸을 구성하여 요청을 시도해야함
		// 그냥 맵이다...
		MultiValueMap<String, String> param = new LinkedMultiValueMap<String, String>(); // 몸체
		//?????? add, put 사용 구분 확인~~~~~
		param.add("grant_type", "authorization_code"); // 임시코드를 이용하여 토큰을 요청하겠다는 것을 명시
		param.add("code", code); // 구글로 부터 발급받은 임시코드를 그대로 추가
		param.add("client_id", google.getClientId()); // 클라이언 아이디 추가
		param.add("client_secret", google.getClientSecret()); // 
		param.add("redirect_uri", google.getRedirectUri()); // callback url
		
		HttpHeaders headers=new HttpHeaders(); // 머리
		
		// 아래와 같이 전송파라미터에 대한 contentType 을 명시하면, key=value&key=value 방식의 데이터쌍으로 자동으로 변환
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		
		//머리와 본문(몸)을 합쳐서 하나의 HTTP 요청 엔터티로 결합하자
		HttpEntity<MultiValueMap<String, String>> request=new HttpEntity<>(param, headers);
		
		// 구글에 요청 시작!!, 스프링에서는 Http 요청 후 그 응답 정보를 java 객체와 자동으로 매핑해주는 편리한 객체를 지원해주는데,
		// 그 객체가 바로 RestTemplate (http요청 능력 + jack 능력)
		//								(구글의토큰발급주소 , 머리와몸을 합친용청객체, 결과를받을클래스-바구니)
		ResponseEntity<OAuthTokenResponse> response=restTemplate.postForEntity(google.getTokenUrl(), request, OAuthTokenResponse.class);
		log.debug("구글로 부터 받은 응답정보는{}", response.getBody());
		
		// 얻어진 토큰으로 구글에 회원정보를 요청해보기
		OAuthTokenResponse responseBodyString=response.getBody();
		String access_token=responseBodyString.getAccess_token();
		log.debug("구글로 부터 받은 엑세스 토큰은{}", access_token);
		
		// 회원정보 가져오기
		// 구글에 요청을 시도하려면 역시나 이번에도 Http 프로토콜의 형식을 갖추어야 함
		HttpHeaders userInfoHeaders = new HttpHeaders();
		// 내가 바로 토큰을 가진 자임을 알리는 헤더 속성값을 넣어야함
		userInfoHeaders.add("Authorization", "Bearer "+ access_token);
		
		HttpEntity<String> userInfoRequest= new HttpEntity<>("", userInfoHeaders);
		//								(유저정보요청주소, )
		ResponseEntity<GoogleUser> userInfoResponse=restTemplate.exchange(google.getUserInfoUrl(), HttpMethod.GET, userInfoRequest, GoogleUser.class); // 서버로 부터 데이터를 가져오기 이므로, exchage()사용
		
		log.debug("사용자 정보는 {}", userInfoResponse);
		
		/*--------------------------------------------
		얻어진 유저 정보를 이용하여 할일
		1) 얻어진 회원이 우리의 mysql 존재하는지 따져서,
			있다면? 로그인 세션만 부여하고 홈페이지 메인으로 보내기
			없다면? member 테이블에 insert 하고 세션부여하고 홈페이지 메인으로 보내기
		-------------------------------------------------*/
		
		return null;
	}
	
	
}









