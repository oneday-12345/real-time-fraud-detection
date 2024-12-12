package com.fraud.detection.engine.policy;

import com.fraud.detection.bo.TransactionReqBO;

public interface PolicyTagHandler {

    public Boolean handler(String handlerTag, TransactionReqBO handlerTransactionReqBO);

}
