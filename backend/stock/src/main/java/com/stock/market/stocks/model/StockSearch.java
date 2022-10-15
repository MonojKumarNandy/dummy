package com.stock.market.stocks.model;

import com.stock.market.stocks.model.dto.StockDTO;
import lombok.Builder;
import lombok.Data;

import java.util.List;


@Data
@Builder
public class StockSearch {

    private List<StockDTO> stocks;
    private Double minPrice;
    private Double maxPrice;
    private Double avgPrice;
}