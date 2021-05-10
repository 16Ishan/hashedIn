package com.hashedIn.repository;

import com.hashedIn.entity.Show;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class NetflixRepositoryImpl implements NetflixRepository
{
    @Autowired
    private JdbcTemplate jdbcTemplate;

    /*
        Insert the input show's details
        into the database table
     */
    @Override
    public void saveTvShows(Show show)
    {
        String saveTvShowsQuery = "insert ignore into netflix_show_details" +
                "(show_id,type,title,director,cast,country,date_added," +
                "release_year,rating,duration,listed_in,description) " +
                "values(?,?,?,?,?,?,?,?,?,?,?,?)";
        this.jdbcTemplate.update(saveTvShowsQuery,new Object[]{
                show.getShowId(),show.getType(),show.getTitle(),show.getDirector(),
                show.getCast(),show.getCountry(),show.getDateAdded(),show.getReleaseYear(),
                show.getRating(),show.getDuration(),show.getListedIn(),show.getDescription()
        });
    }
}
