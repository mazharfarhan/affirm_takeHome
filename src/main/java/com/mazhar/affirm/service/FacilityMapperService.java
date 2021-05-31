package com.mazhar.affirm.service;

import com.mazhar.affirm.configurations.Constants;
import com.mazhar.affirm.helper.CSVFileParser;
import com.mazhar.affirm.models.Bank;
import com.mazhar.affirm.models.Covenant;
import com.mazhar.affirm.models.Facility;
import com.mazhar.affirm.models.Loan;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;

@Service
@Slf4j
public class FacilityMapperService {


    @Autowired
    private CSVFileParser csvFileParser;

    private List<Bank> bankData = new ArrayList<>();
    private List<Facility> facilitiesData = new ArrayList<>();
    private List<Loan> loansData = new ArrayList<>();
    private List<Covenant> covenantsData = new ArrayList<>();

    private void generateInputDomainData() throws IllegalArgumentException{
        List<List<String>> bankDataList = csvFileParser.getRecords(Constants.BASE_PATH + Constants.BANKS_PATH);
        List<List<String>> facilitiesDataList = csvFileParser.getRecords(Constants.BASE_PATH + Constants.FACILITIES_PATH);
        List<List<String>> covenantsDataList = csvFileParser.getRecords(Constants.BASE_PATH + Constants.COVENANTS_PATH);
        List<List<String>> loansDataList = csvFileParser.getRecords(Constants.BASE_PATH + Constants.LOANS_PATH);

        if(CollectionUtils.isEmpty(bankDataList) || CollectionUtils.isEmpty(facilitiesDataList) || CollectionUtils.isEmpty(covenantsDataList) ||
                CollectionUtils.isEmpty(loansDataList)){
            log.error("ERROR-CODE:12 - The data passed to the program is invalid, please verify if all the data files are valid");
            throw new IllegalArgumentException("Invalid data passed to the program");
        }

        for(int i = 1; i < bankDataList.size(); i++){
            bankData.add(new Bank(bankDataList.get(i)));
        }

        for(int i = 1; i < facilitiesDataList.size(); i++){
            facilitiesData.add(new Facility(facilitiesDataList.get(i)));
        }

        for(int i = 1; i < loansDataList.size(); i++){
            loansData.add(new Loan(loansDataList.get(i)));
        }

        for(int i = 1; i < covenantsDataList.size(); i++){
            covenantsData.add(new Covenant(covenantsDataList.get(i)));
        }
    }

    public Map<Integer, Integer> generateLoanToFacilityMapping(){

        generateInputDomainData();
        Map<Integer, List<Facility>> bankToFacilityMap = getBankToFacilityMap();
        Map<Integer, List<Covenant>> facilityCovenantMap = getFacilityCovenantMap(bankToFacilityMap);

        Collections.sort(facilitiesData, Comparator.comparingDouble(Facility::getInterestRate));
        Map<Integer, Integer> loanFacilityMap = new HashMap<>();
        for(Loan loan: loansData){

            for(Facility facility: facilitiesData){
                Double difference = facility.getAmount() - loan.getAmount();
                if( difference > 0 && checkloanelgibilitybycovenant(loan, facilityCovenantMap.get(facility.getId()))){
                    facility.setAmount(difference);
                    loanFacilityMap.putIfAbsent(loan.getId(), facility.getId());
                    break;
                }
            }
        }

        return loanFacilityMap;
    }

    private boolean checkloanelgibilitybycovenant(Loan loan, List<Covenant> covenants){
        for(Covenant c : covenants){
            if(c.getState().equals(loan.getState()) || c.getMaxDefaultLikeliHood() < loan.getDefaultLikeHood()){
                 return false;
            }
        }

        return true;
    }

    private Map<Integer, List<Covenant>> getFacilityCovenantMap(Map<Integer, List<Facility>> bankToFacilityMap) {
        Map<Integer, List<Covenant>> facilityCovenantMap = new HashMap<>();
        for(Covenant covenant: covenantsData){
              if(covenant.getFacilityId() == 0){
                    for(Facility facility: bankToFacilityMap.get(covenant.getBankId())){
                        facilityCovenantMap.computeIfAbsent(covenant.getFacilityId(), x-> new ArrayList<>()).add(covenant);
                    }
              }
              else{
                  facilityCovenantMap.computeIfAbsent(covenant.getFacilityId(), x -> new ArrayList<>()).add(covenant);
              }
          }

        return facilityCovenantMap;
    }

    private Map<Integer, List<Facility>> getBankToFacilityMap() {
        Map<Integer , List<Facility>> bankToFacilityMap = new HashMap<>();
        for(Facility facility: facilitiesData){
             bankToFacilityMap.computeIfAbsent(facility.getBankId(), x->new ArrayList<>()).add(facility);
        }
        return bankToFacilityMap;
    }


}
