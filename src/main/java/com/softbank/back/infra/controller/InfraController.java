package com.softbank.back.infra.controller;

import com.softbank.back.infra.model.InfraStatus;
import com.softbank.back.infra.model.ProvisioningLog;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/infra")
public class InfraController {

    // (의존성 주입 생략: TerraformService)

    /**
     * FR-01: 비동기 인프라 배포 요청
     * 실제로는 TerraformService.applyAsync()를 호출하여 비동기 작업을 시작합니다.
     */
    @PostMapping("/create")
    public ResponseEntity<String> createInfrastructure() {
        // 실제로는 비동기 작업 시작 및 중복 요청 방지 로직이 포함됩니다.
        System.out.println(">> [InfraController] Terraform apply 요청 시작. (FR-01)");
        return ResponseEntity.accepted().body("Infrastructure provisioning started.");
    }

    /**
     * FR-04: 원클릭 리소스 파괴 요청
     */
    @PostMapping("/destroy")
    public ResponseEntity<String> destroyInfrastructure() {
        System.out.println(">> [InfraController] Terraform destroy 요청 시작. (FR-04)");
        return ResponseEntity.accepted().body("Infrastructure destruction started.");
    }

    /**
     * FR-02: 실시간 상태 폴링
     * 현재는 더미 데이터를 반환합니다.
     */
    @GetMapping("/status")
    public ResponseEntity<ProvisioningLog> getProvisioningStatus() {
        // 실제로는 TerraformService에서 현재 상태와 로그를 조회합니다.
        ProvisioningLog log = new ProvisioningLog(
                InfraStatus.APPLYING,
                75, // 75% 진행률
                "Creating EC2 instance... (Step 5 of 7)",
                LocalDateTime.now()
        );
        return ResponseEntity.ok(log);
    }

    /**
     * FR-03: 리소스 시각화 및 정보 제공
     */
    @GetMapping("/info")
    public ResponseEntity<String> getInfrastructureInfo() {
        // 실제로는 Terraform output 파싱 결과를 반환합니다.
        String dummyOutput = "{\"ec2_ip\": \"13.125.10.10\", \"s3_bucket\": \"penguin-land-bucket\"}";
        return ResponseEntity.ok(dummyOutput);
    }
}