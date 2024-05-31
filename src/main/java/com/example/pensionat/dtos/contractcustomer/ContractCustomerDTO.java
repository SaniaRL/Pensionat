package com.example.pensionat.dtos.contractcustomer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContractCustomerDTO {
    private Long id;
    private String companyName;
    private String contactName;
    private String country;
}
