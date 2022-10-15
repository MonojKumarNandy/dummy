package com.stock.market.stocks.service;

import com.stock.market.stocks.exception.CompanyNotFoundException;
import com.stock.market.stocks.exception.NoDataFoundException;
import com.stock.market.stocks.model.Stock;
import com.stock.market.stocks.model.StockSearch;
import com.stock.market.stocks.model.dto.StockDTO;
import com.stock.market.stocks.repository.StockDao;
import com.stock.market.stocks.utility.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Service
public class StockServiceImpl implements StockService {

    public static final Logger LOGGER = LoggerFactory.getLogger(StockServiceImpl.class);
    @Autowired
    StockDao stockDao;

    @Autowired
    RestTemplate restTemplate;

    @Value("${company.get.endpoint}")
    private String getCompanyEndpoint;

    @Override
    public Stock addStock(Stock stock, String companyCode) {

        ResponseEntity<String> response
                = restTemplate.getForEntity(getCompanyEndpoint + companyCode, String.class);
        if(!response.getStatusCode().equals( HttpStatus.OK))
            throw new CompanyNotFoundException(Constants.COMPANY_NOT_FOUND);

        try {
            stock.setInsertTime(LocalDateTime.now());
            stock.setCompanyCode(companyCode);
            stockDao.save(stock);
            LOGGER.debug("Stock added");
        } catch (Exception e) {
            LOGGER.debug("Exception in Stock insertion " + e.getMessage());
        }
        return stock;
    }

    @Override
    public StockSearch getStocks(String companyCode, String startDate, String endDate) {

        List<Stock> stocks = stockDao.findByInsertTimeBetween(LocalDateTime.parse(startDate),LocalDateTime.parse(endDate));
        LOGGER.info("Stocks .....", stocks);

        Stock minStock = stocks.stream().min(Comparator.comparing(Stock::getPrice)).orElseThrow(NoDataFoundException::new);

        Stock maxStock = stocks.stream().max(Comparator.comparing(Stock::getPrice)).orElseThrow(NoDataFoundException::new);

        Double average = stocks.stream().mapToDouble(s -> s.getPrice()).average().orElseThrow(NoDataFoundException::new);

        return StockSearch.builder().stocks(new StockDTO().getStockDTOs(stocks)).minPrice(minStock.getPrice()).maxPrice(maxStock.getPrice()).avgPrice(average).build();
    }
}
