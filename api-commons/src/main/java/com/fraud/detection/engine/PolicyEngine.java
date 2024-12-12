package com.fraud.detection.engine;

import java.util.Map;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;

import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Value;

import com.fraud.detection.bo.TransactionReqBO;
import com.fraud.detection.constants.PolicyDynamicContentKey;
import com.fraud.detection.constants.PolicyType;
import com.fraud.detection.engine.policy.PolicyTagHandler;
import com.fraud.detection.entity.GroupPolicy;
import com.fraud.detection.enums.Code;
import com.fraud.detection.mapper.PolicyMapper;
import com.fraud.detection.constants.PolicyTagKey;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class PolicyEngine {

    @Autowired
    private RedisService redisService;

    @Autowired
    private PolicyMapper policyMapper;

    private final static Map<String, Value> validators = new HashMap<>();

    public Code detectTransactionFraud(Integer groupId, TransactionReqBO transactionReqBO) {

        List<GroupPolicy> groupPolicys = policyMapper.queryGroupPolicyByGroupId(groupId);

        Code validatorResult = Code.VALIDATOR_DENY;

        List<Integer> path = new ArrayList<>();

        try {

            for (int i = 0; i < groupPolicys.size(); ++i) {

                path.add(i);

                GroupPolicy groupPolicy = groupPolicys.get(i);

                String scriptContent = groupPolicy.getPolicyDynamicContent();

                JSONObject jsonObject = JSON.parseObject(scriptContent);

                String language = jsonObject.getString(PolicyDynamicContentKey.LANGUAGE);
                String type = jsonObject.getString(PolicyDynamicContentKey.TYPE);
                String content = jsonObject.getString(PolicyDynamicContentKey.CONTENT);

                if (PolicyType.TAG.equals(type)) {
                    validatorResult = tagHandler(content, transactionReqBO);
                } else if (PolicyType.SCRIPT.equals(type)) {
                    validatorResult = scriptHandler(language, content, transactionReqBO);
                } else {
                    log.warn(
                            "detectTransactionFraud unknown policy type. groupId={}, transactionReqBO={}, content={}, PolicyType = {}, path={}",
                            groupId, transactionReqBO, content, type, path);
                    return Code.VALIDATOR_DENY;
                }

                if (validatorResult.equals(Code.VALIDATOR_DENY)) {
                    log.info(
                            "detectTransactionFraud denyed. groupId={}, transactionReqBO={}, content={}, PolicyType = {}, path={}",
                            content, type,
                            path);
                    return Code.VALIDATOR_DENY;
                }

            }
        } catch (Exception e) {
            log.warn(
                    "detectTransactionFraud failed. groupId={}, transactionReqBO={}, path={}",
                    groupId, transactionReqBO, path);

            throw e;
        }

        log.info(
                "detectTransactionFraud denyed. groupId={}, transactionReqBO={}, path={}", groupId, transactionReqBO,
                path);
        return Code.SUCCESS;

    }

    public Code tagHandler(String tag, TransactionReqBO transactionReqBO) {
        /**
         * @TODO: 填充所有的tag处理
         *        (1) 可以封装在 private 函数里面
         *        (2) 可以封装在 匿名函数里面
         *        (3) 可以封装在 TagHandler 子类里面
         */

        PolicyTagHandler countryTagHandler = (String handlerTag, TransactionReqBO handlerTransactionReqBO) -> {
            Long countryId = policyMapper.getCountryId(transactionReqBO.fromUid);
            Boolean isExist = redisService.isExist(PolicyTagKey.TAG_COUNTRY, countryId.toString());
            if (isExist) {
                log.info("Denied by to tag. tag={}, transactionReqBO={}", tag, transactionReqBO);
                return true;
            }
            return isExist;
        };

        PolicyTagHandler uidTagHandler = (String handlerTag, TransactionReqBO handlerTransactionReqBO) -> {
            Boolean isExistFrom = redisService.isExist(PolicyTagKey.TAG_UID,
                    handlerTransactionReqBO.fromUid.toString());
            if (isExistFrom) {
                log.info("Denied by from tag. tag={}, transactionReqBO={}", tag, transactionReqBO);
                return true;
            }
            Boolean isExistTo = redisService.isExist(PolicyTagKey.TAG_UID, handlerTransactionReqBO.toUid.toString());
            if (isExistTo) {
                log.info("Denied by to tag. tag={}, transactionReqBO={}", tag, transactionReqBO);
                return true;
            }
            return false;
        };

        switch (tag) {
            case PolicyTagKey.TAG_COUNTRY: {
                boolean isExist = countryTagHandler.handler(tag, transactionReqBO);
                if (isExist) {
                    return Code.VALIDATOR_DENY;
                }
            }
                break;
            case PolicyTagKey.TAG_CITY:
                break;
            case PolicyTagKey.TAG_PHONE:
                break;
            case PolicyTagKey.TAG_EMAIL:
                break;
            case PolicyTagKey.TAG_UID: {
                Boolean isExist = uidTagHandler.handler(tag, transactionReqBO);
                if (isExist) {
                    return Code.VALIDATOR_DENY;
                }
            }
                break;
            case PolicyTagKey.TAG_FROM_UID:
                break;
            case PolicyTagKey.TAG_TO_UID:
                break;
            case PolicyTagKey.TAG_CARDID:
                break;
            case PolicyTagKey.TAG_FROM_CARDID:
                break;
            case PolicyTagKey.TAG_TO_CARDID:
                break;
            case PolicyTagKey.TAG_IP_ADRESS:
                break;
            default:
                break;
        }
        return Code.SUCCESS;
    }

    public Code scriptHandler(String language, String content, TransactionReqBO transactionReqBO) {
        StringBuilder sb = new StringBuilder(content);
        sb.append(";")
                .append(PolicyDynamicContentKey.VALIDATOR);

        Context context = Context.newBuilder()
                .allowAllAccess(true)
                .build();

        try {

            Value validator = getOrCreateValidator(sb.toString(), language, context);

            Value result = validator.execute(transactionReqBO);

            boolean b = result.asBoolean();
            if (!b) {
                return Code.VALIDATOR_DENY;
            }
        } catch (Exception e) {
            log.warn("Script handler has error. language={}, content={}, transactionReqBO={}", language,
                    content, transactionReqBO, e);
            return Code.VALIDATOR_FUNCTION_ERROR;
        }
        return Code.SUCCESS;
    }

    private Value getOrCreateValidator(String strValidator, String language, Context context) {
        // 减少创建 validator，提高性能
        Value validator = null;
        if (PolicyEngine.validators.containsKey(strValidator)) {
            validator = PolicyEngine.validators.get(strValidator);
        } else {
            validator = context.eval(language, strValidator);
            PolicyEngine.validators.put(strValidator, validator);
        }
        return validator;
    }
}
