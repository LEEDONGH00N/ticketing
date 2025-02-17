package study.kiwi.ticketing.domain.member.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import study.kiwi.ticketing.domain.member.dto.MemberRequest;
import study.kiwi.ticketing.domain.member.service.MemberService;
import study.kiwi.ticketing.global.common.ApiResponse;

@RestController
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/home")
    public String home(){
        return "Hello World!!!";
    }

    @PostMapping("/api/naver/signup")
    public ApiResponse<?> signup(@Valid @RequestBody MemberRequest.MemberNaverSignupReqDto request) {
        return ApiResponse.onSuccess(memberService.signupAfterNaverLogin(request));
    }

    @PostMapping("/api/local/signup")
    public ApiResponse<?> signupLocal(@Valid @RequestBody MemberRequest.MemberLocalSignupReqDto request){
        return ApiResponse.onSuccess(memberService.signup(request));
    }
}