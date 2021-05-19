package com.hashedIn.station.service;

import com.hashedIn.station.model.Arrival;
import com.hashedIn.station.model.Station;
import com.hashedIn.station.model.Turnstile;
import com.hashedIn.station.serializer.ReadCsv;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StationServiceImpl implements StationService
{
    @Autowired
    KafkaTemplate<String, Station> stationTemplate;
    @Autowired
    KafkaTemplate<String,Arrival> arrivalTemplate;
    @Autowired
    KafkaTemplate<String,Turnstile> turnstileTemplate;
    @Autowired
    KafkaTemplate<String,String> weatherTemplate;

    private static final String TOPIC_ARRIVALS = "org.station.arrivals";
    private static final String TOPIC_STATIONS = "org.station.stations";
    private static final String TOPIC_TURNSTILES = "org.station.turnstiles";
    private static final String TOPIC_WEATHER = "org.station.weather";

    private ReadCsv readCsv = new ReadCsv("data/stations.csv");

    public void sendArrivalRecord(Station station)
    {
        Arrival trainArrival = new Arrival();

        trainArrival.setStationId(station.getStationId());
        trainArrival.setTrainId("Train No. TR00"+ Math.random());
        trainArrival.setDirectionId(station.getDirectionId());
        trainArrival.setLine(getTrainLine(station));

        Station prevStation = getPreviousStation(station.getStationId());
        if(!(prevStation == null))
        {
            trainArrival.setPrevStationId(prevStation.getStationId());
            trainArrival.setPrevDirectionId(prevStation.getDirectionId());
        }

        ProducerRecord<String,Arrival> producerRecord = new
                ProducerRecord<String,Arrival>(TOPIC_ARRIVALS,trainArrival);
        arrivalTemplate.send(producerRecord);
    }

    private String getTrainLine(Station station)
    {
        if(station.isRed())
            return "Red";
        else if (station.isGreen())
            return "Green";
        else
            return "Blue";
    }

    private Station getPreviousStation(int stationId)
    {
        List<Station> stationsList = readCsv.readCsvFile();

        for(int st = 0;st<stationsList.size();st++)
        {
            if(stationsList.get(st).getStationId() == stationId)
            {
                return stationsList.get(st-1);
            }
        }

        return null;
    }

    public void sendStationRecord(Station station)
    {
        ProducerRecord<String,Station> producerRecord = new
                ProducerRecord<String,Station>(TOPIC_STATIONS,station);
        stationTemplate.send(producerRecord);
    }

    @Override
    public void sendTurnstileRecord(Station station)
    {
        Turnstile turnstile = new Turnstile();

        turnstile.setStationId(station.getStationId());
        turnstile.setStationName(station.getStationName());
        turnstile.setLine(getTrainLine(station));

        ProducerRecord<String,Turnstile> producerRecord = new
                ProducerRecord<String,Turnstile>(TOPIC_TURNSTILES,turnstile);
        turnstileTemplate.send(producerRecord);
    }

    @Override
    public void sendWeatherRecord(String weather)
    {
        ProducerRecord<String,String> producerRecord = new
                ProducerRecord<String,String>(TOPIC_WEATHER,weather);
        weatherTemplate.send(producerRecord);
    }
}
