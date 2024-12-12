package com.fraud.detection.entity;

import java.util.Date;


import lombok.Data;

@Data
public class Group {
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
}
