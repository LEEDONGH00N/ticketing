package study.kiwi.ticketing.member.dto;

public class MemberRequest {

    public record MemberSignupReqDto(
            String name,
            String email,
            String password
    ){}

    public record MemberLoginReqDto(
            String email,
            String password
    ){}
}