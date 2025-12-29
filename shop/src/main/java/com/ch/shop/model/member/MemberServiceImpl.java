package com.ch.shop.model.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ch.shop.dto.Member;
import com.ch.shop.util.MailSender;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MemberServiceImpl implements MemberService{
	
	@Autowired
	private MemberDAO memberDAO;
	
	@Autowired
	private MailSender mailSender;
	
	@Transactional
	public void registOrUpdate(Member member) {
		
		//회원이 존재하는지 먼저 따져보자 
		Member obj=memberDAO.findByProvider(member);
		
		if(obj==null) {
			memberDAO.insert(member);//회원가입이 안되어 있을 경우만..
			mailSender.send(member.getEmail(), "패션샵 회원가입을 축하드립니다", "감사합니다.");
			log.debug("신규 회원 가입 처리");
			//이메일 발송 예정 (카카오의 경우만 제외..)			
		}else {
			//sns의 회원의 경우 자신의 프로필을 변경할 수있기 때문에, 우리의 mysql도 그 정보에 맞게 동기화시켜야 함 ..
			memberDAO.update(member);
			log.debug("기존 회원 업데이트 처리");
		}
	}
	
}







