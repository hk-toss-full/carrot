package com.carrot.payment.service;

import com.carrot.payment.domain.Payment;
import com.carrot.payment.domain.PaymentStatus;
import com.carrot.payment.dto.PaymentRequest;
import com.carrot.payment.dto.PaymentResponse;
import com.carrot.payment.kafka.KafkaMessage;
import com.carrot.payment.kafka.KafkaService;
import com.carrot.payment.repository.PaymentRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Description;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;
    private final KafkaService kafkaService;

    @Description("전체 결제 조회")
    @Override
    public List<PaymentResponse> getAllPayments() {
        return paymentRepository.findAll().stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
    }

    @Description("단일 결제 조회")
    @Override
    public PaymentResponse getPaymentById(Long paymentId) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new IllegalArgumentException("결제를 찾을 수 없습니다."));
        return convertToResponseDto(payment);
    }

    @Description("사용자별 결제 내역 조회")
    @Override
    public List<PaymentResponse> getUserPaymentsHistory(Long userId) {
        return paymentRepository.findByUserId(userId).stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
    }

    @Description("결제 상태별 조회")
    @Override
    public List<PaymentResponse> getPaymentsByStatus(PaymentStatus status) {
        return paymentRepository.findByStatus(status).stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
    }

    @Description("결제 상세 내역 저장")
    @Override
    public PaymentResponse savePaymentDetail(PaymentRequest paymentRequest) {
        Payment payment = convertToEntity(paymentRequest);
        Payment savedPayment = paymentRepository.save(payment);

        KafkaMessage message = new KafkaMessage(LocalDateTime.now(), savedPayment, "CREATED");
        kafkaService.sendPaymentEvent(message);

        return convertToResponseDto(savedPayment);
    }

    @Override
    public void updatePaymentStatus(Long paymentId, PaymentStatus status) {
        paymentRepository.updatePaymentStatus(paymentId, status);
    }

    private Payment convertToEntity(PaymentRequest request) {
        return Payment.builder()
                .userId(request.getUserId())
                .price(request.getPrice())
                .status(request.getStatus())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    private PaymentResponse convertToResponseDto(Payment payment) {
        return PaymentResponse.builder()
                .id(payment.getId())
                .userId(payment.getUserId())
                .price(payment.getPrice())
                .status(payment.getStatus())
                .transactionId(payment.getTransactionId())
                .createdAt(LocalDateTime.parse(payment.getCreatedAt().toString()))
                .updatedAt(LocalDateTime.parse(payment.getUpdatedAt().toString()))
                .build();
    }
}
