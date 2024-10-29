package com.carrot.payment;

import com.carrot.payment.domain.Payment;
import com.carrot.payment.domain.PaymentStatus;
import com.carrot.payment.dto.PaymentRequest;
import com.carrot.payment.dto.PaymentResponse;
import com.carrot.payment.kafka.KafkaMessage;
import com.carrot.payment.kafka.KafkaService;
import com.carrot.payment.repository.PaymentRepository;
import com.carrot.payment.service.PaymentServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class PaymentServiceImplTest {

    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private KafkaService kafkaService;

    @InjectMocks
    private PaymentServiceImpl paymentService;

    private Payment mockPayment;
    private PaymentResponse expectedResponse;
    private PaymentRequest mockRequest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // mockPayment 초기화 (빌더 패턴 활용)
        mockPayment = Payment.builder()
                .id(1L)
                .userId(101L)
                .price(1000.0)
                .status(PaymentStatus.REQUESTED)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        // expectedResponse 초기화
        expectedResponse = PaymentResponse.builder()
                .id(mockPayment.getId())
                .userId(mockPayment.getUserId())
                .price(mockPayment.getPrice())
                .status(mockPayment.getStatus())
                .transactionId("TX123")
                .createdAt(mockPayment.getCreatedAt())
                .updatedAt(mockPayment.getUpdatedAt())
                .build();

        // mockRequest 초기화
        mockRequest = PaymentRequest.builder()
                .userId(mockPayment.getUserId())
                .amount(mockPayment.getPrice())
                .status(mockPayment.getStatus())
                .build();
    }

    @Test
    void getAllPayments_shouldReturnAllPayments() {
        // Given
        when(paymentRepository.findAll()).thenReturn(Collections.singletonList(mockPayment));

        // When
        List<PaymentResponse> payments = paymentService.getAllPayments();

        // Then
        assertEquals(1, payments.size());
        assertEquals(expectedResponse.getUserId(), payments.get(0).getUserId());
        verify(paymentRepository, times(1)).findAll();
    }

    @Test
    void getPaymentById_shouldReturnPayment() {
        // Given
        Long paymentId = 1L;
        when(paymentRepository.findById(paymentId)).thenReturn(Optional.of(mockPayment));

        // When
        PaymentResponse response = paymentService.getPaymentById(paymentId);

        // Then
        assertEquals(expectedResponse.getId(), response.getId());
        assertEquals(expectedResponse.getUserId(), response.getUserId());
        verify(paymentRepository, times(1)).findById(paymentId);
    }

    @Test
    void savePaymentDetail_shouldSavePaymentAndSendKafkaMessage() {
        // Given
        when(paymentRepository.save(any(Payment.class))).thenReturn(mockPayment);

        // When
        PaymentResponse response = paymentService.savePaymentDetail(mockRequest);

        // Then
        assertEquals(expectedResponse.getId(), response.getId());
        assertEquals(expectedResponse.getUserId(), response.getUserId());
        assertEquals(expectedResponse.getPrice(), response.getPrice());
        verify(paymentRepository, times(1)).save(any(Payment.class));
        verify(kafkaService, times(1)).sendPaymentEvent(any(KafkaMessage.class));
    }

    @Test
    void updatePaymentStatus_shouldUpdateStatusInRepository() {
        // Given
        Long paymentId = 1L;
        PaymentStatus status = PaymentStatus.APPROVED;

        // When
        paymentService.updatePaymentStatus(paymentId, status);

        // Then
        verify(paymentRepository, times(1)).updatePaymentStatus(paymentId, status);
    }
}
