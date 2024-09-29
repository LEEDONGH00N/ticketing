package study.kiwi.ticketing.ticket;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import study.kiwi.ticketing.event.EventSchedule;
import study.kiwi.ticketing.member.Member;
import study.kiwi.ticketing.payment.Payment;

import java.time.LocalDate;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String ticketEventName;

    @NotNull
    private String ticketNum;

    @NotNull
    private LocalDate ticketEventDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_schedule_id")
    private EventSchedule eventSchedule;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patment_id")
    private Payment payment;

}
