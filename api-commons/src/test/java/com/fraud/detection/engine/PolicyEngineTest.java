package com.fraud.detection.engine;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.fraud.detection.bo.TransactionReqBO;
import com.fraud.detection.enums.Code;

@SpringBootTest
@RunWith(SpringRunner.class)
public class PolicyEngineTest {

    @Autowired
    private PolicyEngine policyEngine;

    @Test
    public void testDefaultPoliciyGroupDeny() {
        System.out.println(111);

        Integer defaultGroupId = 1;

        TransactionReqBO transactionReqBO = TransactionReqBO
                .builder()
                .fromUid(1L)
                .toUid(2L)
                .amount(100)
                .build();
        Code code = policyEngine.detectTransactionFraud(defaultGroupId, transactionReqBO);
        assertEquals("default policy, deny.", Code.VALIDATOR_DENY.getCode(), code.getCode());
    }

    @Test
    public void testDefaultPoliciyGroupPass() {
        System.out.println(111);

        Integer defaultGroupId = 1;

        TransactionReqBO transactionReqBO = TransactionReqBO
                .builder()
                .fromUid(1L)
                .toUid(2L)
                .amount(5)
                .build();
        Code code = policyEngine.detectTransactionFraud(defaultGroupId, transactionReqBO);
        assertEquals("default policy, pass.", Code.SUCCESS.getCode(), code.getCode());
    }

    @Test
    public void testScriptHandlerPass() {

        String language = "js";
        String content = "function validator(transactionReqBO) {  return transactionReqBO.amount<=1000;   }";
        TransactionReqBO transactionReqBO = TransactionReqBO.builder()
                .fromUid(1L)
                .toUid(2L)
                .amount(100)
                .build();

        Code rest = policyEngine.scriptHandler(language, content, transactionReqBO);
        assertEquals("Javascript function", Code.SUCCESS.getCode(), rest.getCode());

    }

    @Test
    public void testScriptHandlerDeny() {

        String language = "js";
        String content = "function validator(transactionReqBO) {  return transactionReqBO.amount<=1000;   }";
        TransactionReqBO transactionReqBO = TransactionReqBO.builder()
                .fromUid(1L)
                .toUid(2L)
                .amount(1000 + 1)
                .build();

        Code rest = policyEngine.scriptHandler(language, content, transactionReqBO);
        assertEquals("Javascript function", Code.VALIDATOR_DENY.getCode(), rest.getCode());

    }
}
