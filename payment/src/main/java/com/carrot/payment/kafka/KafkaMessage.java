package com.carrot.payment.kafka;

import com.carrot.payment.domain.Payment;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class KafkaMessage {
    private LocalDateTime timestamp;  // 메시지 전송 시간
    private Payment payment;          // Payment 정보
    private String action;            // 승인, 취소, 생성 등 액션 타입

    @Override
    public String toString() {
        return "KafkaMessage{" +
                "timestamp=" + timestamp +
                ", payment=" + payment +
                ", action='" + action + '\'' +
                '}';
    }
}
