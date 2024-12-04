package study.kiwi.ticketing.member;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import study.kiwi.ticketing.member.dto.MemberRequest;
import study.kiwi.ticketing.oauth2.userInfo.OAuth2UserInfo;
import study.kiwi.ticketing.oauth2.domain.OAuthProviderType;
import study.kiwi.ticketing.payment.Payment;

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
    private Member(String name, String email, String oauthId, OAuthProviderType oAuthProviderType, Role role, String phoneNum) {
        this.name = name;
        this.email = email;
        this.oauthId = oauthId;
        this.providerType = oAuthProviderType;
        this.role = role;
        this.phoneNum = phoneNum;
    }

    public static Member createMemberGuest(OAuth2UserInfo userInfo, OAuthProviderType type){
        return Member.builder()
                .name(userInfo.getName())
                .email(userInfo.getEmail())
                .oAuthProviderType(type)
                .oauthId(userInfo.getSocialId())
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
}
