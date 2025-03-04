package com.miguel.lendingservice.dto;

import java.math.BigDecimal;

public record LoanRequest(Integer age, String cpf, String name, BigDecimal income, String location) {}
