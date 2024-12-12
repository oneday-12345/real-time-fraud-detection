package com.fraud.detection.entity;

import java.util.Date;


import lombok.Data;

@Data
public class GroupPolicy {
    /**
     * 策略组id
     */
    private Long groupId;

    /**
    * 策略组名称
    */
    private String groupName;

    /**
    * 策略组描述
    */
    private String groupDesc;

    /**
    * 策略组创建者
    */
    private Long groupCreator;

    /**
    * 策略组创建时间
    */
    private Date groupCreateAt;

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
    private String policyDynamicContent;

    /**
    * 策略创建者
    */
    private Long policyCreator;

    /**
    * 策略创建时间
    */
    private Date policyCreateAt;
}
