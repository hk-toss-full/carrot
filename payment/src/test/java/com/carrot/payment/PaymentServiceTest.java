//package com.carrot.payment;
//
//import com.carrot.payment.domain.Payment;
//import com.carrot.payment.domain.PaymentStatus;
//import com.carrot.payment.dto.PaymentRequest;
//import com.carrot.payment.dto.PaymentResponse;
//import com.carrot.payment.kafka.KafkaMessage;
//import com.carrot.payment.kafka.KafkaService;
//import com.carrot.payment.repository.PaymentRepository;
//import com.carrot.payment.service.PaymentServiceImpl;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//
//import java.time.LocalDateTime;
//import java.util.Collections;
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.*;
//
//class PaymentServiceImplTest {
//
//    @Mock
//    private PaymentRepository paymentRepository;
//
//    @Mock
//    private KafkaService kafkaService;
//
//    @InjectMocks
//    private PaymentServiceImpl paymentService;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    void getAllPayments_shouldReturnAllPayments() {
//        // Given
//        Payment payment = new Payment();
//        when(paymentRepository.findAll()).thenReturn(Collections.singletonList(payment));
//
//        // When
//        List<PaymentResponse> payments = paymentService.getAllPayments();
//
//        // Then
//        assertEquals(1, payments.size());
//        verify(paymentRepository, times(1)).findAll();
//    }
//
//    @Test
//    void getPaymentById_shouldReturnPayment() {
//        // Given
//        Long paymentId = 1L;
//        Payment payment = new Payment();
//        when(paymentRepository.findById(paymentId)).thenReturn(Optional.of(payment));
//
//        // When
//        PaymentResponse response = paymentService.getPaymentById(paymentId);
//
//        // Then
//        assertEquals(payment.getId(), response.getId());
//        verify(paymentRepository, times(1)).findById(paymentId);
//    }
//
//    @Test
//    void savePaymentDetail_shouldSavePaymentAndSendKafkaMessage() {
//        // Given
//        PaymentRequest request = new PaymentRequest(101L, 1000.0, PaymentStatus.PENDING);
//        Payment payment = new Payment();
//        payment.setId(1L);
//        payment.setUserId(101L);
//        payment.setPrice(1000.0);
//        payment.setStatus(PaymentStatus.PENDING);
//        payment.setCreatedAt(LocalDateTime.now());
//        payment.setUpdatedAt(LocalDateTime.now());
//
//        when(paymentRepository.save(any(Payment.class))).thenReturn(payment);
//
//        // When
//        PaymentResponse response = paymentService.savePaymentDetail(request);
//
//        // Then
//        assertEquals(payment.getId(), response.getId());
//        verify(paymentRepository, times(1)).save(any(Payment.class));
//        verify(kafkaService, times(1)).sendPaymentEvent(any(KafkaMessage.class));
//    }
//
//    @Test
//    void updatePaymentStatus_shouldUpdateStatusInRepository() {
//        // Given
//        Long paymentId = 1L;
//        PaymentStatus status = PaymentStatus.APPROVED;
//
//        // When
//        paymentService.updatePaymentStatus(paymentId, status);
//
//        // Then
//        verify(paymentRepository, times(1)).updatePaymentStatus(paymentId, status);
//    }
//}
