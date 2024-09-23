package study.kiwi.ticketing.event;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import study.kiwi.ticketing.eventPlace.EventPlace;
import study.kiwi.ticketing.seat.entity.Seat;
import study.kiwi.ticketing.ticket.Ticket;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class EventSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate eventStartTime;

    private LocalDate eventStartDate;

    private LocalDateTime eventTicketingLinkOpenTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_place_id")
    private EventPlace eventPlace;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id")
    private Event event;

    @OneToMany(mappedBy = "eventSchedule")
    private List<Seat> seats = new ArrayList<>();


    @OneToOne(mappedBy = "eventSchedule")
    private Ticket ticket;

}
