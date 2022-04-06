package ru.itmo.clock;

import java.time.Instant;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class EventStatisticImplTest {
    private SetableClock clock;
    private EventStatistic eventStatistic;

    @BeforeEach
    public void init() {
        clock = new SetableClock(Instant.now());
        eventStatistic = new EventStatisticImpl(clock);
    }

    @Test
    public void getEventStatisticByNameEmptyTest() {
        assertEquals(0.0, eventStatistic.getEventStatisticByName("Event"));
    }

    @Test
    public void getAllEventStatisticEmptyTest() {
        assertTrue(eventStatistic.getAllEventStatistic().isEmpty());
    }

    @Test
    public void getEventStatisticByNameTest() {
        for (int i = 0; i < 6; i++) {
            eventStatistic.incEvent("First Event");
        }
        eventStatistic.incEvent("Second Event");

        assertEquals(0.1, eventStatistic.getEventStatisticByName("First Event"));
    }

    @Test
    public void getAllEventStatisticTest() {
        eventStatistic.incEvent("First Event");
        eventStatistic.incEvent("Second Event");

        clock.plusMinutes(30);
        eventStatistic.incEvent("Third Event");

        double expectedRpm = 1.0 / 60;

        Map<String, Double> expectedStatistic = Map.of(
                "First Event", expectedRpm,
                "Second Event", expectedRpm,
                "Third Event", expectedRpm
        );
        assertEquals(expectedStatistic, eventStatistic.getAllEventStatistic());
    }

    @Test
    public void getEventStatisticByNameLateTest() {
        for (int i = 0; i < 20; i++) {
            eventStatistic.incEvent("Event");
            clock.plusMinutes(5);
        }

        assertEquals(11.0 / 60, eventStatistic.getEventStatisticByName("Event"));
    }

    @Test
    public void getAllEventStatisticLateTest() {
        eventStatistic.incEvent("First Event");
        eventStatistic.incEvent("Second Event");
        clock.plusMinutes(30);

        eventStatistic.incEvent("Second Event");
        clock.plusMinutes(30);

        eventStatistic.incEvent("Third Event");
        eventStatistic.incEvent("Second Event");

        Map<String, Double> expectedStatistic = Map.of(
                "Second Event", 1.0 / 30,
                "Third Event", 1.0 / 60
        );

        assertEquals(expectedStatistic, eventStatistic.getAllEventStatistic());
    }
}
