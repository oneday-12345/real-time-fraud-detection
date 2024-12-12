package com.fraud.detection.controller;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fraud.detection.bo.TransactionReqBO;
import com.fraud.detection.enums.Code;
import com.fraud.detection.model.Result;
import com.fraud.detection.service.FraudDetectionService;

@Slf4j
@RestController
@RequestMapping("/v1/fraud")
public class FraudDetectionController {

    @Autowired
    private FraudDetectionService fraudDetectionService;

    // @TODO: Post
    // 硬编码参数，方便开发测试
    // 实际换成一个post
    @GetMapping("/detect")
    public Result<Boolean> detect(@RequestParam("groupId") Integer groupId) {
        if (groupId == null) {
            groupId = 1;
        }
        TransactionReqBO transactionReqBO = TransactionReqBO
                .builder()
                .fromUid(1L)
                .toUid(2L)
                .amount(100)
                .build();
        Code result = fraudDetectionService.detectTransactionFraud(groupId, transactionReqBO);

        return Result.<Boolean>error(result).setData(true);
    }

}
