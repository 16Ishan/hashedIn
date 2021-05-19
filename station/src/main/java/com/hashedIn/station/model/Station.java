package com.hashedIn.station.model;

import com.opencsv.bean.CsvBindByName;

public class Station
{
    @CsvBindByName(column = "stop_id")
    private int stopId;
    @CsvBindByName(column = "direction_id")
    private String directionId;
    @CsvBindByName(column = "stop_name")
    private String stopName;
    @CsvBindByName(column = "station_name")
    private String stationName;
    @CsvBindByName(column = "station_descriptive_name")
    private String stationDescriptiveName;
    @CsvBindByName(column = "station_id")
    private int stationId;
    @CsvBindByName(column = "order")
    private int order;
    @CsvBindByName(column = "red")
    private boolean red;
    @CsvBindByName(column = "blue")
    private boolean blue;
    @CsvBindByName(column = "green")
    private boolean green;

    public Station()
    {

    }
    public Station(int stopId, String directionId, String stopName, String stationName,
                   String stationDescriptiveName, int stationId, int order,
                   boolean red, boolean blue, boolean green) {
        this.stopId = stopId;
        this.directionId = directionId;
        this.stopName = stopName;
        this.stationName = stationName;
        this.stationDescriptiveName = stationDescriptiveName;
        this.stationId = stationId;
        this.order = order;
        this.red = red;
        this.blue = blue;
        this.green = green;
    }

    public int getStopId() {
        return stopId;
    }

    public void setStopId(int stopId) {
        this.stopId = stopId;
    }

    public String getDirectionId() {
        return directionId;
    }

    public void setDirectionId(String directionId) {
        this.directionId = directionId;
    }

    public String getStopName() {
        return stopName;
    }

    public void setStopName(String stopName) {
        this.stopName = stopName;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public String getStationDescriptiveName() {
        return stationDescriptiveName;
    }

    public void setStationDescriptiveName(String stationDescriptiveName) {
        this.stationDescriptiveName = stationDescriptiveName;
    }

    public int getStationId() {
        return stationId;
    }

    public void setStationId(int stationId) {
        this.stationId = stationId;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public boolean isRed() {
        return red;
    }

    public void setRed(boolean red) {
        this.red = red;
    }

    public boolean isBlue() {
        return blue;
    }

    public void setBlue(boolean blue) {
        this.blue = blue;
    }

    public boolean isGreen() {
        return green;
    }

    public void setGreen(boolean green) {
        this.green = green;
    }
}
