package com.fraud.detection.engine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.fraud.detection.constants.PolicyStateName;
import com.fraud.detection.engine.fsm.EndPolicyState;
import com.fraud.detection.engine.fsm.IPolicyState;
import com.fraud.detection.engine.fsm.ScriptAPolicyState;
import com.fraud.detection.engine.fsm.ScriptBPolicyState;
import com.fraud.detection.engine.fsm.StartPolicyState;
import com.fraud.detection.engine.fsm.StateContext;
import com.fraud.detection.engine.fsm.TagAPolicyState;
import com.fraud.detection.engine.fsm.TagBPolicyState;
import com.fraud.detection.enums.Code;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class FSMEngine {

    private Map<String, IPolicyState> states = new HashMap<>();

    private int total = 1_000;

    public FSMEngine() {
        init();
    }

    public void init() {
        states.put(PolicyStateName.START, new StartPolicyState());
        states.put(PolicyStateName.END, new EndPolicyState());
        states.put(PolicyStateName.SCRIPTA, new ScriptAPolicyState());
        states.put(PolicyStateName.SCRIPTB, new ScriptBPolicyState());
        states.put(PolicyStateName.TAGA, new TagAPolicyState());
        states.put(PolicyStateName.TAGB, new TagBPolicyState());
    }

    public Code detectTransactionFraud(String[] stateNames, StateContext<?> stateContext) {
        List<String> path = new ArrayList<>();
        stateContext.path = path;
        try {
            for (int i = 0; i < stateNames.length; ++i) {
                String stateName = stateNames[i];
                path.add(stateName);
                IPolicyState policyState = states.get(stateName);
                if (policyState == null) {
                    log.warn("detectTransactionFraud fsm state error, stateNames={}, stateContext={}, path={}",
                            stateNames,
                            stateContext, path);
                    return Code.FSM_STATE_ERROR;
                }
                policyState.handler(stateContext);
            }
        } catch (Exception e) {
            log.warn("detectTransactionFraud stateNames={}, stateContext={}, path={}", stateNames, stateContext, path);
            throw e;
        }

        log.info("detectTransactionFraud stateNames={}, stateContext={}, path={}", stateNames, stateContext, path);

        return Code.SUCCESS;
    }

    public Code detectTransactionFraud(String stateName, StateContext<?> stateContext) {

        List<String> path = new ArrayList<>();
        stateContext.path = path;

        String nextStateName = stateName;
        try {

            for (int count = 0; !nextStateName.equals(PolicyStateName.END) && count < total; ++count) {

                if (count >= total - 1) {
                    log.warn("detectTransactionFraud infinite state error, stateName={}, stateContext={}, path={}",
                            stateName,
                            stateContext, path);
                    return Code.FSM_STATE_INFINITE_ERROR;
                }

                path.add(nextStateName);
                IPolicyState policyState = states.get(nextStateName);

                if (policyState == null) {
                    log.warn("detectTransactionFraud fsm state error, stateName={}, stateContext={}, path={}",
                            stateName,
                            stateContext, path);
                    return Code.FSM_STATE_ERROR;
                }

                nextStateName = policyState.handler(stateContext);
            }

            path.add(PolicyStateName.END);
            IPolicyState policyState = states.get(PolicyStateName.END);
            policyState.handler(stateContext);

        } catch (Exception e) {
            log.warn("FSM stateContext failed. nextStateName={}, stateContext={}, path={}", nextStateName, stateContext,
                    path, e);
            throw e;
        }

        log.info("FSM detectTransactionFraud nextStateName={}, stateContext={}, path={}", nextStateName, stateContext,
                path);

        return Code.SUCCESS;
    }

}
