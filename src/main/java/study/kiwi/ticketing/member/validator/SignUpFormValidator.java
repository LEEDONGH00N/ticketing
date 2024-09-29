package study.kiwi.ticketing.member.validator;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import study.kiwi.ticketing.global.codes.ErrorCode;
import study.kiwi.ticketing.global.common.BaseException;
import study.kiwi.ticketing.member.dto.MemberRequest;
import study.kiwi.ticketing.member.repository.MemberRepository;

@Component
@RequiredArgsConstructor
@Slf4j
public class SignUpFormValidator implements Validator {

    private final MemberRepository memberRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        log.info("SignUpFormValidator supports");
        return clazz.isAssignableFrom(MemberRequest.MemberSignupReqDto.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        MemberRequest.MemberSignupReqDto request = (MemberRequest.MemberSignupReqDto) target;
        if (memberRepository.existsByEmail(request.email())) {
            //errors.rejectValue("email", "EXIST_EMAIL", "이미 존재하는 이메일입니다.");
            throw new BaseException(ErrorCode.EMAIL_ALREADY_EXISTS);
        }
        log.info("회원가입된 이력이 없음");
    }
}
