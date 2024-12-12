package com.fraud.detection.engine.fsm;

import java.util.List;

import com.fraud.detection.bo.TransactionReqBO;
import com.fraud.detection.bo.TransactionResBO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class StateContext<T> {
    public TransactionReqBO input;
    public TransactionResBO output;
    public List<String> path;
    public T data;
}
