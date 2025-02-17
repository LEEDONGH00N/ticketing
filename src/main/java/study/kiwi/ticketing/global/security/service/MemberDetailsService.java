package study.kiwi.ticketing.global.security.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import study.kiwi.ticketing.global.codes.ErrorCode;
import study.kiwi.ticketing.global.common.BaseException;
import study.kiwi.ticketing.global.security.domain.PrincipalDetails;
import study.kiwi.ticketing.domain.member.Member;
import study.kiwi.ticketing.domain.member.dto.AuthenticatedMember;
import study.kiwi.ticketing.domain.member.repository.MemberRepository;

@RequiredArgsConstructor
@Service
@Slf4j
public class MemberDetailsService implements UserDetailsService {
    private final MemberRepository memberRepository;

    @Override
    public PrincipalDetails loadUserByUsername(String email){
        Member findMember = memberRepository
                .findMemberByEmail(email)
                .orElseThrow(() -> new BaseException(ErrorCode.MEMBER_NOT_FOUND));
        AuthenticatedMember authenticatedMember = AuthenticatedMember.from(findMember);
        return PrincipalDetails.from(authenticatedMember);
    }
}
