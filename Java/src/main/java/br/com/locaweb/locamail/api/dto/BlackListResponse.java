package br.com.locaweb.locamail.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BlackListResponse {
    private String status;
    private String input_raw;
    private String input_domain;
    private String ip_address;
    private Long detections;
}
