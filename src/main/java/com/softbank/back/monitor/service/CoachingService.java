package com.softbank.back.monitor.service;

import com.softbank.back.monitor.model.PenguinStatus;
import org.springframework.stereotype.Service;

@Service
public class CoachingService {

    private boolean isSimulationMode = false;

    /**
     * FR-07: CloudWatch 데이터 기반 펭귄 상태 계산
     */
    public PenguinStatus calculatePenguinStatus() {
        if (isSimulationMode) {
            // FR-08: 시뮬레이션 모드 활성화 시 강제 위험 상태 반환
            return new PenguinStatus(
                    95,
                    "CRITICAL",
                    "🚨 CPU가 과열되고 있어요! 시뮬레이션 모드 활성화됨."
            );
        }

        // 실제 로직: CloudWatch API를 호출하여 최근 메트릭(CPU, Latency 등)을 조회하고
        // FR-06의 임계값 기준에 따라 Risk Score를 계산합니다.
        return new PenguinStatus(
                25,
                "STABLE",
                "👍 아주 안정적이에요! 모든 메트릭이 정상 범위입니다."
        );
    }

    /**
     * FR-08: 시뮬레이션 모드 전환
     */
    public void setSimulationMode(boolean enable) {
        this.isSimulationMode = enable;
        System.out.println("Simulation Mode set to: " + enable);
    }

    /**
     * FR-06: SNS Webhook으로부터 받은 알람 데이터 처리
     */
    public void processAlarm(String rawAlarmMessage) {
        // 실제 로직: rawAlarmMessage(JSON)를 파싱하여 어떤 경보가 발생했는지 확인하고
        // 펭귄 상태를 즉시 'WARNING' 또는 'CRITICAL'로 업데이트하는 로직이 들어갑니다.
        System.out.println("[Service] Received and Processing Alarm Message: " + rawAlarmMessage);
    }
}