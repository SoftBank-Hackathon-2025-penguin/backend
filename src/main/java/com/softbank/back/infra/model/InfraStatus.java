package com.softbank.back.infra.model;

/**
 * FR-02: Terraform 배포 상태를 나타내는 ENUM
 */
public enum InfraStatus {
    INIT, PLANNING, APPLYING, COMPLETE, FAILED, DESTROYING
}