package study.kiwi.ticketing.global.security.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import study.kiwi.ticketing.global.codes.ErrorCode;
import study.kiwi.ticketing.global.common.BaseException;
import study.kiwi.ticketing.global.security.domain.MemberDetails;
import study.kiwi.ticketing.member.repository.MemberRepository;

import java.util.Collections;

@RequiredArgsConstructor
@Service
@Slf4j
public class MemberDetailsService implements UserDetailsService {
    private final MemberRepository memberRepository;

    @Override
    public MemberDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
        log.info("loadUserByUsername 진입");
        return memberRepository.findMemberByEmail(userEmail)
                .map(member -> new MemberDetails(member, Collections.singleton(new SimpleGrantedAuthority(member.getRole().name()))))
                .orElseThrow(() -> new BaseException(ErrorCode.MEMBER_NOT_FOUND));
    }
}