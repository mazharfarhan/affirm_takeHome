package com.mazhar.affirm.helper;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Service
@Slf4j
public class CSVFileParser {


    private static final String COMMA_DELIMITER = ",";

    /**
     * Method for reading the CSV file to get all the records from the file.
     * @param fileName Absolute path for the file name
     * @return Collection of records as list of list where each record/line in csv is indicated as a nested list object.
     */
    public List<List<String>> getRecords(String fileName){

        log.info("Reading the input file - {}", fileName);
        List<List<String>> records = new ArrayList<>();
        try(Scanner scanner = new Scanner(new File(fileName));){
            while(scanner.hasNextLine()){
                records.add(getRecordFromLine(scanner.nextLine()));
            }

        }
        catch (FileNotFoundException e) {
             log.error("ERROR-CODE:11 - The file passed to the csv parser doesn't exist , " +
                     "please verify the path - {}", fileName);
             return records;
        }
        return records;
    }


    /***
     * Method for parsing the individual line in the CSV file and getting individual cell values separated based on delimiter.
     * @param line The line read from the csv file.
     * @return  individual cell values returned as list of strings.
     */
    private List<String> getRecordFromLine(String line) {
        List<String> values = new ArrayList<>();
        try (Scanner rowScanner = new Scanner(line)) {
            rowScanner.useDelimiter(COMMA_DELIMITER);
            while (rowScanner.hasNext()) {
                values.add(rowScanner.next());
            }
        }
        return values;
    }


}
