package study.kiwi.ticketing.eventPlace;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import study.kiwi.ticketing.event.Event;
import study.kiwi.ticketing.event.EventSchedule;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EventPlace {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String eventPlaceName;

    private String eventPlaceDescription;

    private String eventPlaceLocation;

    private String eventPlaceType;

    @OneToMany(mappedBy = "eventPlace")
    private List<EventSchedule> eventSchedules = new ArrayList<>();
}
