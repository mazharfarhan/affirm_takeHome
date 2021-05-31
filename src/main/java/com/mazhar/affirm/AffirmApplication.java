package com.mazhar.affirm;

import com.mazhar.affirm.configurations.Constants;
import com.mazhar.affirm.helper.CSVFileParser;
import com.mazhar.affirm.service.FacilityMapperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
@Component
public class AffirmApplication implements CommandLineRunner {



	@Autowired
	private FacilityMapperService service;

	@Autowired
	private CSVFileParser csvFileParser;

	@Override
	public void run(String... args) {

		Map<Integer, Integer> loanFileMap = service.generateLoanToFacilityMapping();
		csvFileParser.createCSVFile(Constants.OUTPUT_BASE_PATH + Constants.ASSIGNMENTS_PATH, loanFileMap, Constants.LOAN_ID, Constants.FACILITY_ID);



	}

	public static void main(String[] args){
		SpringApplication.run(AffirmApplication.class, args);
	}
}
