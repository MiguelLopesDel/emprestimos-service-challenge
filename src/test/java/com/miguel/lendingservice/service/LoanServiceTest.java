package com.miguel.lendingservice.service;

import com.miguel.lendingservice.dto.LoanDTO;
import com.miguel.lendingservice.dto.LoanRequest;
import com.miguel.lendingservice.dto.LoanResponse;
import com.miguel.lendingservice.model.LoanType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LoanServiceTest {
    private final String cpf = "275.484.389-23";
    private final String name = "Vuxaywua Zukiagou";
    private final String sp = "SP";
    private final LoanType[] personalAndGuaranteedLoans = {LoanType.PERSONAL, LoanType.GUARANTEED};
    private LoanService loanService;

    @BeforeEach
    void setUp() {
        loanService = new LoanService();
    }

    @ParameterizedTest
    @ValueSource(doubles = {2999.99, 3000.00, 4000.00})
    void salaryBelow5000_sp_returnsPersonalAndGuaranteedLoans(double salary) {
        assertLoanTypes(new LoanRequest(26, cpf, name, BigDecimal.valueOf(salary), sp), personalAndGuaranteedLoans);
    }

    @Test
    void salaryExactly3000_age30OrMore_notSp_returnsOnlyPersonalAndGuaranteedLoans() {
        assertLoanTypes(new LoanRequest(30, cpf, name, BigDecimal.valueOf(3000), "RJ"), personalAndGuaranteedLoans);
    }

    @Test
    void salaryExactly5000_sp_returnsPersonalGuaranteedAndConsignmentLoans() {
        assertLoanTypes(new LoanRequest(26, cpf, name, BigDecimal.valueOf(5000), sp), LoanType.PERSONAL, LoanType.GUARANTEED, LoanType.CONSIGNMENT);
    }

    @ParameterizedTest
    @CsvSource({"5000.01, sp", "5000.00, rj", "5000.01, rj"})
    void salaryAbove5000_returnsOnlyConsignmentLoan(Double salary, String state) {
        assertLoanTypes(new LoanRequest(26, cpf, name, BigDecimal.valueOf(salary), state), LoanType.CONSIGNMENT);
    }

    @ParameterizedTest
    @CsvSource({"4000, RJ, 26", "4000, SP, 30"})
    void salaryBetween3000And5000_noLoans(Double salary, String state, int age){
        assertLoanTypes(new LoanRequest(age, cpf, name, BigDecimal.valueOf(salary), state));
    }

    private void assertLoanTypes(LoanRequest request, LoanType... expectedLoans) {
        LoanResponse response = loanService.determineEligibleLoans(request);
        assertNotNull(response);
        assertEquals(name, response.customer());

        List<LoanDTO> loans = response.loans();
        assertEquals(expectedLoans.length, loans.size());
        for (LoanType loanType : expectedLoans) {
            assertTrue(loans.contains(new LoanDTO(loanType, getLoanRate(loanType))));
        }
    }

    private int getLoanRate(LoanType loanType) {
        return switch (loanType) {
            case PERSONAL -> 4;
            case GUARANTEED -> 3;
            case CONSIGNMENT -> 2;
        };
    }
}