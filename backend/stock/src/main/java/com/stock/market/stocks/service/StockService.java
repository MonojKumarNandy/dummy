package com.stock.market.stocks.service;

import com.stock.market.stocks.model.Stock;
import com.stock.market.stocks.model.StockSearch;

public interface StockService {


    public Stock addStock(Stock company, String companyCode);

    public StockSearch getStocks(String companyCode, String startDate, String endDate);

}
