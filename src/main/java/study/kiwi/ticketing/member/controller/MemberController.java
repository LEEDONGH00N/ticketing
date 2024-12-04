package study.kiwi.ticketing.member.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import study.kiwi.ticketing.global.common.ApiResponse;
import study.kiwi.ticketing.global.security.domain.MemberDetails;
import study.kiwi.ticketing.member.service.MemberService;

import java.util.Arrays;

import static study.kiwi.ticketing.member.dto.MemberRequest.*;

@RestController
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/home")
    public String home(@AuthenticationPrincipal MemberDetails principal) {
        return principal.getMember().toString();
    }

    @PostMapping("/api/signup")
    public ApiResponse<?> signup(@Valid @RequestBody MemberNaverSignupReqDto request) {
        return ApiResponse.onSuccess(memberService.signupAfterNaverLogin(request));
    }
}