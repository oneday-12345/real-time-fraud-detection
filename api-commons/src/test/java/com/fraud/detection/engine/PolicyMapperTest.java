package com.fraud.detection.engine;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.fraud.detection.mapper.PolicyMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class PolicyMapperTest {

    @Autowired
    PolicyMapper policyMapper;

    @Test
    public void testPolicyEnginePolicyMapper() {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Date begin = null;
        Date end = null;

        try {
            begin = simpleDateFormat.parse("2024-12-08 00:00:00");
            end = simpleDateFormat.parse("2024-12-08 23:59:59");
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        Long contryId = policyMapper.getCountryId(1L);

        assertEquals(1, contryId);

        Long cityId = policyMapper.getCityId(1L);

        assertEquals(1, cityId);

        Long a = policyMapper.transferFromCount(1L, begin, end);
        Long b = policyMapper.transferToCount(1L, begin, end);

        assertEquals(6, a);
        assertEquals(6, b);

    }
}
