package study.kiwi.ticketing.domain.member;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import study.kiwi.ticketing.domain.member.dto.MemberRequest;
import study.kiwi.ticketing.global.oauth2.userInfo.OAuth2UserInfo;
import study.kiwi.ticketing.global.oauth2.domain.OAuthProviderType;
import study.kiwi.ticketing.domain.payment.Payment;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String oauthId;
    private String phoneNum;
    private String encodedPassword;

    @Enumerated(value = EnumType.STRING)
    private OAuthProviderType providerType;

    @OneToMany(mappedBy = "member", cascade = CascadeType.PERSIST)
    private List<Payment> payments = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private Role role;

    public enum Role {
        USER("USER"),
        GUEST("GUEST");

        Role(String type) {}

        private String type;
    }

    @Builder
    private Member(String name, String email, String oauthId, OAuthProviderType oAuthProviderType, Role role, String phoneNum, String password) {
        this.name = name;
        this.email = email;
        this.oauthId = oauthId;
        this.providerType = oAuthProviderType;
        this.role = role;
        this.phoneNum = phoneNum;
        this.encodedPassword = password;
    }

    public static Member createMemberGuest(OAuth2UserInfo userInfo){
        return Member.builder()
                .name(userInfo.getName())
                .email(userInfo.getEmail())
                .oAuthProviderType(userInfo.getProviderType())
                .oauthId(userInfo.getOAuthId())
                .phoneNum(userInfo.getPhoneNum())
                .role(Role.GUEST)
                .build();
    }

    public static Member createMember(MemberRequest.MemberNaverSignupReqDto request){
        return Member.builder()
                .name(request.name())
                .phoneNum(request.phoneNum())
                .email(request.email())
                .oauthId(request.oauthId())
                .role(Role.USER)
                .build();
    }

    public static Member createMember(MemberRequest.MemberLocalSignupReqDto request){
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return Member.builder()
                .name(request.name())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .oAuthProviderType(OAuthProviderType.NAVER)
                .role(Role.USER)
                .build();
    }
}
