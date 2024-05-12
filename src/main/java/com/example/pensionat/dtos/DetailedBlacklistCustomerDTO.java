package com.example.pensionat.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DetailedBlacklistCustomerDTO {
    private int id;
    private String email;
    private String name;
    private String group;
    private Date created;
    private Boolean ok;
}
