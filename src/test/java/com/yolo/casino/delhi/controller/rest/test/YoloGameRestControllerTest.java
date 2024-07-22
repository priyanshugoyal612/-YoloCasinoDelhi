package com.yolo.casino.delhi.controller.rest.test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yolo.casino.delhi.controller.rest.YoloGameRestController;
import com.yolo.casino.delhi.model.YoloBetRequest;
import com.yolo.casino.delhi.model.YoloBetResponse;
import com.yolo.casino.delhi.service.YoloGameService;

/**
 * Unit tests for the YoloGameRestController class.
 */
@ExtendWith({ MockitoExtension.class, SpringExtension.class })
@WebMvcTest(YoloGameRestController.class)
public class YoloGameRestControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Mock
	private YoloGameService yoloGameService;

	@InjectMocks
	private YoloGameRestController yoloGameRestController;

	@BeforeEach
	public void setup() {
		mockMvc = MockMvcBuilders.standaloneSetup(yoloGameRestController).build();
	}

	@Test
	public void testYoloBet_ValidRequest() throws Exception {

		YoloBetRequest betRequest = new YoloBetRequest();
		betRequest.setNumber(50);
		betRequest.setBet(BigDecimal.valueOf(100));

		YoloBetResponse betResponse = new YoloBetResponse(BigDecimal.valueOf(200));

		when(yoloGameService.gameBet(any(YoloBetRequest.class))).thenReturn(betResponse);

		mockMvc.perform(
				post("/api/v1/game/bet").contentType(MediaType.APPLICATION_JSON).content(asJsonString(betRequest)))
				.andExpect(status().isOk()).andExpect(jsonPath("$.win").value(200));
	}

	@Test
	public void testYoloBet_InvalidRequest() throws Exception {
		YoloBetRequest betRequest = new YoloBetRequest();
		betRequest.setNumber(101); // Invalid number
		betRequest.setBet(BigDecimal.valueOf(-1)); // Invalid bet amount

		mockMvc.perform(
				post("/api/v1/game/bet").contentType(MediaType.APPLICATION_JSON).content(asJsonString(betRequest)))
				.andExpect(status().isBadRequest());
	}

	@Test
	public void testYolo() throws Exception {
		mockMvc.perform(get("/api/v1/bet")).andExpect(status().isOk())
				.andExpect(jsonPath("$").value("You Only Lived Once"));
	}

	/**
	 * Helper method to convert an object to a JSON string.
	 *
	 * @param obj the object to be converted
	 * @return JSON string representation of the object
	 * @throws Exception if conversion fails
	 */
	private String asJsonString(final Object obj) throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(obj);
	}
}
