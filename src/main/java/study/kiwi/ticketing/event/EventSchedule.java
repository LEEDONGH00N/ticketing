package study.kiwi.ticketing.event;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import study.kiwi.ticketing.eventPlace.EventPlace;
import study.kiwi.ticketing.seat.entity.Seat;
import study.kiwi.ticketing.ticket.Ticket;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static study.kiwi.ticketing.event.dto.EventScheduleRequestDto.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class EventSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private LocalDate eventStartTime;

    @NotNull
    private LocalDate eventStartDate;

    @NotNull
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

    @Builder
    private EventSchedule(LocalDate eventStartTime, LocalDate eventStartDate, LocalDateTime eventTicketingLinkOpenTime){
        this.eventStartTime = eventStartTime;
        this.eventStartDate = eventStartDate;
        this.eventTicketingLinkOpenTime = eventTicketingLinkOpenTime;
    }

    public static EventSchedule from(EventScheduleCreateRequestDto request){
        return EventSchedule.builder()
                .eventTicketingLinkOpenTime(request.eventTicketingLinkOpenTime())
                .eventStartDate(request.eventStartDate())
                .eventStartTime(request.eventStartTime())
                .build();
    }

}
