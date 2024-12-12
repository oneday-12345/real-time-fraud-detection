INSERT INTO t_country(c_id, c_name, c_desc, c_creator)
VALUES
(1,'A','A',1),
(2,'B','B',2),
(3,'C','C',3),
(4,'D','D',4)
;

INSERT INTO t_city(ct_id,ct_country_id,ct_name,ct_desc,ct_creator)
VALUES
(1,1,'A1','A1',1),
(2,1,'A2','A2',1),
(3,1,'A3','A3',1),

(4,2,'B1','B1',2),
(5,2,'B2','B2',2),
(6,2,'B3','B3',2),

(7,3,'C1','C1',3),
(8,3,'C2','C2',3),
(9,3,'C3','C3',3),

(10,4,'D1','D1',4),
(11,4,'D2','D2',4),
(12,4,'D3','D3',4)
;


INSERT INTO t_user(u_id, u_name, u_password, u_avatar1,u_avatar2, u_avatar3,u_idcard, u_email,u_phone, u_birthday, u_address, u_city_id, u_valid_flag, u_status, u_creator)
VALUES
(1,'U1','password','avatar1','avatar2','avatar3','idcard','email','phone','1990-01-01','address', 1, 0, 1, 1),
(2,'U2','password','avatar1','avatar2','avatar3','idcard','email','phone','1990-01-01','address', 1, 0, 1, 1),
(3,'U3','password','avatar1','avatar2','avatar3','idcard','email','phone','1990-01-01','address', 1, 0, 1, 1),
(4,'U4','password','avatar1','avatar2','avatar3','idcard','email','phone','1990-01-01','address', 1, 0, 1, 1),
(5,'U5','password','avatar1','avatar2','avatar3','idcard','email','phone','1990-01-01','address', 1, 0, 1, 1),
(6,'U6','password','avatar1','avatar2','avatar3','idcard','email','phone','1990-01-01','address', 1, 0, 1, 1),
(7,'U7','password','avatar1','avatar2','avatar3','idcard','email','phone','1990-01-01','address', 1, 0, 1, 1),
(8,'U8','password','avatar1','avatar2','avatar3','idcard','email','phone','1990-01-01','address', 1, 0, 1, 1),
(9,'U9','password','avatar1','avatar2','avatar3','idcard','email','phone','1990-01-01','address', 1, 0, 1, 1),
(10,'U10','password','avatar1','avatar2','avatar3','idcard','email','phone','1990-01-01','address', 1, 0, 1, 1),
(11,'U11','password','avatar1','avatar2','avatar3','idcard','email','phone','1990-01-01','address', 1, 0, 1, 1),
(12,'U12','password','avatar1','avatar2','avatar3','idcard','email','phone','1990-01-01','address', 1, 0, 1, 1)
;


INSERT INTO t_bank_card(bc_id, bc_card_id,bc_name,bc_type,bc_amount,bc_user_id,bc_creator)
VALUES
(1,1,'a',0,1000,1,1),
(2,2,'a',0,1000,1,1),
(3,3,'b',0,1000,1,1),
(4,4,'b',0,1000,1,1),
(5,5,'c',0,1000,1,1),
(6,6,'c',0,1000,1,1),
(7,7,'d',0,1000,1,1),
(8,8,'d',0,1000,1,1)
;


INSERT INTO t_transaction_from_flow(tff_id, tff_from_back_card_id, tff_to_back_card_id, tff_amount_before, tff_amount, tff_amount_after, tff_create_at)
VALUES
(1,1,2,1000,10,990, '2024-12-08 12:01:03'),
(2,1,2,990,10,980, '2024-12-08 12:02:03'),
(3,1,2,980,10,970, '2024-12-08 12:03:03'),
(4,1,2,970,10,960, '2024-12-08 12:04:03'),
(5,1,2,960,10,950, '2024-12-08 12:05:03'),
(6,1,2,950,10,940, '2024-12-08 12:06:03')
;


INSERT INTO t_transaction_to_flow(ttf_id,ttf_to_back_card_id,ttf_from_back_card_id,ttf_amount_before,ttf_amount,ttf_amount_after,ttf_create_at)
VALUES
(1,1,2,1000,10,1010, '2024-12-08 12:01:03'),
(2,1,2,1010,10,1020, '2024-12-08 12:02:03'),
(3,1,2,1020,10,1030, '2024-12-08 12:03:03'),
(4,1,2,1030,10,1040, '2024-12-08 12:04:03'),
(5,1,2,1040,10,1050, '2024-12-08 12:05:03'),
(6,1,2,1050,10,1060, '2024-12-08 12:06:03')
;



INSERT INTO t_group(g_id, g_name, g_desc, g_creator, g_create_at)
VALUES
(1,'1','1',1,now()),
(2,'2','2',2,now()),
(3,'3','3',3,now()),
(4,'4','4',4,now())
;


INSERT INTO t_policy(p_id, p_name, p_desc, p_dynamic_content, p_creator, p_create_at)
VALUES
(1,'1','1','{"language":"","type":"tag","content":"tag_country"}',1,now()),
(2,'2','2','{"language":"","type":"tag","content":"tag_city"}',1,now()),
(3,'3','3','{"language":"","type":"tag","content":"tag_uid"}',1,now()),
(4,'4','4','{"language":"js","type":"script","content":"function validator(transactionReqBO) {  return transactionReqBO.amount<=1000;   }"}',1,now()),
(5,'5','5','{"language":"js","type":"script","content":"function validator(transactionReqBO) {  return transactionReqBO.amount<=100;  }"}',1,now()),
(6,'6','6','{"language":"js","type":"script","content":"function validator(transactionReqBO) {  return transactionReqBO.amount<=10; }"}',1,now())
;


INSERT INTO t_group_policy(gp_group_id,gp_policy_id,gp_order,gp_creator,gp_create_at)
VALUES
(1,1,1.0,1,now()),
(1,2,2.0,1,now()),
(1,3,3.0,1,now()),
(1,4,4.0,1,now()),
(1,5,5.0,1,now()),
(1,6,6.0,1,now()),

(2,1,1.0,1,now()),
(2,2,2.0,1,now()),
(2,3,3.0,1,now()),
(2,4,4.0,1,now()),
(2,5,5.0,1,now()),
(2,6,6.0,1,now()),

(3,1,1.0,1,now()),
(3,2,2.0,1,now()),
(3,3,3.0,1,now()),

(4,4,4.0,1,now()),
(4,5,5.0,1,now()),
(4,6,6.0,1,now())
;




SELECT g_id, g_name, g_desc, g_creator, g_create_at,
       p_id, p_name, p_desc, p_dynamic_content, p_creator, p_create_at
FROM t_group_policy
LEFT JOIN t_group ON t_group_policy.gp_group_id = t_group.g_id
LEFT JOIN t_policy ON t_group_policy.gp_policy_id = t_policy.p_id
WHERE gp_group_id = 1
ORDER BY gp_order ASC
;





SELECT u_city_id
FROM t_user
WHERE u_id=1
;

SELECT ct_country_id
FROM t_user
LEFT JOIN t_city
ON t_user.u_city_id = t_city.ct_id
WHERE u_id=1
;


SELECT count(1) AS cnt
FROM t_transaction_from_flow
WHERE tff_from_back_card_id='1' AND tff_create_at >= '2024-12-08 00:00:00' AND tff_create_at <= '2024-12-08 23:59:59'
;


SELECT count(1) AS cnt
FROM t_transaction_to_flow
WHERE ttf_to_back_card_id='1' AND ttf_create_at >= '2024-12-08 00:00:00' AND ttf_create_at <= '2024-12-08 23:59:59'
;




