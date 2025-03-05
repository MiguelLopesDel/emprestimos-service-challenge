package com.miguel.lendingservice.service;

import com.miguel.lendingservice.dto.LoanDTO;
import com.miguel.lendingservice.dto.LoanRequest;
import com.miguel.lendingservice.dto.LoanResponse;
import com.miguel.lendingservice.model.LoanType;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;

@Service
public class LoanService {

    public LoanResponse determineEligibleLoans(LoanRequest request) {
        ArrayList<LoanDTO> loanTypes = new ArrayList<>();
        BigDecimal income = request.income();
        BigDecimal threeThousand = BigDecimal.valueOf(3000);
        BigDecimal fiveThousand = BigDecimal.valueOf(5000);

        boolean eligibleForPersonalAndGuaranteed = income.compareTo(threeThousand) <= 0 ||
                (income.compareTo(threeThousand) >= 0 && income.compareTo(fiveThousand) <= 0 && request.age() < 30 &&
                                "SP".equalsIgnoreCase(request.location()));
        if (eligibleForPersonalAndGuaranteed) {
            loanTypes.add(new LoanDTO(LoanType.PERSONAL, 4));
            loanTypes.add(new LoanDTO(LoanType.GUARANTEED, 3));
        }
        if (income.compareTo(fiveThousand) >= 0) {
            loanTypes.add(new LoanDTO(LoanType.CONSIGNMENT, 2));
        }

        return new LoanResponse(request.name(), loanTypes);
    }
}
