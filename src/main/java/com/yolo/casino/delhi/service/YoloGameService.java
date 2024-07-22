package com.yolo.casino.delhi.service;

import com.yolo.casino.delhi.model.YoloBetRequest;
import com.yolo.casino.delhi.model.YoloBetResponse;

/**
 * @author Priyanshu Goyal
 *  Service for handling game betting logic.
 */
public interface YoloGameService {

	 /**
     * Processes the bet request and calculates the win amount based on the player's bet and selected number.
     * 
     * @param betRequest The bet request containing the bet amount and the number selected by the player.
     * @return The response containing the calculated win amount.
     */
	YoloBetResponse gameBet(YoloBetRequest betRequest);

}