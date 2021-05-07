package com.hashedIn.assignment.functional.main;

import com.hashedIn.assignment.functional.exception.InvalidDateFormatException;
import com.hashedIn.assignment.functional.pojo.Show;

import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Netflix
{
    private static Date startDate;
    private static Date endDate;

    public static void main(String args[]) throws IOException,
            ParseException, InvalidDateFormatException
    {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter file path:");
        String filePath = scanner.nextLine();
        //Parse the input csv file and store all the shows in a list
        List<Show> showsList = getShowList(filePath);

        int proceed;
        do
        {
            //Input the value of n(number of records) from the user
            System.out.println("Enter the value of n:");
            int n = scanner.nextInt();
            scanner.nextLine();

            //Input start date from the user for upper limit
            System.out.println("Enter Start Date in dd-MM-yyyy format:");
            String startDateStr = scanner.nextLine();
            if(!isValidDateFormat(startDateStr))
            {
                throw new InvalidDateFormatException("Invalid Date Entered...");
            }

            //Input end date from the user for lower limit
            System.out.println("Enter End Date in dd-MM-yyyy format:");
            String endDateStr = scanner.nextLine();
            if(!isValidDateFormat(endDateStr))
            {
                throw new InvalidDateFormatException("Invalid Date Entered...");
            }

            //Parse the input start date and end date strings into dd-MM-yyyy
            startDate = dateParser(startDateStr,"dd-MM-yyyy");
            endDate = dateParser(endDateStr,"dd-MM-yyyy");

            //Print out the user menu and input choice
            System.out.println("\n1. List the first "+ n +" records where type: TV Show " +
                               "and date_added lies between given Start Date and End Date.\n" +
                    "2. List the first "+ n +" records where listed_in: Horror Movies (may contain other values) " +
                    "and date_added lies between given Start Date and End Date.\n" +
                    "3. List the first " +n +" type: Movie where country: India " +
                    "and date_added lies between given Start Date and End Date.\n" +
                    "Enter your choice:");
            int choice = scanner.nextInt();

            System.out.println("Show Id\tType\tTitle\tDirector\tCast\tCountry\tDate Added\t" +
                    "Release Year\tRating\tDuration\tListed In\tDescription");
            switch(choice)
            {
                case 1:
                    //Filter the list based on type: TV Show and startDate <= dateAdded <= endDate
                    showsList.stream()
                            .filter(show -> show.getType().equals("TV Show"))
                            .filter(Netflix::dateRange)
                            .limit(n)
                            .forEach(Netflix::displayShowDetails);
                    break;

                case 2:
                    //Filter the list based on listedIn: Horror Movies and startDate <= dateAdded <= endDate
                    showsList.stream()
                            .filter(show -> show.getListedIn().contains("Horror Movies"))
                            .filter(Netflix::dateRange)
                            .limit(n)
                            .forEach(Netflix::displayShowDetails);
                    break;

                case 3:
                    //Filter the list based on type: Movie, country: India and startDate <= dateAdded <= endDate
                    showsList.stream()
                            .filter(show -> show.getType().equals("Movie"))
                            .filter(show -> show.getCountry().equals("India"))
                            .filter(Netflix::dateRange)
                            .limit(n)
                            .forEach(Netflix::displayShowDetails);
                    break;

                default:
                    //In case of invalid input
                    System.out.println("Invalid Input...Try Again.");
            }

            //Ask whether to proceed or not
            System.out.println("\nContinue?\n1. Yes\n2. No\n");
            proceed = scanner.nextInt();
        }while(proceed == 1);
    }

    /*
        Parses the csv file using the file path
        and creates show objects using each row
     */
    private static List<Show> getShowList(String filePath) throws IOException
    {
        BufferedReader netflixReader = new BufferedReader(new FileReader(filePath));
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
    private static Show createShow(String[] data)
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
        if(dateStr.equals(""))
        {
            return null;
        }
        if(dateFormat.equals("MMMM d, yyyy"))
        {
            //Remove the double quotes at the beginning and end of date and trim any whitespaces
            dateStr = dateStr.substring(1,dateStr.length()-1).trim();
        }

        //Convert the date string into the given date format
        DateFormat format = new SimpleDateFormat(dateFormat, Locale.ENGLISH);
        return format.parse(dateStr);
    }

    /*
        Prints out the show data
     */
    private static void displayShowDetails(Show show)
    {
        System.out.print(show.getShowId()+"\t"+show.getType()+"\t"+show.getTitle()
                +"\t"+show.getDirector()+"\t"+show.getCast()+"\t"+show.getCountry()
                +"\t"+show.getDateAdded()+"\t"+show.getReleaseYear()+"\t"
                +show.getRating()+"\t"+show.getDuration()+"\t"+show.getDescription()+"\t");
        System.out.println();
    }

    /*
        Verify whether the input show's dateAdded lies in the range of startDate and endDate
     */
    private static boolean dateRange(Show show)
    {
        try
        {
            Date date = dateParser(show.getDateAdded(),"MMMM d, yyyy");
            if(date == null)
            {
                return false;
            }
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
        Verifies whether the date is valid and in the dd-MM-yyyy format
     */
    private static boolean isValidDateFormat(String dateStr)
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
    private static boolean isLeap(int year)
    {
        if (((year % 4 == 0) && (year % 100!= 0)) || (year%400 == 0))
            return true;

        return false;
    }
}