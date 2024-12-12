package com.fraud.detection.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import org.apache.ibatis.annotations.Mapper;

import com.fraud.detection.entity.GroupPolicy;

@Mapper
public interface PolicyMapper {

        @Select("""
                        SELECT g_id                                 AS         groupId ,
                        g_name                               AS         groupName ,
                        g_desc                               AS         groupDesc ,
                        g_creator                            AS         groupCreator ,
                        g_create_at                          AS         groupCreateAt ,
                        p_id                                 AS         policyId ,
                        p_name                               AS         policyName ,
                        p_desc                               AS         policyDesc ,
                        p_dynamic_content                    AS         policyDynamicContent ,
                        p_creator                            AS         policyCreator ,
                        p_create_at                          AS         policyCreateAt
                        FROM t_group_policy
                        LEFT JOIN t_group ON t_group_policy.gp_group_id = t_group.g_id
                        LEFT JOIN t_policy ON t_group_policy.gp_policy_id = t_policy.p_id
                        WHERE gp_group_id = #{groupId}
                        ORDER BY gp_order ASC
                        """)
        public List<GroupPolicy> queryGroupPolicyByGroupId(int groupId);

        @Select("""
                        SELECT ct_country_id
                        FROM t_user
                        LEFT JOIN t_city
                        ON t_user.u_city_id = t_city.ct_id
                        WHERE u_id=#{userId}
                        """)
        public Long getCountryId(Long userId);

        @Select("""
                        SELECT u_city_id
                        FROM t_user
                        WHERE u_id=#{userId}
                        """)
        public Long getCityId(Long userId);

        @Select("""
                        SELECT COUNT(1) AS cnt
                        FROM t_transaction_from_flow
                        WHERE tff_from_back_card_id=#{cardId} AND tff_create_at >= #{startDate} AND tff_create_at <= #{endDate}
                        """)
        public Long transferFromCount(
                        @Param("cardId") Long cardId,
                        @Param("startDate") Date startDate,
                        @Param("endDate") Date endDate);

        @Select("""
                        SELECT COUNT(1) AS cnt
                        FROM t_transaction_to_flow
                        WHERE ttf_to_back_card_id=#{cardId} AND ttf_create_at >= #{startDate} AND ttf_create_at <= #{endDate}
                        """)
        public Long transferToCount(
                        @Param("cardId") Long cardId,
                        @Param("startDate") Date startDate,
                        @Param("endDate") Date endDate);

}
