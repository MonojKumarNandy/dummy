package com.stock.market.company.Service;

import com.mongodb.DuplicateKeyException;
import com.stock.market.company.Utility.Constants;
import com.stock.market.company.exception.CompanyExistsException;
import com.stock.market.company.exception.CompanyNotFoundException;
import com.stock.market.company.exception.InvalidStructureException;
import com.stock.market.company.model.Company;
import com.stock.market.company.model.Result;
import com.stock.market.company.repository.CompanyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class CompanyServiceImpl implements CompanyService {

    Logger LOGGER = LoggerFactory.getLogger(CompanyServiceImpl.class);


    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    Validator validator;

    @Override
    public Result registerCompany(Company company) {

        Set<ConstraintViolation<Company>> validationResult=validator.validate(company);
        if(!validationResult.isEmpty()){
            LOGGER.error("Structural Validation Error", validationResult);
            throw new InvalidStructureException(Constants.INVALID_STRUCTURE);
        }

        Optional<Company> companyFromCompanyId = companyRepository.findById(company.getCompanyCode());
        if (companyFromCompanyId.isPresent()) {
            LOGGER.error("Company Already Exists");
            throw new CompanyExistsException(Constants.COMPANY_EXISTS);
        }
        LOGGER.info("Inserting record in Database");
        companyRepository.insert(company);

        return Result.builder().result(Constants.REGISTRATION_SUCCESS).build();
    }

    @Override
    public Company fetchCompanyDetails(String companyCode) {
        Optional<Company> companyDetails = this.companyRepository.findById(companyCode);
        if (!companyDetails.isPresent()) {
            LOGGER.error("Couldn't find Company");
            throw new CompanyNotFoundException(Constants.COMPANY_NOT_FOUND);
        }

        Company companyDetailsResponse = companyDetails.get();
        return companyDetailsResponse;
    }

    @Override
    public List<Company> fetchAllCompany() {
        LOGGER.info("Retrieving All companies");
        return companyRepository.findAll();
    }

    @Override
    public Result deleteCompany(String companyCode) {
        Optional<Company> companyDetails = this.companyRepository.findById(companyCode);
        if (!companyDetails.isPresent()) {
            LOGGER.error("Couldn't find Company");
            throw new CompanyNotFoundException(Constants.COMPANY_NOT_FOUND);
        }
        LOGGER.info("Deleting Company");
        companyRepository.deleteById(companyCode);

        return Result.builder().result(Constants.DELETION_SUCCESS).build();
    }
}
