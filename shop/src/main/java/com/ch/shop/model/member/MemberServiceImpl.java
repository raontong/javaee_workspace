package com.ch.shop.model.member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ch.shop.dto.Member;
import com.ch.shop.util.MailSender;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Transactional
public class MemberServiceImpl implements MemberService{
    @Autowired
    private MemberDAO memberDAO;

    @Autowired
    private MailSender mailSender;

    @Override
    public void registOrUpdate(Member member) {
        Member obj = memberDAO.findByProvider(member);

        if(obj == null) {
            memberDAO.insert(member); // 회원가입이 안되어 있을 경우만...

            String email = member.getEmail();
            if(email != null && !email.trim().isEmpty()) {
                try {
                    mailSender.send(email, "패션샵 회원가입 감사합니다", "감사합니다요");
                    log.debug("가입 축하 메일 발송 완료. email={}", email);
                } catch (Exception e) {
                    // ✅ 메일 실패로 회원가입 롤백되는 걸 막고 싶으면 여기서 예외를 삼켜야 함
                    // (지금은 @Transactional이라 send 에서 RuntimeException 터지면 롤백됨)
                    log.warn("가입 메일 발송 실패(회원가입은 유지). email={}", email, e);
                }
            } else {
                log.debug("이메일이 없어 가입 메일 발송 스킵. provider_userid={}, provider_id={}",
                        member.getProvider_userid(), member.getProvider().getProvider_id());
            }
            log.debug("신규회원 가입 처리");
        } else {
            memberDAO.update(member);
            log.debug("기존 회원 업데이트 처리");
        }
    }
}