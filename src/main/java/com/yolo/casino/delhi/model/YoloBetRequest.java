package com.yolo.casino.delhi.model;

import static com.yolo.casino.delhi.constant.YoloConstant.BET_AMOUNT_MUST_BE_AT_LEAST_0;
import static com.yolo.casino.delhi.constant.YoloConstant.BET_AMOUNT_MUST_NOT_BE_NULL;
import static com.yolo.casino.delhi.constant.YoloConstant.NUMBER_MUST_BE_NO_MORE_THAN_100;
import static com.yolo.casino.delhi.constant.YoloConstant.NUMBER_MUST_BE_AT_LEAST_1;
import static com.yolo.casino.delhi.constant.YoloConstant.NUMBER_MUST_NOT_BE_NULL;

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
	@NotNull(message = NUMBER_MUST_NOT_BE_NULL)
	@Min(value = 1, message = NUMBER_MUST_BE_AT_LEAST_1)
	@Max(value = 100, message = NUMBER_MUST_BE_NO_MORE_THAN_100)
	private Integer number;

	/**
	 * The amount of money the player is betting. Should be a non-negative value.
	 */
	@NotNull(message = BET_AMOUNT_MUST_NOT_BE_NULL)
	@Min(value = 0, message = BET_AMOUNT_MUST_BE_AT_LEAST_0)
	private BigDecimal bet;
}
