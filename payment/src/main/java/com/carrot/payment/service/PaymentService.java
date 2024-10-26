package com.carrot.payment.service;

import com.carrot.payment.domain.PaymentStatus;
import com.carrot.payment.dto.PaymentRequest;
import com.carrot.payment.dto.PaymentResponse;
import java.util.List;

public interface PaymentService {
    List<PaymentResponse> getAllPayments();
    PaymentResponse getPaymentById(Long paymentId);
    List<PaymentResponse> getUserPaymentsHistory(Long userId);
    List<PaymentResponse> getPaymentsByStatus(PaymentStatus status);
    PaymentResponse savePaymentDetail(PaymentRequest paymentRequest);
}
