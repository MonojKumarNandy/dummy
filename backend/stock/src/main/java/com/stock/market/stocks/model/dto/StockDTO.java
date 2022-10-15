package com.stock.market.stocks.model.dto;

import com.stock.market.stocks.model.Stock;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;


@Data
@NoArgsConstructor
public class StockDTO {

    private String companyCode;
    private Double price;
    private LocalDateTime insertTime;

    public StockDTO(Stock stock) {
        this.companyCode = stock.getCompanyCode();
        this.price = stock.getPrice();
        this.insertTime = stock.getInsertTime();
    }

    public List<StockDTO> getStockDTOs(List<Stock> stocks) {

        return stocks.stream()
                .map(s->new StockDTO(s))
                .collect(toList());
    }
}