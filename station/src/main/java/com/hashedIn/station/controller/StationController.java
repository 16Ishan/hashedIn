package com.hashedIn.station.controller;

import com.hashedIn.station.model.Station;
import com.hashedIn.station.serializer.ReadCsv;
import com.hashedIn.station.service.StationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("station")
public class StationController
{
    private ReadCsv readCsv = new ReadCsv("data/stations.csv");

    @Autowired
    StationService stationService;

    @GetMapping("/stations")
    public String postStations()
    {
        List<Station> stationsList = readCsv.readCsvFile();

        stationsList.stream()
                .forEach(station -> stationService.sendStationRecord(station));
        return "All Stations Published Successfully";
    }

    @GetMapping("/arrivals/{stationId}")
    public String postArrival(@PathVariable("stationId") int stationId)
    {
        List<Station> stationsList = readCsv.readCsvFile();

        stationsList.stream()
                .filter(station -> station.getStationId() == stationId)
                .forEach(station -> stationService.sendArrivalRecord(station));
        return "Station Arrival Published Successfully";
    }

    @GetMapping("/turnstile/{stationId}")
    public String postTurnstile(@PathVariable("stationId") int stationId)
    {
        List<Station> stationsList = readCsv.readCsvFile();

        stationsList.stream()
                .filter(station -> station.getStationId() == stationId)
                .forEach(station -> stationService.sendTurnstileRecord(station));
        return "Turnstile Arrival Published Successfully";
    }
    
    @GetMapping("/weather/{location}")
    public String postWeather(@PathVariable("location") String location)
    {
        String API_KEY = "fc8b05e35f754a5de8e5a9df5c7ef24f";
        String urlString = "http://api.openweathermap.org/data/2.5/weather?q="
                +location+"&appid="+API_KEY+"&units=imperial";

        StringBuilder weatherResult = new StringBuilder();
        try
        {

            URL url = new URL(urlString);
            URLConnection con = url.openConnection();

            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));

            String line;
            while((line=br.readLine()) != null)
            {
                weatherResult.append(line);
            }

            br.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        stationService.sendWeatherRecord(weatherResult.toString());
        return "Weather Published Successfully";
    }
}
