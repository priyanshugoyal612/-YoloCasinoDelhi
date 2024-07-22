package com.yolo.casino.delhi.model;

import java.math.BigDecimal;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents a betting request from a player.
 */
@Getter
@Setter
public class YoloBetRequest {

	/**
	 * The number chosen by the player for the bet. Should be between 1 and 100
	 * (inclusive).
	 */
	@NotNull(message = "Number must not be null.")
	@Min(value = 1, message = "Number must be at least 1.")
	@Max(value = 100, message = "Number must be no more than 100.")
	private Integer number;

	/**
	 * The amount of money the player is betting. Should be a non-negative value.
	 */
	@NotNull(message = "Bet amount must not be null.")
	@Min(value = 0, message = "Bet amount must be at least 0.")
	private BigDecimal bet;
}
