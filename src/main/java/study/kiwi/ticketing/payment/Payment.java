package study.kiwi.ticketing.payment;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import study.kiwi.ticketing.member.Member;
import study.kiwi.ticketing.ticket.Ticket;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @OneToOne(mappedBy = "payment", cascade = CascadeType.REMOVE)
    private Ticket ticket;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
}