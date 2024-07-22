package com.yolo.casino.delhi.model;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

/**
 * Represents the response to a betting request, containing the calculated win amount.
 */
@Getter
@Setter
public class YoloBetResponse {

    /** The amount won by the player. */
    private BigDecimal win;

    /**
     * Constructor to initialize the win amount.
     *
     * @param win The calculated win amount.
     */
    public YoloBetResponse(BigDecimal win) {
        this.win = win;
    }
}
