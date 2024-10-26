package com.carrot.payment.controller;

import com.carrot.payment.domain.PaymentStatus;
import com.carrot.payment.dto.PaymentRequest;
import com.carrot.payment.dto.PaymentResponse;
import com.carrot.payment.global.CommonApiResponse;
import com.carrot.payment.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService service;

    // 전체 결제 조회
    @GetMapping
    public ResponseEntity<CommonApiResponse<List<PaymentResponse>>> getAllPayments() {
        List<PaymentResponse> payments = service.getAllPayments();
        return ResponseEntity.ok(CommonApiResponse.successResponse(HttpStatus.OK, payments));
    }

    // 단일 결제 조회
    @GetMapping("/{paymentId}")
    public ResponseEntity<CommonApiResponse<PaymentResponse>> getPaymentById(@PathVariable Long paymentId) {
        PaymentResponse payment = service.getPaymentById(paymentId);
        return ResponseEntity.ok(CommonApiResponse.successResponse(HttpStatus.OK, payment));
    }

    // 사용자 결제 내역 조회
    @GetMapping("/user/{userId}")
    public ResponseEntity<CommonApiResponse<List<PaymentResponse>>> getUserPaymentHistory(@PathVariable Long userId) {
        List<PaymentResponse> payments = service.getUserPaymentsHistory(userId);
        return ResponseEntity.ok(CommonApiResponse.successResponse(HttpStatus.OK, payments));
    }

    // 결제 상태별 조회
    @GetMapping("/status/{status}")
    public ResponseEntity<CommonApiResponse<List<PaymentResponse>>> getPaymentsByStatus(@PathVariable PaymentStatus status) {
        List<PaymentResponse> payments = service.getPaymentsByStatus(status);
        return ResponseEntity.ok(CommonApiResponse.successResponse(HttpStatus.OK, payments));
    }

    // 결제 저장
    @PostMapping("/save")
    public ResponseEntity<CommonApiResponse<PaymentResponse>> savePaymentDetail(@RequestBody PaymentRequest paymentRequest) {
        PaymentResponse savedPayment = service.savePaymentDetail(paymentRequest);
        return ResponseEntity.ok(CommonApiResponse.successResponse(HttpStatus.CREATED, savedPayment));
    }
}
