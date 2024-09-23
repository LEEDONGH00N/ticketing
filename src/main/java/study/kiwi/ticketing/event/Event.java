package study.kiwi.ticketing.event;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String eventTitle;

    // TODO : 이미지로 대체할까?
    private String eventDescription;

    private LocalDateTime eventDateTime;

    @OneToMany(mappedBy = "event")
    private List<EventSchedule> eventSchedules = new ArrayList<>();

}
