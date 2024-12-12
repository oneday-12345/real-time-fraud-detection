package com.fraud.detection.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

import com.fraud.detection.enums.Code;
import com.fraud.detection.mapper.PolicyExMapper;
import com.fraud.detection.bo.TransactionReqBO;
import com.fraud.detection.bo.TransactionResBO;
import com.fraud.detection.engine.FSMEngine;
import com.fraud.detection.engine.PolicyEngine;
import com.fraud.detection.engine.fsm.StateContext;

@Slf4j
@Service
public class FraudDetectionService {

    @Autowired
    private PolicyEngine policyEngine;

    @Autowired
    private FSMEngine fsmEngine;

    @Autowired
    private PolicyExMapper policyExMapper;


    public Code detectTransactionFraud(Integer groupId, TransactionReqBO transactionReqBO) {

        return policyEngine.detectTransactionFraud(groupId, transactionReqBO);
    }

    public Code detectTransactionFraud(String stateName, TransactionReqBO input, TransactionResBO output) {
        StateContext<Object> stateContext = StateContext.builder()
                .input(input)
                .output(output)
                .data(null)
                .build();
        return fsmEngine.detectTransactionFraud(stateName, stateContext);
    }

    public Code detectTransactionFraud(String[] stateNames, TransactionReqBO input, TransactionResBO output) {
        StateContext<Object> stateContext = StateContext.builder()
                .input(input)
                .output(output)
                .data(null)
                .build();
        return fsmEngine.detectTransactionFraud(stateNames, stateContext);
    }

}
