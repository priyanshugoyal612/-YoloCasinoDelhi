package com.yolo.casino.delhi.service.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.yolo.casino.delhi.model.YoloBetRequest;
import com.yolo.casino.delhi.model.YoloBetResponse;
import com.yolo.casino.delhi.service.YoloGameServiceImpl;

/**
 * Unit tests for the YoloGameServiceImpl class.
 */
public class YoloGameServiceImplTest {

	@InjectMocks
	private YoloGameServiceImpl yoloGameService;

	@Mock
	private Random random;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void testGameBet_PlayerWins() {
		YoloBetRequest betRequest = new YoloBetRequest();
		betRequest.setNumber(60); // Player's number
		betRequest.setBet(BigDecimal.valueOf(50)); // Bet amount

		// Simulate server number being less than the player's number
		when(random.nextInt(100)).thenReturn(40); // Server number

		// Calculate expected win
		BigDecimal number = BigDecimal.valueOf(60);
		BigDecimal divisor = BigDecimal.valueOf(100).subtract(number);
		BigDecimal factor = BigDecimal.valueOf(99).divide(divisor, 10, RoundingMode.HALF_UP);
		BigDecimal expectedWin = betRequest.getBet().multiply(factor);

	
		YoloBetResponse response = yoloGameService.gameBet(betRequest);

		assertEquals(expectedWin, response.getWin(), "The calculated win amount is incorrect.");
	}

	@Test
	public void testGameBet_PlayerLoses() {
		
		YoloBetRequest betRequest = new YoloBetRequest();
		betRequest.setNumber(40); // Player's number
		betRequest.setBet(BigDecimal.valueOf(50)); // Bet amount

		
		when(random.nextInt(100)).thenReturn(60); // Server number

		
		YoloBetResponse response = yoloGameService.gameBet(betRequest);

		assertEquals(BigDecimal.ZERO, response.getWin(), "The win amount should be zero when the player loses.");
	}

	@Test
	public void testGameBet_DivisionByZero() {
		YoloBetRequest betRequest = new YoloBetRequest();
		betRequest.setNumber(100); // Player's number
		betRequest.setBet(BigDecimal.valueOf(50)); // Bet amount

		
		when(random.nextInt(100)).thenReturn(0); // Server number

		YoloBetResponse response = yoloGameService.gameBet(betRequest);

		
		assertEquals(BigDecimal.ZERO, response.getWin(),
				"The win amount should be zero when there's a division by zero.");
	}
}
