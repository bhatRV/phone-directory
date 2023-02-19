package com.unit.test.rv.service;

import com.rv.PhoneDirectoryApplication;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import static com.rv.entities.DirectionFacingEnum.EAST;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = PhoneDirectoryApplication.class)
@ActiveProfiles("test")
public class RobotRightLeftAPITest {

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
    public void shouldGetSuccessWhenDirectionChangesFromEastToSouth() {
        //place
        robotService.place(new Position(2, 2, EAST));

        //report
        robotService.left();

        // Assertion on report
        assertEquals("Robot is at 2,2,NORTH", robotService.report());

    }

    @Test
    public void shouldGetSuccessWhenDirectionChangesFromEastToSOUTH() {
        //place
        robotService.place(new Position(2, 2, EAST));

        //report
        robotService.right();

        // Assertion on report
        assertEquals("Robot is at 2,2,SOUTH", robotService.report());

    }


}




