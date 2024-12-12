
package com.fraud.detection.service;

import com.fraud.detection.engine.RedisService;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EventConsumeService {

    @Autowired
    private RedisService redisService;

    @Autowired
    private ConsumerService consumerService;

    void handlerEvent(String msg) {
    }

}
