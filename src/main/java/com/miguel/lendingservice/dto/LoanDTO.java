package com.miguel.lendingservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.miguel.lendingservice.model.LoanType;

public record LoanDTO(LoanType type, @JsonProperty("interest_rate") double interestRate) {}
