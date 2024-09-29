package study.kiwi.ticketing.member;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "member_oauth")
public class MemberOAuth {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_oauth_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id") @NotNull
    private Member member;

    @Column(name = "oauth_id") @NotNull
    private String oauthId;

    @Column(name = "provider_type") @NotNull
    @Enumerated(value = EnumType.STRING)
    private OAuthProviderType oAuthProviderType;

    @Builder
    private MemberOAuth(Member member, String oauthId, OAuthProviderType oAuthProviderType) {
        this.member = member;
        this.oauthId = oauthId;
        this.oAuthProviderType = oAuthProviderType;
    }

//    public static MemberOAuth createMemberOAuthWithSocialLogin(Member member, String oauthId, OAuthProviderType oAuthProviderType){
//        return MemberOAuth.builder()
//                .member(member)
//                .oauthId(oauthId)
//                .oAuthProviderType(oAuthProviderType)
//                .build();
//    }
}
