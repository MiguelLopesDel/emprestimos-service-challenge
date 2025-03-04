package com.miguel.lendingservice.dto;

import com.miguel.lendingservice.model.LoanType;

public record LoanDTO(LoanType type, double interestRate) {}
