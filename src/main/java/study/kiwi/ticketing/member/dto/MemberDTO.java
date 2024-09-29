package study.kiwi.ticketing.member.dto;


import study.kiwi.ticketing.member.Member;

public record MemberDTO(
        Long id,
        String email
) {
    public static MemberDTO from(Member member) {
        return new MemberDTO(member.getId(), member.getEmail());
    }
}
