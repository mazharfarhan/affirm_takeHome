package com.mazhar.affirm.service;

import com.mazhar.affirm.AffirmApplication;
import com.mazhar.affirm.helper.CSVFileParser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Map;

@SpringBootTest(classes = AffirmApplication.class)
public class FacilityMapperServiceIT {

    @Autowired
    private FacilityMapperService facilityMapperService;


    @Test
    public void testGenerateMapping(){
        Map<Integer, Integer> loanMap = facilityMapperService.generateLoanToFacilityMapping();
        Assert.notEmpty(loanMap, "Not empty");
    }


}
