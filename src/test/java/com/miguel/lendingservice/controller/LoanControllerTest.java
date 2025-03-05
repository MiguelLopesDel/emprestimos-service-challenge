package com.miguel.lendingservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.miguel.lendingservice.dto.LoanDTO;
import com.miguel.lendingservice.dto.LoanRequest;
import com.miguel.lendingservice.dto.LoanResponse;
import com.miguel.lendingservice.model.LoanType;
import com.miguel.lendingservice.service.LoanService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.result.StatusResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class LoanControllerTest {

    @InjectMocks
    private LoanController controller;
    @Mock
    private LoanService service;
    private MockMvc mockMvc;
    private final ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void getEligibleLoans_withValidInput_returnsCorrectLoans() throws Exception {
        LoanRequest loanRequest = new LoanRequest(26, "275.484.389-23", "Vuxaywua Zukiagou",
                BigDecimal.valueOf(7000.00), "SP");
        LoanResponse mockResponse = new LoanResponse("Vuxaywua Zukiagou",
                List.of(new LoanDTO(LoanType.PERSONAL, 5000.00),
                        new LoanDTO(LoanType.GUARANTEED, 7000.00)));
        when(service.determineEligibleLoans(any(LoanRequest.class))).thenReturn(mockResponse);
        mockMvc.perform(post("/customer-loans")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsBytes(loanRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customer").value("Vuxaywua Zukiagou"))
                .andExpect(jsonPath("$.loans[0].type").value("PERSONAL"))
                .andExpect(jsonPath("$.loans[1].type").value("GUARANTEED"))
                .andExpect(jsonPath("$.loans", hasSize(2)));
    }
}
