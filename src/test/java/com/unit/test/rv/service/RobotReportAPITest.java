package com.unit.test.rv.service;

import com.rv.PhoneDirectoryApplication;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import static com.rv.entities.DirectionFacingEnum.EAST;
import static com.rv.util.Constants.POSITION_IS_NOT_INITIALIZED;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = PhoneDirectoryApplication.class)
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

public class RobotReportAPITest {

    @Autowired
    private RobotEntity robotEntity;

    @Autowired
    private TableDimention grid;

    @Autowired
    private RobotService robotService;

    @Autowired
    private WebTestClient webTestClient;


    @BeforeEach
    public void init() {
        grid.setWidth(5);
        grid.setHeight(5);
    }

    @Test
    @Order(1)
    public void shouldThrowErrorWhenPositionIsNullForReportAPICall() {
        // Assertion
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            robotService.report();
        });

        assertEquals(POSITION_IS_NOT_INITIALIZED, exception.getMessage());
    }

    @Test
    public void shouldReturnSuccessWhenPositionIsInitializedForReportAPICall() {
        //place
        robotService.place(new Position(2, 2, EAST));

        //report
        String reponse = robotService.report();

        // Assertion on report
        assertEquals("Robot is at 2,2,EAST", reponse);

    }


}




