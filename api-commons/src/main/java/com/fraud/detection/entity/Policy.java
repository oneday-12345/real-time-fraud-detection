package com.fraud.detection.entity;


import lombok.Data;
import java.util.Date;


@Data
public class Policy {
    /**
    * 策略id
    */
    private Long policyId;

    /**
    * 策略名称
    */
    private String policyName;

    /**
    * 策略描述
    */
    private String policyDesc;

    /**
    * 策略内容json
    */
    private String policyDynamicScriptContent;

    /**
    * 策略创建者
    */
    private Long policyCreator;

    /**
    * 策略创建时间
    */
    private Date policyCreateAt;
}
