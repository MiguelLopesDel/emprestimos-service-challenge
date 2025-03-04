package com.miguel.lendingservice.controller;

import com.miguel.lendingservice.dto.LoanRequest;
import com.miguel.lendingservice.dto.LoanResponse;
import com.miguel.lendingservice.service.LoanService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customer-loans")
@RequiredArgsConstructor
public class LoanController {

    private final LoanService loanService;

    @PostMapping
    public ResponseEntity<LoanResponse> getEligibleLoans(@RequestBody LoanRequest request) {
        return ResponseEntity.ok(loanService.determineEligibleLoans(request));
    }
}
