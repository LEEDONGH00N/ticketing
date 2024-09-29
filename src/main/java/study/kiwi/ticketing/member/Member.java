package study.kiwi.ticketing.member;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import study.kiwi.ticketing.member.dto.MemberRequest;
import study.kiwi.ticketing.payment.Payment;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String email;

    @NotNull
    private String encodedPassword;

    @OneToMany(mappedBy = "member", cascade = CascadeType.PERSIST)
    private List<Payment> payments = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private Role role = Role.USER;

    @Getter
    public enum Role {
        USER("USER"),
        ADMIN("ADMIN");

        Role(String name) {}

        private String name;
    }


    @Builder
    private Member(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.encodedPassword = password;
    }

    public static Member from(MemberRequest.MemberSignupReqDto request, String encodedPassword) {
        return Member.builder()
                .email(request.email())
                .name(request.name())
                .password(encodedPassword)
                .build();
    }

}
