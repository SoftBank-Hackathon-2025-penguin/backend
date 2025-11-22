package com.softbank.back.monitor.controller;

import com.softbank.back.monitor.model.PenguinStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/monitor")
public class MonitorController {

    // (의존성 주입 생략: CoachingService)

    /**
     * FR-07: 게이미피케이션 상태 조회 (펭귄 상태)
     * 현재는 "주의" 상태 더미 데이터를 반환합니다.
     */
    @GetMapping("/status")
    public ResponseEntity<PenguinStatus> getMonitoringStatus() {
        // 실제로는 CoachingService에서 CloudWatch 데이터 분석 결과를 반환합니다.
        PenguinStatus status = new PenguinStatus(
                45, // 45점 (주의 범위)
                "WARNING", // 상태
                "⚠️ 조금 불안정해 보여요. 네트워크 레이턴시가 높아지고 있습니다."
        );
        return ResponseEntity.ok(status);
    }

    /**
     * FR-08: 데모용 시뮬레이션 모드 (강제 위험 신호 트리거)
     */
    @PostMapping("/simulate")
    public ResponseEntity<String> triggerSimulation(@RequestParam boolean enable) {
        if (enable) {
            // 실제로는 CoachingService에 강제 위험 상태 진입을 알립니다.
            System.out.println(">> [MonitorController] 강제 위험 시뮬레이션 시작.");
            return ResponseEntity.ok("Simulation mode ENABLED. System status forced to CRITICAL.");
        } else {
            System.out.println(">> [MonitorController] 시뮬레이션 모드 종료.");
            return ResponseEntity.ok("Simulation mode DISABLED. System status returning to normal.");
        }
    }
}