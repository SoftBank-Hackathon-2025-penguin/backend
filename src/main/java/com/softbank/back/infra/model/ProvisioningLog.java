package com.softbank.back.infra.model;

import java.time.LocalDateTime;

// Lombok을 사용하지 않는 경우 생성자, Getter/Setter 필요
public class ProvisioningLog {
    private InfraStatus status;
    private int progressPercentage;
    private String latestLog;
    private LocalDateTime updateTime;

    public ProvisioningLog(InfraStatus status, int progressPercentage, String latestLog, LocalDateTime updateTime) {
        this.status = status;
        this.progressPercentage = progressPercentage;
        this.latestLog = latestLog;
        this.updateTime = updateTime;
    }

    // Getter 및 Setter는 생략합니다. (실제 프로젝트에서는 Lombok 권장)
    // Getter 예: public InfraStatus getStatus() { return status; }
}