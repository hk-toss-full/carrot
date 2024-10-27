package com.carrot.payment.repository;

import com.carrot.payment.domain.Payment;
import com.carrot.payment.domain.PaymentStatus;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    @Modifying
    @Transactional
    @Query("UPDATE Payment p SET p.status = :status, p.updatedAt = CURRENT_TIMESTAMP WHERE p.id = :paymentId")
    void updatePaymentStatus(Long paymentId, PaymentStatus status);

    List<Payment> findByUserId(Long userId);

    List<Payment> findByStatus(PaymentStatus status);
}
