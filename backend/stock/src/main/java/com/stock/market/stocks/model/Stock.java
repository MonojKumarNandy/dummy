package com.stock.market.stocks.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;


@Data
@Entity
@Table(name = "stock")
public class Stock {

    @Id
    @Column(name = "STOCK_ID", unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "COMPANY_CODE")
    private String companyCode;
    @Column(name = "STOCK_PRICE")
    private Double price;
    @Column(name = "INSERT_TIME")
    private LocalDateTime insertTime;
}