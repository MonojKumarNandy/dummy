package com.stock.market.company.Service;

import com.stock.market.company.model.Company;
import com.stock.market.company.model.Result;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public interface CompanyService {

    Result registerCompany(Company company);

    Company fetchCompanyDetails(String companyCode);

    List<Company> fetchAllCompany();

    Result deleteCompany(String companyCode);
}
