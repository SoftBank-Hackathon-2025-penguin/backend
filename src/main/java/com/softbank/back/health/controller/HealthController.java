package com.softbank.back.health.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class HealthController {

    // CloudWatch Logs에 기록될 로그를 생성하기 위한 로거 객체
    private static final Logger logger = LoggerFactory.getLogger(HealthController.class);

    /**
     * 서버 상태 확인 (로드 밸런서 헬스체크용)
     */
    @GetMapping("/health")
    public ResponseEntity<String> checkHealth() {
        return ResponseEntity.ok("UP");
    }

    /**
     * CloudWatch Logs 연동 테스트 API
     * ERROR 레벨 로그를 생성하여 CloudWatch Logs 그룹에 기록되는지 확인합니다.
     */
    @GetMapping("/api/v1/test/log")
    public ResponseEntity<String> testLogIntegration() {
        logger.info(">> [TEST_LOG] INFO 레벨 로그를 생성합니다.");
        logger.warn(">> [TEST_LOG] WARN 레벨 로그를 생성합니다. (경고)");
        logger.error(">> [TEST_LOG] !!! CRITICAL ERROR LOG !!! (CloudWatch 경보 대상)");

        return ResponseEntity.ok("INFO, WARN, ERROR 로그가 CloudWatch Logs로 전송되었습니다. 확인하세요.");
    }
}