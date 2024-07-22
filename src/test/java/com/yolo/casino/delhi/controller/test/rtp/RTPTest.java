package com.yolo.casino.delhi.controller.test.rtp;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.yolo.casino.delhi.model.YoloBetRequest;
import com.yolo.casino.delhi.model.YoloBetResponse;
import com.yolo.casino.delhi.service.YoloGameServiceImpl;

import lombok.extern.log4j.Log4j2;

/**
 * 
 */
@Log4j2
public class RTPTest {

	private YoloGameServiceImpl gameService = new YoloGameServiceImpl();

	@Test
	public void testRTP() throws Exception {
		int rounds = 1_000_000;
		int threads = 24;
		BigDecimal totalBet = BigDecimal.valueOf(rounds); // Total bet is 1,000,000 if each bet is 1
		BigDecimal totalWin = BigDecimal.ZERO;

		ExecutorService executor = Executors.newFixedThreadPool(threads);

		List<Callable<BigDecimal>> tasks = IntStream.range(0, rounds).mapToObj(i -> (Callable<BigDecimal>) () -> {
			YoloBetRequest request = new YoloBetRequest();
			request.setBet(BigDecimal.ONE); // Each bet is 1 unit
			request.setNumber((int) (Math.random() * 99) + 1); // Number between 1 and 99
			YoloBetResponse response = gameService.gameBet(request);
			return response.getWin();
		}).collect(Collectors.toList());

		// Submit tasks and collect results
		List<Future<BigDecimal>> futures = executor.invokeAll(tasks);

		for (Future<BigDecimal> future : futures) {
			try {
				totalWin = totalWin.add(future.get());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		executor.shutdown();

		// Calculate RTP
		BigDecimal rtp = totalWin.divide(totalBet, 4, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(100));
		log.info("Total Bet: " + totalBet);
		log.info("Total Win: " + totalWin);
		log.info("RTP: " + rtp + "%");

		// Adjust acceptable range based on expected RTP variability

		Assertions.assertTrue(totalBet.compareTo(BigDecimal.ZERO) > 0);
		Assertions.assertTrue(totalWin.compareTo(BigDecimal.ZERO) > 0);
		assertTrue(rtp.compareTo(BigDecimal.valueOf(98)) >= 0 && rtp.compareTo(BigDecimal.valueOf(420)) <= 0,
				"RTP out of expected range. Actual RTP: " + rtp);
	}
}
