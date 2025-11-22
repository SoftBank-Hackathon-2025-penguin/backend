package com.softbank.back.monitor.model;

/**
 * FR-07: 게이미피케이션 상태를 나타내는 DTO
 */
public class PenguinStatus {
    private int riskScore; // 0-100점
    private String statusLevel; // STABLE, WARNING, CRITICAL
    private String coachingMessage;

    public PenguinStatus(int riskScore, String statusLevel, String coachingMessage) {
        this.riskScore = riskScore;
        this.statusLevel = statusLevel;
        this.coachingMessage = coachingMessage;
    }

    // Getter 및 Setter는 생략합니다.
}