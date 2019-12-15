package com.afkl.travel.exercise.metrics;

import io.micrometer.core.instrument.Meter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tag;
import io.micrometer.core.instrument.composite.CompositeMeterRegistry;
import io.micrometer.core.instrument.cumulative.CumulativeTimer;
import io.micrometer.core.instrument.util.StringUtils;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;

@Endpoint(id = "stats")
@Component
public class MetricReduction {

    private final MeterRegistry registry;

    public MetricReduction(MeterRegistry registry) {
        this.registry = registry;
    }

    @ReadOperation
    public Stats listNames() {
        final Collection<Meter> httpMeters = findFirstMatchingMeters(registry, "http.server.requests", Collections.emptyList());

        final long total = reduceCount(httpMeters, x -> true);
        final long ok = reduceCount(httpMeters, this::isOk);
        final long fourXX = reduceCount(httpMeters, this::is4XX);
        final long fiveXX = reduceCount(httpMeters, this::is5XX);

        final double totalTime = reduceTime(httpMeters);
        final double average = total == 0 ? 0 : totalTime / total;
        final double maxTime = maxTime(httpMeters);
        return new Stats()
                .setFiveXX(fiveXX)
                .setFourXX(fourXX)
                .setOk(ok)
                .setTotal(total)
                .setMaxTime(maxTime)
                .setAverage(average)
                .setTotalTime(totalTime)
                .setTotal(total);
    }

    private Collection<Meter> findFirstMatchingMeters(MeterRegistry registry, String name,
                                                      Iterable<Tag> tags) {
        if (registry instanceof CompositeMeterRegistry) {
            return findFirstMatchingMeters((CompositeMeterRegistry) registry, name, tags);
        }
        return registry.find(name).tags(tags).meters();
    }

    private Collection<Meter> findFirstMatchingMeters(CompositeMeterRegistry composite,
                                                      String name, Iterable<Tag> tags) {
        return composite.getRegistries().stream()
                .map((registry) -> findFirstMatchingMeters(registry, name, tags))
                .filter((matching) -> !matching.isEmpty()).findFirst()
                .orElse(Collections.emptyList());
    }

    private double maxTime(Collection<Meter> httpMeters) {
        return httpMeters.stream().map(x -> ((CumulativeTimer) x).max(TimeUnit.SECONDS)).reduce(Double::max).orElse(0.);
    }

    private double reduceTime(Collection<Meter> httpMeters) {
        return httpMeters.stream().map(x -> ((CumulativeTimer) x).totalTime(TimeUnit.SECONDS)).reduce(Double::sum).orElse(0.);
    }

    private long reduceCount(Collection<Meter> httpMeters, Predicate<Meter> filter) {
        return httpMeters.stream().filter(filter).map(x -> ((CumulativeTimer) x).count()).reduce(Long::sum).orElse(0L);
    }

    private boolean isOk(Meter meter) {
        return Objects.equals(meter.getId().getTag("outcome"), "SUCCESS");
    }

    private boolean is4XX(Meter meter) {
        final String status = meter.getId().getTag("status");
        return !StringUtils.isBlank(status) && status.startsWith("4");
    }

    private boolean is5XX(Meter meter) {
        final String status = meter.getId().getTag("status");
        return !StringUtils.isBlank(status) && status.startsWith("5");
    }


}
