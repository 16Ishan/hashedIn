package com.hashedIn.controller;

import com.hashedIn.entity.Show;
import com.hashedIn.exception.InvalidDateFormatException;
import com.hashedIn.exception.InvalidSourceException;
import com.hashedIn.service.NetflixServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.List;

@RestController
public class NetflixRestController
{
    @Autowired
    private NetflixServiceImpl netflixServiceImpl;

    @GetMapping("/tvShows")
    public String fetchTvShows(@RequestParam int count, HttpServletResponse response)
            throws IOException
    {
        //Log the start time of the process
        long startTime = System.currentTimeMillis();

        //Read and retrieve the shows list from the file
        List<Show> showsList = netflixServiceImpl.getShowList();
        StringBuilder output = new StringBuilder();

        //Iterate through the list and append first n TV show's details into output
        showsList.stream().filter(show -> show.getType().equals("TV Show"))
                .limit(count)
                .forEach(show -> output.append(netflixServiceImpl.outputShowDetails(show)));

        //Log the end time of the process
        long endTime = System.currentTimeMillis();

        //Set the time taken to execute the process into the response header
        response.setHeader("X-TIME-TO-EXECUTE",(endTime-startTime)+"mS");
        return output.toString();
    }

    @GetMapping("/tvShows/movies")
    public String fetchMoviesByType(@RequestParam String movieType, HttpServletResponse response)
            throws IOException
    {
        //Log the start time of the process
        long startTime = System.currentTimeMillis();

        //Read and retrieve the shows list from the file
        List<Show> showsList = netflixServiceImpl.getShowList();
        StringBuilder output = new StringBuilder();

        /*Iterate through the list and append each
          show's details where listed_in=MovieType into output*/
        showsList.stream()
                .filter(show -> show.getListedIn().contains(movieType))
                .forEach(show -> output.append(netflixServiceImpl.outputShowDetails(show)));

        //Log the end time of the process
        long endTime = System.currentTimeMillis();

        //Set the time taken to execute the process into the response header
        response.setHeader("X-TIME-TO-EXECUTE",(endTime-startTime)+"mS");

        return output.toString();
    }

    @GetMapping("/tvShows/movies/region")
    public String fetchMoviesByRegion(@RequestParam String country, HttpServletResponse response)
            throws IOException
    {
        //Log the start time of the process
        long startTime = System.currentTimeMillis();

        //Read and retrieve the shows list from the file
        List<Show> showsList = netflixServiceImpl.getShowList();
        StringBuilder output = new StringBuilder();

        /*Iterate through the list and append each
          show's details where type=Movie and country=given country into output*/
        showsList.stream()
                .filter(show -> show.getType().equals("Movie"))
                .filter(show -> show.getCountry().equals(country))
                .forEach(show -> output.append(netflixServiceImpl.outputShowDetails(show)));

        //Log the end time of the process
        long endTime = System.currentTimeMillis();

        //Set the time taken to execute the process into the response header
        response.setHeader("X-TIME-TO-EXECUTE",(endTime-startTime)+"mS");

        return output.toString();
    }

    @GetMapping("/tvShows/dateAdded")
    public String fetchTvShowsBetweenDateAdded(@RequestParam String startDate,
                                               @RequestParam String endDate,
                                               HttpServletResponse response)throws IOException,
                                                InvalidDateFormatException, ParseException
    {
        //Log the start time of the process
        long startTime = System.currentTimeMillis();

        /*Validate whether the startDate and endDate
          parameters are in correct format and are valid dates*/
        netflixServiceImpl.validate(startDate,endDate);

        //Read and retrieve the shows list from the file
        List<Show> showsList = netflixServiceImpl.getShowList();
        StringBuilder output = new StringBuilder();

        //Iterate through the list and append each show's details into output
        showsList.stream()
                .filter(NetflixServiceImpl::dateRange)
                .forEach(show -> output.append(netflixServiceImpl.outputShowDetails(show)));

        //Log the end time of the process
        long endTime = System.currentTimeMillis();

        //Set the time taken to execute the process into the response header
        response.setHeader("X-TIME-TO-EXECUTE",(endTime-startTime)+"mS");
        return output.toString();
    }

    @PostMapping("/saveTvShows")
    public void saveTvShows(HttpServletResponse response)
            throws IOException
    {
        //Log the start time of the process
        long startTime = System.currentTimeMillis();

        //Read and retrieve the shows list from the file
        List<Show> showsList = netflixServiceImpl.getShowList();

        //Save each show from the list into the database
        showsList.stream()
                .forEach(show -> netflixServiceImpl.saveTvShows(show));

        //Log the end time of the process
        long endTime = System.currentTimeMillis();

        //Set the time taken to execute the process into the reponse header
        response.setHeader("X-TIME-TO-EXECUTE",(endTime-startTime)+"mS");
    }

    @PostMapping("/saveTvShows/datasource")
    public void saveTvShowsByDataSource(@RequestBody Show show,
                                        @RequestParam String source,HttpServletResponse response)
            throws IOException, InvalidSourceException, URISyntaxException {
        //Log the start time of the process
        long startTime = System.currentTimeMillis();

        //Check whether source parameter is sql datasource
        if(source.equals("sql"))
        {
            netflixServiceImpl.saveTvShows(show);
        }
        //Check whether source parameter is csv datasource
        else if(source.equals("csv"))
        {
            netflixServiceImpl.saveTvShowsInCsv(show);
        }
        //Throw Exception if source is invalid
        else
        {
            throw new InvalidSourceException("Invalid Source Provided");
        }

        //Log the end time of the process
        long endTime = System.currentTimeMillis();
        //Set the time taken to execute the process into the reponse header
        response.setHeader("X-TIME-TO-EXECUTE",(endTime-startTime)+"mS");
    }
}