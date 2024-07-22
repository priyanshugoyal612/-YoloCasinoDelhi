package com.yolo.casino.delhi.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.yolo.casino.delhi.model.YoloBetRequest;
import com.yolo.casino.delhi.model.YoloBetResponse;
import com.yolo.casino.delhi.service.YoloGameService;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;

/**
 * REST controller for handling Yolo game betting operations.
 */
@RestController
@RequestMapping("/api/v1")
@Log4j2
public class YoloGameRestController {

	@Autowired
	private YoloGameService yoloGameService;

	/**
	 * Endpoint for processing a player's bet.
	 * 
	 * @param betRequest The request body containing the bet amount and selected
	 *                   number.
	 * @return A response entity containing the win amount based on the bet and
	 *         selected number.
	 */
	@PostMapping("/game/bet")
	public ResponseEntity<YoloBetResponse> yoloBet(@Valid @RequestBody YoloBetRequest betRequest) {
		log.info("Received bet request: Bet={}, Number={}", betRequest.getBet(), betRequest.getNumber());
		YoloBetResponse response = yoloGameService.gameBet(betRequest);

		log.info("Processed bet request: Bet={}, Number={}, Win={}", betRequest.getBet(), betRequest.getNumber(),
				response.getWin());

		return ResponseEntity.ok(response);
	}

	/**
	 * Simple endpoint for testing the API.
	 * 
	 * @return A response entity with a simple message.
	 */
	@GetMapping("/bet")
	public ResponseEntity<String> yolo() {
		log.info("Received request to the test endpoint.");

		return ResponseEntity.ok("You Only Lived Once");
	}
}
