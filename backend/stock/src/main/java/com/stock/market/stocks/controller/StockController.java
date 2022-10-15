package com.stock.market.stocks.controller;


import com.stock.market.stocks.exception.CompanyNotFoundException;
import com.stock.market.stocks.exception.NoDataFoundException;
import com.stock.market.stocks.model.Error;
import com.stock.market.stocks.model.Stock;
import com.stock.market.stocks.model.StockSearch;
import com.stock.market.stocks.model.dto.StockDTO;
import com.stock.market.stocks.service.StockService;
import com.stock.market.stocks.utility.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1.0/market/stock")
public class StockController {

    public static final Logger LOGGER = LoggerFactory.getLogger(StockController.class);

    @Autowired
    StockService stockService;

    @PostMapping("/add/{companycode}")
    public ResponseEntity<StockDTO> addStock(@RequestBody Stock stock, @PathVariable("companycode") String companyCode) {
        try {
            Stock registeredStock = stockService.addStock(stock, companyCode);
            return new ResponseEntity(new StockDTO(registeredStock), HttpStatus.OK);
        } catch (CompanyNotFoundException exception) {
            LOGGER.error("No company found with company code");
            return new ResponseEntity(Error.builder().result(Constants.COMPANY_NOT_FOUND + " : " + companyCode).build(), HttpStatus.BAD_REQUEST);
        } catch (Exception exception) {
            LOGGER.error("Technical Error Occurred");
            return new ResponseEntity(Error.builder().result(Constants.TECH_ERROR).build(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/get/{companycode}/{startdate}/{enddate}")
    public ResponseEntity<StockSearch> getStock(@PathVariable("companycode") String companyCode, @PathVariable("startdate") String startDate, @PathVariable("enddate") String endDate) {
        try {
            return new ResponseEntity(stockService.getStocks(companyCode, startDate, endDate), HttpStatus.OK);
        } catch (NoDataFoundException e) {
            LOGGER.error("No stock found matching above criteria");
            return new ResponseEntity(Error.builder().result(Constants.NO_DATA).build(), HttpStatus.BAD_REQUEST);
        }
    }
}
