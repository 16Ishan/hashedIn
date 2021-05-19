package com.hashedIn.station.service;

import com.hashedIn.station.model.Station;

public interface StationService
{
    public void sendArrivalRecord(Station station);
    public void sendStationRecord(Station station);
    public void sendTurnstileRecord(Station station);
    public void sendWeatherRecord(String toString);
}
