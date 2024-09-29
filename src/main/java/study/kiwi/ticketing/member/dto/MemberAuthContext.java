package study.kiwi.ticketing.member.dto;

import lombok.Builder;
import study.kiwi.ticketing.member.Member;

@Builder
public record MemberAuthContext(
        Long id,
        String name,
        String role,
        String email,
        String password
) {
    public static MemberAuthContext of(Member member) {
        return MemberAuthContext.builder()
                .email(member.getEmail())
                .name(member.getName())
                .password(member.getEncodedPassword())
                .role(member.getRole().toString())
                .id(member.getId())
                .build();
    }
}
