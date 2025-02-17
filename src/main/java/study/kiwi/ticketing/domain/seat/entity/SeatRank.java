package study.kiwi.ticketing.domain.seat.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum SeatRank {
    VIP(30000),
    S(20000),
    A(10000);

    private Integer price;

}
