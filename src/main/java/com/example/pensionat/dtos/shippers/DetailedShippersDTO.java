package com.example.pensionat.dtos.shippers;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DetailedShippersDTO {
        private Long id;
        private String companyName;
        private String phone;
        private String email;
        private String contactName;
        private String contactTitle;
        private String streetAddress;
        private String city;
        private String postalCode;
        private String country;
        private String fax;
}
