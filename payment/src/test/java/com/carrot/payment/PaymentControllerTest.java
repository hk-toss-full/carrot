package com.carrot.payment;

import com.carrot.payment.controller.PaymentController;
import com.carrot.payment.domain.PaymentStatus;
import com.carrot.payment.dto.PaymentRequest;
import com.carrot.payment.dto.PaymentResponse;
import com.carrot.payment.service.PaymentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Collections;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(PaymentController.class)
class PaymentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PaymentService paymentService;

    @Autowired
    private ObjectMapper objectMapper;

    private PaymentResponse mockResponse;

    @BeforeEach
    void setUp() {
        mockResponse = PaymentResponse.builder()
                .id(1L)
                .userId(101L)
                .price(1000.0)
                .status(PaymentStatus.APPROVED)
                .transactionId("TX123")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    @Test
    void getAllPayments_shouldReturnListOfPayments() throws Exception {
        when(paymentService.getAllPayments()).thenReturn(Collections.singletonList(mockResponse));

        mockMvc.perform(get("/api/v1/payments")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", hasSize(1)))
                .andExpect(jsonPath("$.data[0].id").value(mockResponse.getId()))
                .andExpect(jsonPath("$.data[0].userId").value(mockResponse.getUserId()));
    }

    @Test
    void getPaymentById_shouldReturnPayment() throws Exception {
        Long paymentId = 1L;
        when(paymentService.getPaymentById(paymentId)).thenReturn(mockResponse);

        mockMvc.perform(get("/api/v1/payments/{paymentId}", paymentId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(mockResponse.getId()))
                .andExpect(jsonPath("$.data.userId").value(mockResponse.getUserId()));
    }

    @Test
    void getUserPaymentHistory_shouldReturnPaymentsForUser() throws Exception {
        Long userId = 101L;
        when(paymentService.getUserPaymentsHistory(userId)).thenReturn(Collections.singletonList(mockResponse));

        mockMvc.perform(get("/api/v1/payments/user/{userId}", userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", hasSize(1)))
                .andExpect(jsonPath("$.data[0].userId").value(mockResponse.getUserId()));
    }

    @Test
    void getPaymentsByStatus_shouldReturnPaymentsWithGivenStatus() throws Exception {
        PaymentStatus status = PaymentStatus.APPROVED;
        when(paymentService.getPaymentsByStatus(status)).thenReturn(Collections.singletonList(mockResponse));

        mockMvc.perform(get("/api/v1/payments/status/{status}", status)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", hasSize(1)))
                .andExpect(jsonPath("$.data[0].status").value(status.toString()));
    }

    @Test
    void savePaymentDetail_shouldReturnSavedPayment() throws Exception {
        PaymentRequest paymentRequest = new PaymentRequest();
        when(paymentService.savePaymentDetail(paymentRequest)).thenReturn(mockResponse);

        mockMvc.perform(post("/api/v1/payments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(paymentRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.id").value(mockResponse.getId()))
                .andExpect(jsonPath("$.data.userId").value(mockResponse.getUserId()));
    }
}
