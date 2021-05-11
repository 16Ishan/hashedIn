package com.hashedIn.service;

import com.hashedIn.entity.Show;
import com.hashedIn.exception.InvalidDateFormatException;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.List;

public interface NetflixService
{
    public List<Show> getShowList() throws IOException;
    public String outputShowDetails(Show show);
    public boolean validate(String startDt, String endDt)throws ParseException,
            InvalidDateFormatException;
    public void saveTvShows(Show show);
    public void saveTvShowsInCsv(Show show) throws IOException, URISyntaxException;
}
