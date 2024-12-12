package com.fraud.detection.engine;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.fraud.detection.constants.PolicyStateName;
import com.fraud.detection.engine.fsm.StateContext;
import com.fraud.detection.enums.Code;

@SpringBootTest
@RunWith(SpringRunner.class)
public class FSMEngineTest {
    @Autowired
    private FSMEngine fsmEngine;

    @Test
    public void testFSMEngineStartWithArray() {
        String[] states = new String[] {
                PolicyStateName.START,
                PolicyStateName.SCRIPTA,
                PolicyStateName.SCRIPTB,
                PolicyStateName.TAGA,
                PolicyStateName.TAGB,
                PolicyStateName.END
        };

        StateContext<Object> stateContext = new StateContext<>();
        Code result = fsmEngine.detectTransactionFraud(states, stateContext);
        assertEquals("FSMEngine", Code.SUCCESS.getCode(), result.getCode());
        assertEquals("Path has the same length.", states.length, stateContext.path.size());
        String[] path = stateContext.path.toArray(new String[0]);
        assertArrayEquals(states, path);
    }

    @Test
    public void testFSMEngineStartWithState() {

        String[] states = new String[] {
                PolicyStateName.START,
                PolicyStateName.SCRIPTA,
                PolicyStateName.SCRIPTB,
                PolicyStateName.TAGA,
                PolicyStateName.TAGB,
                PolicyStateName.END
        };

        StateContext<Object> stateContext = new StateContext<>();
        Code result = fsmEngine.detectTransactionFraud(PolicyStateName.START, stateContext);
        assertEquals("FSMEngine", Code.SUCCESS.getCode(), result.getCode());
        assertEquals("Path has the same length.", states.length, stateContext.path.size());
        String[] path = stateContext.path.toArray(new String[0]);
        assertArrayEquals(states, path);
    }

}
