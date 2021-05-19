package com.hashedIn.station.model;

public class Turnstile
{
    private int stationId;
    private String stationName;
    private String line;

    public Turnstile()
    {

    }

    public Turnstile(int stationId, String stationName, String line) {
        this.stationId = stationId;
        this.stationName = stationName;
        this.line = line;
    }

    public int getStationId() {
        return stationId;
    }

    public void setStationId(int stationId) {
        this.stationId = stationId;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }
}
