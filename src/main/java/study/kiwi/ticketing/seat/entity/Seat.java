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
@Table(name = "seat", indexes = {
        @Index(name = "idx_seat_location", columnList = "seatLocation"),
        @Index(name = "idx_seat_rank", columnList = "seatRank"),
        @Index(name = "idx_is_selected", columnList = "isSelected"),
        @Index(name = "idx_is_payed", columnList = "isPayed"),
        @Index(name = "idx_event_schedule", columnList = "event_schedule_id")
})
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
    @JoinColumn(name = "event_schedule_id")
    private EventSchedule eventSchedule;


}
