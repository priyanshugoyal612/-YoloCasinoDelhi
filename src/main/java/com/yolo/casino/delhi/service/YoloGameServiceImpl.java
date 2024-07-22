package com.yolo.casino.delhi.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.yolo.casino.delhi.model.YoloBetRequest;
import com.yolo.casino.delhi.model.YoloBetResponse;

import lombok.extern.log4j.Log4j2;

/**
 * Service implementation for handling game betting logic.
 */
@Service
@Log4j2
public class YoloGameServiceImpl implements YoloGameService {

	private final Random random = new Random();

	/**
	 * Processes a bet request and calculates the win based on the player's number
	 * and bet amount.
	 *
	 * @param betRequest Contains the player's bet amount and the number they have
	 *                   chosen.
	 * @return A YoloBetResponse containing the calculated win amount.
	 */
	@Override
	public YoloBetResponse gameBet(YoloBetRequest betRequest) {
		// Generate a random number between 1 and 100 (inclusive) for the server
		int serverNumber = random.nextInt(100) + 1;
		log.debug("Server generated number: {}", serverNumber);

		if (betRequest.getNumber() > serverNumber) {
			BigDecimal bet = betRequest.getBet();
			BigDecimal number = BigDecimal.valueOf(betRequest.getNumber());
			BigDecimal divisor = BigDecimal.valueOf(100).subtract(number);

			// Check for division by zero scenario
			if (divisor.compareTo(BigDecimal.ZERO) == 0) {
				log.error("Division by zero error: Player number is 100.");
				return new YoloBetResponse(BigDecimal.ZERO);
			}

			// Calculate the win amount
			BigDecimal factor = BigDecimal.valueOf(99).divide(divisor, 10, RoundingMode.HALF_UP);
			BigDecimal win = bet.multiply(factor);
			
			log.info("Processing bet: Bet Amount = {}, Player Number = {}", bet, number);
			log.info("Divisor = {}", divisor);
			log.info("Calculation Factor = {}", factor);
			log.info("Calculated Win Amount = {}", win);

			return new YoloBetResponse(win);
		} else {
			log.info("Player's number is not greater than the server's number. No win.");
			return new YoloBetResponse(BigDecimal.ZERO);
		}
	}
}
