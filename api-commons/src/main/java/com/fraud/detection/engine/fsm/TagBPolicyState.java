package com.fraud.detection.engine.fsm;

import com.fraud.detection.constants.PolicyStateName;

public class TagBPolicyState implements IPolicyState {

    private String nexString;

    public String handler(StateContext<?> stateContext) {
        /**
         * 根据业务逻辑，返回下一个状态的名字
         * Start -> ScriptA -> ScriptB -> TagA -> TagB -> End
         */
        setNextState(PolicyStateName.END);
        return getNextState();

    }

    public String stateName() {
        return PolicyStateName.TAGB;
    }

    public void setNextState(String nextState) {
        this.nexString = nextState;
    }

    public String getNextState() {
        return this.nexString;
    }

}
