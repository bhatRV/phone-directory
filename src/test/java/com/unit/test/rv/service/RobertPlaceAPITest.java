package com.unit.test.rv.service;

import com.rv.PhoneDirectoryApplication;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import static com.rv.entities.DirectionFacingEnum.EAST;
import static com.rv.util.Constants.ERROR_MSG_OUT_OF_BOUNDRY;
import static com.rv.util.Constants.POSITION_IS_NOT_INITIALIZED;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = PhoneDirectoryApplication.class)
@ActiveProfiles("test")
public class RobertPlaceAPITest {

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

    //TEST CASES FOR PLACE
    @ParameterizedTest
    @ValueSource(strings = {"EAST", "WEST", "NORTH"})
    public void shouldPlaceRobotInAnyDirection(String dir) {
        // Actual result
        robotService.place(new Position(1, 2, DirectionFacingEnum.valueOf(dir)));

        // Assertion
        assertEquals(1, robotEntity.getPosition().getXCordinate());
        assertEquals(2, robotEntity.getPosition().getYCordinate());
        assertEquals(dir, robotEntity.getPosition().getDirectionEnum().name());
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2, 3, 4, 5})
    public void shouldPlaceRobotInAnyValid_X_Co_Ordinate(int x) {
        // Actual result
        robotService.place(new Position(x, 2, EAST));

        // Assertion
        assertEquals(x, robotEntity.getPosition().getXCordinate());
        assertEquals(2, robotEntity.getPosition().getYCordinate());
        assertEquals(EAST, robotEntity.getPosition().getDirectionEnum());
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2, 3, 4, 5})
    public void shouldPlaceRobotInAnyValid_Y_Co_Ordinate(int y) {
        // Actual result
        robotService.place(new Position(2, y, EAST));

        // Assertion
        assertEquals(2, robotEntity.getPosition().getXCordinate());
        assertEquals(y, robotEntity.getPosition().getYCordinate());
        assertEquals(EAST, robotEntity.getPosition().getDirectionEnum());
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2, 3, 4, 5})
    public void shouldPlaceItRight(int y) {
        // Actual result
        robotService.place(new Position(2, y, EAST));

        // Assertion
        assertEquals(2, robotEntity.getPosition().getXCordinate());
        assertEquals(y, robotEntity.getPosition().getYCordinate());
        assertEquals(EAST, robotEntity.getPosition().getDirectionEnum());
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, -2})
    public void shouldThrowErrorIfInput_X_CoardinateIsOutOfBound(int x) {
        // Assertion
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            robotService.place(new Position(x, 3, EAST));
        });

        assertEquals(ERROR_MSG_OUT_OF_BOUNDRY, exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, -2})
    public void shouldThrowErrorIfInput_Y_CoardinateIsOutOfBound(int y) {
        // Assertion
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            robotService.place(new Position(3, y, EAST));
        });

        assertEquals(ERROR_MSG_OUT_OF_BOUNDRY, exception.getMessage());
    }

    @Test
    public void shouldThrowErrorWhenPositionIsNull() {
        // Assertion
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            robotService.place(null);
        });

        assertEquals(POSITION_IS_NOT_INITIALIZED, exception.getMessage());
    }


}




