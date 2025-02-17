package study.kiwi.ticketing.domain.member.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public class MemberRequest {
    public record MemberLoginReqDto(
            String email,
            String password
    ){}

    public record MemberNaverSignupReqDto(
            @NotEmpty String name,
            @Email String email,
            @NotEmpty String phoneNum,
            @NotEmpty String providerType,
            @NotEmpty String oauthId

    ){ }

    public record MemberLocalSignupReqDto(
            String name,
            String email,
            String password
    ){}
}