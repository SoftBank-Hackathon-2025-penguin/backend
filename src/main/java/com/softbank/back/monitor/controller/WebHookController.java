package com.softbank.back.monitor.controller;
import com.softbank.back.monitor.model.AnomalyData;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * CloudWatch Alarm -> SNS -> Backend Webhook Flow를 처리하는 컨트롤러입니다.
 */
@RestController
@RequestMapping("/api/v1/monitor")
public class WebHookController {

    // (의존성 주입 생략: CoachingService)

    /**
     * FR-06: CloudWatch/SNS 알림 수신 엔드포인트
     * CloudWatch Alarm이 SNS를 통해 보낸 JSON 메시지를 받습니다.
     */
    @PostMapping("/webhook")
    public ResponseEntity<String> receiveAlarmWebhook(@RequestBody AnomalyData anomalyData) {
        // 실제로는 SNS Type 확인 및 메시지 구독/확인 로직이 필요합니다.

        if ("Notification".equals(anomalyData.getType())) {
            System.out.println(">> [WebhookController] CloudWatch Alarm 수신: " + anomalyData.getSubject());
            // 실제로는 CoachingService.processAlarm(anomalyData.getMessage()) 호출
            return ResponseEntity.ok("Alarm Notification Received and Processed.");
        } else if ("SubscriptionConfirmation".equals(anomalyData.getType())) {
            // SNS 토픽 구독 확인 메시지 처리 (첫 구독 시 필수)
            System.out.println(">> [WebhookController] SNS Subscription Confirmation: " + anomalyData.getSubscribeURL());
            // 실제로는 subscribeURL로 GET 요청을 보내 구독을 확정해야 함
            return ResponseEntity.ok("Subscription Confirmation Handled.");
        }

        return ResponseEntity.badRequest().body("Unsupported SNS message type.");
    }
}