package com.softbank.back.infra.service;

import com.softbank.back.infra.model.ProvisioningLog;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TerraformService {

    private boolean isProvisioningInProgress = false;

    /**
     * FR-01: 비동기 인프라 배포 시작 (실제로는 ProcessBuilder 실행)
     */
    public boolean startProvisioning() {
        if (isProvisioningInProgress) {
            return false; // 중복 요청 방지
        }
        isProvisioningInProgress = true;

        // 실제로는 여기에서 별도 스레드/CompletableFuture를 사용하여
        // ProcessBuilder.start("terraform apply...") 실행 로직이 들어갑니다.
        System.out.println("[Service] Terraform Apply Started.");
        return true;
    }

    /**
     * FR-04: 리소스 파괴 시작 (실제로는 ProcessBuilder 실행)
     */
    public void startDestroy() {
        System.out.println("[Service] Terraform Destroy Started.");
        // 실제로는 "terraform destroy" 실행 로직이 들어갑니다.
    }

    /**
     * FR-02: 실시간 상태 조회 (더미)
     */
    public ProvisioningLog getCurrentStatus() {
        // 실제 로직: 현재 실행 중인 프로세스의 로그를 파싱하여 상태를 업데이트합니다.
        // 여기서는 임시 로그를 반환합니다.
        return new ProvisioningLog(
                com.softbank.back.infra.model.InfraStatus.APPLYING,
                80,
                "Finished creating S3 bucket. Proceeding to DynamoDB...",
                LocalDateTime.now()
        );
    }
}