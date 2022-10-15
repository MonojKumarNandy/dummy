package com.stock.market.stocks.repository;



import com.stock.market.stocks.model.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface StockDao extends JpaRepository<Stock, Long> {

    List<Stock> findByCompanyCode(String code);
    List<Stock> findByInsertTimeBetween(LocalDateTime from, LocalDateTime to);

}