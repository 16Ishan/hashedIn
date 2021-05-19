package com.hashedIn.station.serializer;

import com.hashedIn.station.model.Station;
import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

public class ReadCsv
{
    private String csvFileName;
    private List stationList;

    public ReadCsv(String csvFileName)
    {
        this.csvFileName = csvFileName;
    }

    public List<Station> readCsvFile()
    {
        try
        {
            CSVReader csvReader = new CSVReader(new FileReader(csvFileName));

            CsvToBean csvToBean = new CsvToBeanBuilder(csvReader)
                    .withType(Station.class)
                    .withIgnoreLeadingWhiteSpace(true).build();

            stationList = csvToBean.parse();
            csvReader.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return stationList;
    }
}
