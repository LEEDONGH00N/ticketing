package study.kiwi.ticketing.member.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import study.kiwi.ticketing.global.codes.ErrorCode;
import study.kiwi.ticketing.global.common.BaseException;
import study.kiwi.ticketing.member.Member;
import study.kiwi.ticketing.member.dto.MemberAuthContext;
import study.kiwi.ticketing.member.dto.MemberDetails;
import study.kiwi.ticketing.member.repository.MemberRepository;

@RequiredArgsConstructor
@Service
@Slf4j
public class MemberDetailsService implements UserDetailsService {
    private final MemberRepository memberRepository;

    @Override
    public MemberDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
        Member member = memberRepository.findMemberByEmail(userEmail)
                .orElseThrow(() -> {
                    log.info("[loadUserByUsername] username:{}, {}", userEmail, ErrorCode.MEMBER_NOT_FOUND);
                    return new BaseException(ErrorCode.MEMBER_NOT_FOUND);
                });
        MemberAuthContext ctx = MemberAuthContext.of(member);
        return new MemberDetails(ctx);
    }
}