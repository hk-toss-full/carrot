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
                .map(PaymentResponse::fromEntity)
                .collect(Collectors.toList());
    }

    @Description("단일 결제 조회")
    @Override
    public PaymentResponse getPaymentById(Long paymentId) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new IllegalArgumentException("결제를 찾을 수 없습니다."));
        return PaymentResponse.fromEntity(payment);
    }

    @Description("사용자별 결제 내역 조회")
    @Override
    public List<PaymentResponse> getUserPaymentsHistory(Long userId) {
        return paymentRepository.findByUserId(userId).stream()
                .map(PaymentResponse::fromEntity)
                .collect(Collectors.toList());
    }

    @Description("결제 상태별 조회")
    @Override
    public List<PaymentResponse> getPaymentsByStatus(PaymentStatus status) {
        return paymentRepository.findByStatus(status).stream()
                .map(PaymentResponse::fromEntity)
                .collect(Collectors.toList());
    }

    @Description("결제 상세 내역 저장")
    @Override
    public PaymentResponse savePaymentDetail(PaymentRequest paymentRequest) {
        Payment payment = paymentRequest.toEntity();
        Payment savedPayment = paymentRepository.save(payment);

        KafkaMessage message = new KafkaMessage(LocalDateTime.now(), savedPayment, "CREATED");
        kafkaService.sendPaymentEvent(message);

        return PaymentResponse.fromEntity(savedPayment);
    }

    @Override
    public void updatePaymentStatus(Long paymentId, PaymentStatus status) {
        paymentRepository.updatePaymentStatus(paymentId, status);
    }
}
