package com.stock.market.company.Controller;

import com.stock.market.company.Service.CompanyService;
import com.stock.market.company.Utility.Constants;
import com.stock.market.company.exception.CompanyExistsException;
import com.stock.market.company.exception.CompanyNotFoundException;
import com.stock.market.company.exception.InvalidStructureException;
import com.stock.market.company.model.Company;
import com.stock.market.company.model.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1.0/market/company")
public class CompanyController {

    @Autowired
    CompanyService companyService;

    Logger logger = LoggerFactory.getLogger(CompanyController.class);

    @PostMapping("/register")
    public ResponseEntity<Result> registerCompany(@RequestBody Company company) {
        logger.info(" Registering Company");

        try {
            return new ResponseEntity(companyService.registerCompany(company), HttpStatus.OK);
        } catch (CompanyExistsException exception) {
            logger.error("Company Already existing");
            return new ResponseEntity(Result.builder().result(Constants.COMPANY_EXISTS + " : " + company.getCompanyCode()).build(), HttpStatus.BAD_REQUEST);
        } catch(InvalidStructureException exp){
            logger.error("Invalid Structure");
            return new ResponseEntity(Result.builder().result(Constants.INVALID_STRUCTURE).build(), HttpStatus.BAD_REQUEST);

        }
        catch (Exception exception) {
            logger.error("Technical Error Occurred");
            return new ResponseEntity(Result.builder().result(Constants.TECH_ERROR).build(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/info/{companyCode}")
    public ResponseEntity<Company> fetchCompanyDetails(@PathVariable String companyCode) {
        logger.info("fetching company Details ");

        try {
            return new ResponseEntity(companyService.fetchCompanyDetails(companyCode), HttpStatus.OK);
        } catch (CompanyNotFoundException exception) {
            logger.error("Company Already existing");
            return new ResponseEntity(Result.builder().result(Constants.COMPANY_NOT_FOUND + " : " + companyCode).build(), HttpStatus.BAD_REQUEST);
        } catch (Exception exception) {
            logger.error("Technical Error Occurred");
            return new ResponseEntity(Result.builder().result(Constants.TECH_ERROR).build(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getall")
    public ResponseEntity<List<Company>> fetchCompanyDetails() {
        logger.info("fetching all companies");

        try {
            return new ResponseEntity(companyService.fetchAllCompany(), HttpStatus.OK);
        } catch (Exception exception) {
            logger.error("Technical Error Occurred");
            return new ResponseEntity(Result.builder().result(Constants.TECH_ERROR).build(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete/{companyCode}")
    public ResponseEntity<Object> deleteCompany(@PathVariable String companyCode) {
        logger.info("company deleted", companyCode);

        try {
            return new ResponseEntity(companyService.deleteCompany(companyCode), HttpStatus.OK);
        } catch (CompanyNotFoundException exception) {
            logger.error("Company Already existing");
            return new ResponseEntity(Result.builder().result(Constants.COMPANY_NOT_FOUND + " : " + companyCode).build(), HttpStatus.BAD_REQUEST);
        } catch (Exception exception) {
            logger.error("Technical Error Occurred");
            return new ResponseEntity(Result.builder().result(Constants.TECH_ERROR).build(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
