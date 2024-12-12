package com.fraud.detection.bo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TransactionReqBO {

    public Long fromUid;
    public Long fromCardId;

    public Long toUid;
    public Long toCardId;

    public double amount;

    public String ipAdress;
}
