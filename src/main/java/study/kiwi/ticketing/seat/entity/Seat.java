package study.kiwi.ticketing.seat.entity;

import jakarta.persistence.*;
import jakarta.transaction.Synchronization;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import study.kiwi.ticketing.event.EventSchedule;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
//@Table(indexes = {
//        @Index(name = "seat_key_id", columnList = "")
//})
public class Seat {

    @EmbeddedId
    private SeatId seatId;

    @NotNull
    private String seatLocation;

    @Enumerated(EnumType.STRING)
    private SeatRank seatRank;

    private Boolean isSelected = false;

    private Boolean isPayed = false;

    @MapsId("eventScheduleId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seat_event_schedule_id")
    private EventSchedule eventSchedule;


}
