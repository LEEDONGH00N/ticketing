package study.kiwi.ticketing.domain.event.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class EventScheduleRequestDto {

    public record EventScheduleCreateRequestDto(
            LocalDate eventStartTime,
            LocalDate eventStartDate,
            LocalDateTime eventTicketingLinkOpenTime
    ){}
}
