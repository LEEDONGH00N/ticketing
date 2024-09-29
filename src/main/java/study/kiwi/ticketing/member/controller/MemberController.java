package study.kiwi.ticketing.member.controller;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import study.kiwi.ticketing.global.common.ApiResponse;
import study.kiwi.ticketing.member.service.MemberService;
import study.kiwi.ticketing.member.validator.SignUpFormValidator;

import static study.kiwi.ticketing.member.dto.MemberRequest.*;

@RestController
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final MemberService memberService;
    private final SignUpFormValidator signUpFormValidator;

    @PostMapping("/signup")
    public ApiResponse<?> signup(@Valid @RequestBody MemberSignupReqDto request, BindingResult bindingResult) {
        signUpFormValidator.validate(request, bindingResult);
        return ApiResponse.onSuccess(memberService.memberSignup(request));
    }

    @PostMapping("/login")
    public ApiResponse<?> login(@Valid @RequestBody MemberLoginReqDto request, HttpServletResponse response) {
        return ApiResponse.onSuccess(memberService.memberLogin(request, response));
    }

}