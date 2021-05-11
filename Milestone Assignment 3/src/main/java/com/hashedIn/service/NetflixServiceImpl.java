package com.hashedIn.service;

import com.hashedIn.repository.NetflixRepository;
import com.hashedIn.entity.Show;
import com.hashedIn.exception.InvalidDateFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Service
public class NetflixServiceImpl implements NetflixService
{
    @Autowired
    private NetflixRepository netflixRepository;

    private static Date startDate;
    private static Date endDate;

    private StringBuilder output = new StringBuilder();

    public StringBuilder getOutput()
    {
        return output;
    }

    /*
        Parses the csv file using the file path
        and creates show objects using each row
     */
    public List<Show> getShowList() throws IOException
    {
        /*BufferedReader netflixReader = new BufferedReader(
                new FileReader("D:\\Downloads\\netflix_titles.csv"));*/
        BufferedReader netflixReader = new BufferedReader(
                new InputStreamReader(getClass().getClassLoader()
                        .getResourceAsStream("netflix_titles.csv")));
        netflixReader.readLine();

        List<Show> showsList = new ArrayList<>();

        String row;
        while ((row = netflixReader.readLine()) != null)
        {
            /* Split the row String using ',' but not ', ' since the data inside
               double quotes(" ") does not require split
             */
            String[] rowData = row.split(",(?! )");

            //Create show object using each index of rowData as a separate column
            Show show = createShow(rowData);

            //Add the show object to the showsList
            showsList.add(show);
        }

        return showsList;
    }

    /*
        Considers each index of data[] as a separate column
        and constructs the show object
     */
    private Show createShow(String[] data)
    {
        String showId = data[0];
        String type = data[1];
        String title = data[2];
        String director = data[3];
        String cast = data[4];
        String country = data[5];
        String dateAdded = data[6];
        String releaseYear = data[7];
        String rating = data[8];
        String duration = data[9];
        String listedIn = data[10];
        String description = data[11];

        return new Show(showId,type,title,director,cast,
                country,dateAdded,releaseYear,rating,duration,
                listedIn,description);
    }

    /*
        Parse the input date into the given format
     */
    private static Date dateParser(String dateStr, String dateFormat) throws ParseException
    {
        //Return null if input date is blank
        if(dateStr.isEmpty() || dateStr.isBlank())
        {
            return null;
        }

        if(dateFormat.equals("MMMM d, yyyy"))
        {
            if(dateStr.charAt(0) == '\"' && dateStr.charAt(dateStr.length()-1) == '\"')
            {
                //Remove the double quotes at the beginning and end of date and trim any whitespaces
                dateStr = dateStr.substring(1,dateStr.length()-1).trim();
            }
        }

        //Convert the date string into the given date format
        DateFormat format = new SimpleDateFormat(dateFormat, Locale.ENGLISH);
        return format.parse(dateStr);
    }

    /*
        Prints out the show data
     */
    public String outputShowDetails(Show show)
    {
          return show.getShowId()+"\t"+show.getType()+"\t"+show.getTitle()
            +"\t"+show.getDirector()+"\t"+show.getCast()+"\t"+show.getCountry()
            +"\t"+show.getDateAdded()+"\t"+show.getReleaseYear()+"\t"
            +show.getRating()+"\t"+show.getDuration()+"\t"+show.getListedIn()
            +"\t"+show.getDescription()+"\n";
    }

    /*
        Verify whether the input show's dateAdded lies in the range of startDate and endDate
     */
    public static boolean dateRange(Show show)
    {
        try
        {
            Date date = dateParser(show.getDateAdded(),"MMMM d, yyyy");
            //If input date is null, return false
            if(date == null)
            {
                return false;
            }
            //if input date lies between startDate and endDate (both inclusive) return true
            if((date.after(startDate) && date.before(endDate))
                    || date.equals(startDate)
                    || date.equals(endDate))
            {
                return true;
            }
        }
        catch(ParseException e)
        {
            e.printStackTrace();
        }

        return false;
    }

    /*
        Check whether the input dates are valid
     */
    public boolean validate(String startDt, String endDt) throws ParseException,
            InvalidDateFormatException
    {
        if(isValidDateFormat(startDt)
                && isValidDateFormat(endDt))
        {
            startDate = dateParser(startDt,"dd-MM-yyyy");
            endDate = dateParser(endDt,"dd-MM-yyyy");
            return true;
        }
        else
        {
            throw new InvalidDateFormatException("Invalid Date or Date should be in dd-MM-yyyy format");
        }
    }

    /*
        Saves the Show object into the database using
        Repository object
     */
    @Override
    public void saveTvShows(Show show)
    {
        //Remove any "" present in the data
        show.setCast(parseString(show.getCast()));
        show.setDateAdded(parseString(show.getDateAdded()));
        show.setListedIn(parseString(show.getListedIn()));
        show.setDescription(parseString(show.getDescription()));
        show.setCountry(parseString(show.getCountry()));

        netflixRepository.saveTvShows(show);
    }

    /*
        Appends the Show details into the file
     */
    @Override
    public void saveTvShowsInCsv(Show show) throws IOException
    {
        File netflixFile = new File("./src/main/resources/netflix_titles.csv");
        FileWriter netflixFileWriter = new FileWriter(netflixFile, true);
        BufferedWriter netflixFileBufferedWriter = new BufferedWriter(netflixFileWriter);

        netflixFileBufferedWriter.write("\n"+show.getShowId()+","+show.getType()+","+show.getTitle()
                +","+show.getDirector()+","+show.getCast()+","+show.getCountry()
                +","+show.getDateAdded()+","+show.getReleaseYear()+","
                +show.getRating()+","+show.getDuration()+","+show.getListedIn()
                +","+show.getDescription());

        netflixFileBufferedWriter.close();
        netflixFileWriter.close();
    }

    /*
        Removes the " present in the input string
     */
    private String parseString(String str)
    {
        if(!(str.isBlank() || str.isEmpty()))
        {
            if(str.charAt(0) == '\"' && str.charAt(str.length()-1) == '\"')
            {
                str = str.substring(1,str.length()-1);
            }
        }

        return str;
    }

    /*
        Verifies whether the date is valid and in the dd-MM-yyyy format
     */
    private boolean isValidDateFormat(String dateStr)
    {
        String date[] = dateStr.split("-");

        if(date.length<3)
        {
            return false;
        }

        int monthDays[] = {31,28,31,30,31,30,31,31,30,31,30,31};
        int day = Integer.parseInt(date[0]);
        int month = Integer.parseInt(date[1]);
        int year = Integer.parseInt(date[2]);

        //If the year is leap then make Feb days as 29
        if(isLeap(year))
        {
            monthDays[1] = 29;
        }

        //Check if month value is between 1-12
        if(month<1 || month>12)
        {
            return false;
        }

        //Check if date value is between 1-31 and day value lies in the range of month days
        if(day<1 || day>31 || (day>monthDays[month-1]))
        {
            return false;
        }

        //Check if year lies between 0-9999
        if(year<0 || year>9999)
        {
            return false;
        }

        return true;
    }

    /*
        Checks for a leap year
     */
    private boolean isLeap(int year)
    {
        if (((year % 4 == 0) && (year % 100!= 0)) || (year%400 == 0))
            return true;

        return false;
    }
}