package com.component.test.rv.controller;

import org.junit.Ignore;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.springframework.mock.http.server.reactive.MockServerHttpRequest.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class RobotControllerTest {
    @Autowired
    private MockMvc mvc;

    @Mock
    RobotService robotService;

    @InjectMocks
    RobotController robotController;


    private static HttpHeaders headers;
    String requestBody;

    @BeforeEach
    public void init() {
        headers = new HttpHeaders();
    }

    @Ignore
    public void testAPIPlaceReturnsSuccess() throws Exception {
        doNothing().when(robotService).place(any());

        mvc.perform(post("/place")
                        .body("{\n" +
                                "    \"xCordinate\" : 4,\n" +
                                "    \"yCordinate\" : 4,\n" +
                                "    \"directionEnum\" : \"EAST\"\n" +
                                "}").getNativeRequest())
                .andExpect(status().is2xxSuccessful());

    }
}
