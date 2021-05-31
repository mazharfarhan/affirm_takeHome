package com.mazhar.affirm.service;

import com.mazhar.affirm.AffirmApplication;
import com.mazhar.affirm.helper.CSVFileParser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.util.List;

@SpringBootTest(classes = AffirmApplication.class)
public class FacilityMapperServiceIT {

    @Autowired
    private CSVFileParser csvFileParser;


    @Test
    public void testReadFile(){
        List<List<String>> records = csvFileParser.getRecords("src/input/banks.csv");
        Assert.notEmpty(records, "File is not empty");
    }


}
