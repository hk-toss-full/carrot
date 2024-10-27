package com.carrot.payment;

import com.carrot.payment.controller.PaymentController;
import com.carrot.payment.domain.PaymentStatus;
import com.carrot.payment.dto.PaymentRequest;
import com.carrot.payment.dto.PaymentResponse;
import com.carrot.payment.global.CommonApiResponse;
import com.carrot.payment.service.PaymentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class PaymentControllerTest {

    @Mock
    private PaymentService paymentService;

    @InjectMocks
    private PaymentController paymentController;

    // 공통으로 사용할 PaymentResponse 객체
    private PaymentResponse mockResponse;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // 공통 PaymentResponse 객체 초기화
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
    void getAllPayments_shouldReturnListOfPayments() {
        // Given
        when(paymentService.getAllPayments()).thenReturn(Collections.singletonList(mockResponse));

        // When
        ResponseEntity<CommonApiResponse<List<PaymentResponse>>> response = paymentController.getAllPayments();

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().getData().size());
        verify(paymentService, times(1)).getAllPayments();
    }

    @Test
    void getPaymentById_shouldReturnPayment() {
        // Given
        Long paymentId = 1L;
        when(paymentService.getPaymentById(paymentId)).thenReturn(mockResponse);

        // When
        ResponseEntity<CommonApiResponse<PaymentResponse>> response = paymentController.getPaymentById(paymentId);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockResponse, response.getBody().getData());
        verify(paymentService, times(1)).getPaymentById(paymentId);
    }

    @Test
    void getUserPaymentHistory_shouldReturnPaymentsForUser() {
        // Given
        Long userId = 101L;
        when(paymentService.getUserPaymentsHistory(userId)).thenReturn(Collections.singletonList(mockResponse));

        // When
        ResponseEntity<CommonApiResponse<List<PaymentResponse>>> response = paymentController.getUserPaymentHistory(userId);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().getData().size());
        verify(paymentService, times(1)).getUserPaymentsHistory(userId);
    }

    @Test
    void getPaymentsByStatus_shouldReturnPaymentsWithGivenStatus() {
        // Given
        PaymentStatus status = PaymentStatus.APPROVED;
        when(paymentService.getPaymentsByStatus(status)).thenReturn(Collections.singletonList(mockResponse));

        // When
        ResponseEntity<CommonApiResponse<List<PaymentResponse>>> response = paymentController.getPaymentsByStatus(status);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().getData().size());
        verify(paymentService, times(1)).getPaymentsByStatus(status);
    }

    @Test
    void savePaymentDetail_shouldReturnSavedPayment() {
        // Given
        PaymentRequest paymentRequest = new PaymentRequest();
        when(paymentService.savePaymentDetail(paymentRequest)).thenReturn(mockResponse);

        // When
        ResponseEntity<CommonApiResponse<PaymentResponse>> response = paymentController.savePaymentDetail(paymentRequest);

        // Then
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(mockResponse, response.getBody().getData());
        verify(paymentService, times(1)).savePaymentDetail(paymentRequest);
    }
}
