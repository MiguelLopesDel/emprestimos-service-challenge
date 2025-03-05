package com.miguel.lendingservice.service;

import com.miguel.lendingservice.dto.LoanDTO;
import com.miguel.lendingservice.dto.LoanRequest;
import com.miguel.lendingservice.dto.LoanResponse;
import com.miguel.lendingservice.model.LoanType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LoanServiceTest {
    private final String cpf = "275.484.389-23";
    private final String name = "Vuxaywua Zukiagou";
    private final String sp = "SP";
    private LoanService loanService;

    @BeforeEach
    void setUp() {
        loanService = new LoanService();
    }

    @ParameterizedTest
    @ValueSource(doubles = {2000.00, 3000.00, 4000.00})
    void salaryAtMost3000_returnsPersonalAndGuaranteed(double salary) {
        LoanRequest request = new LoanRequest(26, cpf, name, BigDecimal.valueOf(salary), sp);
        LoanResponse response = loanService.determineEligibleLoans(request);

        assertNotNull(response);
        assertEquals(name, response.customer());

        List<LoanDTO> loans = response.loans();
        assertEquals(2, loans.size());
        assertTrue(loans.contains(new LoanDTO(LoanType.PERSONAL, 4)));
        assertTrue(loans.contains(new LoanDTO(LoanType.GUARANTEED, 3)));
    }

    @Test
    void salaryAbove5000_returnsConsignment() {
        LoanRequest request = new LoanRequest(26, cpf, name, BigDecimal.valueOf(7000.00), sp);
        LoanResponse response = loanService.determineEligibleLoans(request);
        assertNotNull(response);
        assertEquals(name, response.customer());
        List<LoanDTO> loans = response.loans();
        assertEquals(1, loans.size());
        assertTrue(loans.contains(new LoanDTO(LoanType.CONSIGNMENT, 2)));
    }

    @Test
    void salaryExactly5000_AgeAbove30_returnsOnlyConsignment() {
        LoanRequest request = new LoanRequest(35, cpf, name, BigDecimal.valueOf(5000.00), sp);
        LoanResponse response = loanService.determineEligibleLoans(request);

        assertNotNull(response);
        assertEquals(name, response.customer());

        List<LoanDTO> loans = response.loans();
        assertEquals(1, loans.size());
        assertTrue(loans.contains(new LoanDTO(LoanType.CONSIGNMENT, 2)));
    }

    @Test
    void salaryBetween3000And5000_NotSP_returnsNoLoans() {
        LoanRequest request = new LoanRequest(26, cpf, name, BigDecimal.valueOf(4000.00), "RJ");
        LoanResponse response = loanService.determineEligibleLoans(request);
        assertNotNull(response);
        assertEquals(name, response.customer());
        List<LoanDTO> loans = response.loans();
        assertTrue(loans.isEmpty());
    }
}