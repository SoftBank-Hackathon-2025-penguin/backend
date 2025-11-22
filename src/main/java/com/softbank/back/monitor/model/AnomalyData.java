package com.softbank.back.monitor.model;

import lombok.Getter;
import lombok.Setter;

/**
 * FR-06: SNS Webhook 알림의 JSON 구조를 매핑하는 모델
 */
@Getter
@Setter
public class AnomalyData {
    private String Type; // Notification or SubscriptionConfirmation
    private String MessageId;
    private String TopicArn;
    private String Subject;
    private String Message; // CloudWatch Alarm 상세 내용이 JSON 스트링 형태로 들어옴
    private String SubscribeURL; // 구독 확정용 URL

    // Getter 및 Setter는 생략합니다. (JSON 바인딩을 위해 필요)
}
