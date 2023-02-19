package com.unit.test.rv.service;

import com.rv.PhoneDirectoryApplication;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import static com.rv.entities.DirectionFacingEnum.*;
import static com.rv.util.Constants.ERROR_MSG_OUT_OF_BOUNDRY;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = PhoneDirectoryApplication.class)
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class RobotMoveAPITest {

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
    public void shouldGetSuccessWhenPositionIsInitializedForMoveAPICall() {
        //place
        robotService.place(new Position(2, 2, EAST));

        //report
        robotService.move();

        // Assertion on report
        assertEquals("Robot is at 3,2,EAST", robotService.report());

    }


    @Test
    public void shouldReturnErrorWhenPositionIsEastOutOfBoundMoveAPICall() {
        //place
        robotService.place(new Position(5, 2, EAST));

        //report
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            robotService.move();
        });
        // Assertion on report
        assertEquals(ERROR_MSG_OUT_OF_BOUNDRY, exception.getMessage());

    }

    @Test
    public void shouldGetErrorWhenPositionIsNorthOutOfBoundMoveAPICall() {
        //place
        robotService.place(new Position(3, 5, NORTH));

        //report
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            robotService.move();
        });
        // Assertion on report
        assertEquals(ERROR_MSG_OUT_OF_BOUNDRY, exception.getMessage());

    }


    @Test
    public void shouldGetErrorWhenPositionIsWESTOutOfBoundMoveAPICall() {
        //place
        robotService.place(new Position(0, 3, WEST));

        //report
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            robotService.move();
        });
        // Assertion on report
        assertEquals(ERROR_MSG_OUT_OF_BOUNDRY, exception.getMessage());

    }
}




