package com.chatop.api.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class RentalCreationRequest {
    private String name;
    private BigDecimal surface;
    private BigDecimal price;
    private String description;
}