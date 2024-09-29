package study.kiwi.ticketing.member.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public class MemberRequest {

    public record MemberSignupReqDto(
            @NotEmpty String name,
            @Email String email,
            @NotEmpty String password
    ){}

    public record MemberLoginReqDto(
            String email,
            String password
    ){}
}