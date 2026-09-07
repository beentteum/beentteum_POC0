package com.beenteum.recommendation.cafe;

public class Cafe {

    private final Long id;
    private final String name;
    private final boolean outlet;
    private final boolean wifi;
    private final NoiseLevel noiseLevel;

    public Cafe(
            Long id,
            String name,
            boolean outlet,
            boolean wifi,
            NoiseLevel noiseLevel
    ) {
        this.id = id;
        this.name = name;
        this.outlet = outlet;
        this.wifi = wifi;
        this.noiseLevel = noiseLevel;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean hasOutlet() {
        return outlet;
    }

    public boolean hasWifi() {
        return wifi;
    }

    public NoiseLevel getNoiseLevel() {
        return noiseLevel;
    }
}