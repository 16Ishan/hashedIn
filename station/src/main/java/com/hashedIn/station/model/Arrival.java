package com.hashedIn.station.model;

public class Arrival
{
    private int stationId;
    private String trainId;
    private String directionId;
    private String line;
    private int prevStationId;
    private String prevDirectionId;

    public Arrival()
    {

    }

    public Arrival(int stationId, String trainId, String directionId,
                   String line, int prevStationId, String prevDirectionId) {
        this.stationId = stationId;
        this.trainId = trainId;
        this.directionId = directionId;
        this.line = line;
        this.prevStationId = prevStationId;
        this.prevDirectionId = prevDirectionId;
    }

    public int getStationId() {
        return stationId;
    }

    public void setStationId(int stationId) {
        this.stationId = stationId;
    }

    public String getTrainId() {
        return trainId;
    }

    public void setTrainId(String trainId) {
        this.trainId = trainId;
    }

    public String getDirectionId() {
        return directionId;
    }

    public void setDirectionId(String directionId) {
        this.directionId = directionId;
    }

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }

    public int getPrevStationId() {
        return prevStationId;
    }

    public void setPrevStationId(int prevStationId) {
        this.prevStationId = prevStationId;
    }

    public String getPrevDirectionId() {
        return prevDirectionId;
    }

    public void setPrevDirectionId(String prevDirectionId) {
        this.prevDirectionId = prevDirectionId;
    }
}
