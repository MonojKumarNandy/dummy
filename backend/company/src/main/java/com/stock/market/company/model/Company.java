package com.stock.market.company.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Document
public class Company {

    @Id
    @NotBlank(message = "Company Code must be provided")
    private String companyCode;
    @Field
    @NotBlank(message = "Company Name must be provided")
    private String companyName;
    @Field
    @NotBlank(message = "Company CEO must be provided")
    private String companyCEO;
    @Field
    @NotNull(message = "Turn over must be Provided")
    @Min(value = 10, message = "Turn over must be grater that 10 Cr")
    private Long companyTurnover;
    @Field
    @NotBlank(message = "Company Website must be provided")
    private String companyWebsite;
    @Field
    @NotBlank(message = "Company SE must be provided")
    private String enlistedSE;

}
