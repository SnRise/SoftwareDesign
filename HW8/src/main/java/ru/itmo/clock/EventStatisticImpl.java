package ru.itmo.clock;

import java.time.Clock;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class EventStatisticImpl implements EventStatistic {
    private final int MINUTES_PER_HOUR = 60;

    private final Clock clock;
    private final Map<String, List<Instant>> events = new HashMap<>();

    public EventStatisticImpl(Clock clock) {
        this.clock = clock;
    }

    @Override
    public void incEvent(String name) {
        if (!events.containsKey(name)) {
            events.put(name, new ArrayList<>());
        }

        events.get(name).add(clock.instant());
    }

    @Override
    public double getEventStatisticByName(String name) {
        refreshEvents();

        if (!events.containsKey(name)) {
            return 0.0;
        }
        return calculateRpm(events.get(name));
    }

    @Override
    public Map<String, Double> getAllEventStatistic() {
        refreshEvents();

        return events.entrySet()
                .stream()
                .collect(
                        Collectors.toMap(
                                Map.Entry::getKey,
                                entry -> calculateRpm(entry.getValue())
                        )
                );
    }

    @Override
    public void printStatistic() {
        Map<String, Double> statistic = getAllEventStatistic();

        for (Map.Entry<String, Double> entry : statistic.entrySet()) {
            System.out.printf("Event %s, RPM=%.4f%n", entry.getKey(), entry.getValue());
        }
    }

    private double calculateRpm(List<Instant> instants) {
        return instants.size() / (double) MINUTES_PER_HOUR;
    }

    private void refreshEvents() {
        Instant hourAgo = clock.instant().minus(1, ChronoUnit.HOURS);

        for (Map.Entry<String, List<Instant>> entry : events.entrySet()) {
            List<Instant> freshInstants = entry.getValue().stream()
                    .filter(instant -> instant.isAfter(hourAgo))
                    .collect(Collectors.toList());

            events.put(entry.getKey(), freshInstants);
        }

        events.entrySet().removeIf(entry -> entry.getValue().isEmpty());
    }
}
