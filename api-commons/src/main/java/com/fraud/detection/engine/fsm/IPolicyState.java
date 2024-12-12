package com.fraud.detection.engine.fsm;

public interface IPolicyState {

    public String stateName();

    public String handler(StateContext<?> stateContext);

    public void setNextState(String nextState);

    public String getNextState();

}
