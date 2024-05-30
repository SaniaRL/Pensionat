package com.example.pensionat.dtos.blacklist;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BlacklistResponse {
    String statusText;
    Boolean ok;
}
