
DROP DATABASE frauddetection
;

CREATE DATABASE frauddetection
;

USE frauddetection
;


CREATE TABLE `t_country` (
  `c_id` INT UNSIGNED AUTO_INCREMENT NOT NULL COMMENT '主键id，国家id',
  `c_name` VARCHAR(255) NOT NULL DEFAULT '' COMMENT '国家名称',
  `c_desc` VARCHAR(255) NOT NULL DEFAULT '' COMMENT '描述信息',
  `c_creator` BIGINT UNSIGNED NOT NULL DEFAULT 0 COMMENT '创建者id',
  `c_create_at` DATETIME NOT NULL DEFAULT NOW() COMMENT '创建时间',
  PRIMARY KEY (`c_id`),
  INDEX `idx_name` (`c_name` ASC)
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COMMENT = '国家表'
;


CREATE TABLE `t_city` (
  `ct_id` INT UNSIGNED AUTO_INCREMENT NOT NULL COMMENT '主键id，城市id',
  `ct_country_id` INT UNSIGNED NOT NULL DEFAULT 0 COMMENT '城市所属国家id',
  `ct_name` VARCHAR(127) NOT NULL DEFAULT '' COMMENT '城市名称',
  `ct_desc` VARCHAR(255) NOT NULL DEFAULT '' COMMENT '描述信息',
  `ct_creator` BIGINT UNSIGNED NOT NULL DEFAULT 0 COMMENT '创建者id',
  `ct_create_at` DATETIME NOT NULL DEFAULT NOW() COMMENT '创建时间',
  PRIMARY KEY (`ct_id`,`ct_country_id`),
  INDEX `idx_name` (`ct_name` ASC)
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COMMENT = '国家城市表'
;

CREATE TABLE `t_user` (
  `u_id` BIGINT UNSIGNED NOT NULL COMMENT '主键id，用户id',
  `u_name` VARCHAR(127) NOT NULL DEFAULT '' COMMENT '用户名',
  `u_password` VARCHAR(255) NOT NULL DEFAULT '' COMMENT '加密后密码',
  `u_avatar1` VARCHAR(255) NOT NULL DEFAULT '' COMMENT '用户头像1',
  `u_avatar2` VARCHAR(255) NOT NULL DEFAULT '' COMMENT '用户头像2',
  `u_avatar3` VARCHAR(255) NOT NULL DEFAULT '' COMMENT '用户头像3',
  `u_idcard` VARCHAR(255) NOT NULL DEFAULT '' COMMENT '用户身份(证)信息',
  `u_email` VARCHAR(255) NOT NULL DEFAULT '' COMMENT '用户邮箱',
  `u_phone` VARCHAR(127) NOT NULL DEFAULT '' COMMENT '用户手机号',
  `u_birthday` DATE NOT NULL DEFAULT '1000-01-01' COMMENT '用户出生日',
  `u_address` VARCHAR(1024) NOT NULL DEFAULT '' COMMENT '用户地址',
  `u_city_id` INT UNSIGNED NOT NULL DEFAULT 0 COMMENT '城市id',
  `u_valid_flag` INT UNSIGNED NOT NULL DEFAULT 0 COMMENT '比特位，1 邮箱验证过，2 手机验证过，4身份证验证过',
  `u_status` TINYINT NOT NULL DEFAULT 1 COMMENT '是否有效 0: 无效，1 有效，2 冻结，3 禁止转入，4 禁止转出',
  `u_creator` BIGINT UNSIGNED NOT NULL DEFAULT 0 COMMENT '创建者id',
  `u_create_at` DATETIME NOT NULL DEFAULT '1000-01-01' COMMENT '创建时间',
  `u_update` DATETIME NOT NULL DEFAULT NOW() COMMENT '修改时间',
  PRIMARY KEY (`u_id`),
  INDEX `idx_name` (`u_name` ASC),
  INDEX `idx_idcard` (`u_idcard` ASC),
  INDEX `idx_email` (`u_email` ASC),
  INDEX `idx_phone` (`u_phone` ASC),
  INDEX `idx_city` (`u_city_id` ASC)
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COMMENT = '用户表'
PARTITION BY HASH(`u_id`)
PARTITIONS 20
;

CREATE TABLE `t_user_ex` (
  `ux_id` BIGINT UNSIGNED NOT NULL COMMENT '主键id，用户id',
  `ux_amount` DECIMAL NOT NULL DEFAULT 0.0 COMMENT '用户余额',
  `ux_lastest_login` DATETIME NOT NULL DEFAULT NOW() COMMENT '最近一次登录时间',
  `ux_create_at` DATETIME NOT NULL DEFAULT '1000-01-01' COMMENT '创建时间',
  `ux_update` DATETIME NOT NULL DEFAULT NOW() COMMENT '修改时间',
  PRIMARY KEY (`ux_id`)
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COMMENT = '用户表扩展表'
PARTITION BY HASH(`ux_id`)
PARTITIONS 20
;


CREATE TABLE `t_bank_card` (
  `bc_id` BIGINT UNSIGNED NOT NULL COMMENT '主键id',
  `bc_card_id` BIGINT UNSIGNED NOT NULL DEFAULT 0 COMMENT '银行卡id',
  `bc_name` VARCHAR(127) NOT NULL DEFAULT '' COMMENT '银行卡名称',
  `bc_type` TINYINT NOT NULL DEFAULT 0 COMMENT '卡类型：0 普通卡，1 铜卡，2 银卡，3 金卡等',
  `bc_password1` VARCHAR(255) NOT NULL DEFAULT '' COMMENT '加密后密码,查询密码',
  `bc_password2` VARCHAR(255) NOT NULL DEFAULT '' COMMENT '加密后密码,交易密码',
  `bc_password_hint1` VARCHAR(255) NOT NULL DEFAULT '' COMMENT '密码提示1',
  `bc_password_hint2` VARCHAR(255) NOT NULL DEFAULT '' COMMENT '密码提示2',
  `bc_password_hint3` VARCHAR(255) NOT NULL DEFAULT '' COMMENT '密码提示3',
  `bc_amount` DECIMAL NOT NULL DEFAULT 0.0 COMMENT '账户余额',
  `bc_user_id` BIGINT UNSIGNED  NOT NULL DEFAULT 0 COMMENT '卡所属用户uid',
  `bc_status` TINYINT NOT NULL DEFAULT 1 COMMENT '是否有效 0: 无效，1 有效',
  `bc_creator` BIGINT UNSIGNED NOT NULL DEFAULT 0 COMMENT '创建者id',
  `bc_create_at` DATETIME NOT NULL DEFAULT '1000-01-01' COMMENT '创建时间',
  `bc_update` DATETIME NOT NULL DEFAULT NOW() COMMENT '修改时间',
  PRIMARY KEY (`bc_card_id`,`bc_id`),
  INDEX `idx_uid` (`bc_user_id` ASC)
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COMMENT = '账号表'
PARTITION BY HASH(`bc_card_id`)
PARTITIONS 100
;


CREATE TABLE `t_transaction_from_flow` (
  `tff_id` BIGINT UNSIGNED NOT NULL COMMENT '主键id',
  `tff_from_back_card_id` BIGINT UNSIGNED  NOT NULL DEFAULT 0 COMMENT '转出银行卡id',
  `tff_to_back_card_id` BIGINT UNSIGNED  NOT NULL DEFAULT 0 COMMENT '转入银行卡id',
  `tff_amount_before` DECIMAL NOT NULL DEFAULT 0.0 COMMENT '转账前金额',
  `tff_amount` DECIMAL NOT NULL DEFAULT 0.0 COMMENT '转账金额',
  `tff_amount_after` DECIMAL NOT NULL DEFAULT 0.0 COMMENT '转账后金额',
  `tff_create_at` DATETIME NOT NULL DEFAULT NOW() COMMENT '创建时间',
  PRIMARY KEY (`tff_from_back_card_id`,`tff_id`),
  INDEX `idx_create`(`tff_create_at` DESC)
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COMMENT = '转账流水表，转出用户是本行，插入一条数据'
PARTITION BY HASH(`tff_from_back_card_id`)
PARTITIONS 1000
;

CREATE TABLE `t_transaction_to_flow` (
  `ttf_id` BIGINT UNSIGNED NOT NULL COMMENT '主键id',
  `ttf_to_back_card_id` BIGINT UNSIGNED  NOT NULL DEFAULT 0 COMMENT '转入银行卡id',
  `ttf_from_back_card_id` BIGINT UNSIGNED  NOT NULL DEFAULT 0 COMMENT '转出银行卡id',
  `ttf_amount_before` DECIMAL NOT NULL DEFAULT 0.0 COMMENT '转账前金额',
  `ttf_amount` DECIMAL NOT NULL DEFAULT 0.0 COMMENT '转账金额',
  `ttf_amount_after` DECIMAL NOT NULL DEFAULT 0.0 COMMENT '转账后金额',
  `ttf_create_at` DATETIME NOT NULL DEFAULT NOW() COMMENT '创建时间',
  PRIMARY KEY (`ttf_to_back_card_id`,`ttf_id`),
  INDEX `idx_create`(`ttf_create_at` DESC)
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COMMENT = '转账流水表，转入用户是本行，插入一条数据'
PARTITION BY HASH(`ttf_to_back_card_id`)
PARTITIONS 1000
;


CREATE TABLE `t_transaction_flow_check` (
  `tfc_id` BIGINT UNSIGNED AUTO_INCREMENT NOT NULL COMMENT '主键id',
  `tfc_transaction_id` BIGINT UNSIGNED NOT NULL DEFAULT 0 COMMENT '关联交易主键id',
  `tfc_type` TINYINT UNSIGNED NOT NULL DEFAULT 0 COMMENT '0: 转入、1 转出',
  `tfc_status` TINYINT UNSIGNED NOT NULL DEFAULT 0 COMMENT '0: 拒绝，1 通过',
  `tfc_creator` BIGINT UNSIGNED NOT NULL DEFAULT 0 COMMENT '创建者id',
  `tfc_creator_source` INT UNSIGNED NOT NULL DEFAULT 0 COMMENT '创建者来源, 例如策略组id，可以分级审核',
  `tfc_operator` BIGINT UNSIGNED NOT NULL DEFAULT 0 COMMENT '操作者id',
  `tfc_create_at` DATETIME NOT NULL DEFAULT '1000-01-01' COMMENT '创建时间',
  `tfc_update_at` DATETIME NOT NULL DEFAULT NOW() COMMENT '更新时间',
  PRIMARY KEY (`tfc_id`),
  INDEX `idx_transaction`(`tfc_transaction_id` DESC),
  INDEX `idx_create_source`(`tfc_create_at` DESC, `tfc_creator_source` ASC)
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COMMENT = '交易审核表'
PARTITION BY HASH(`tfc_id`)
PARTITIONS 2000
;


CREATE TABLE `t_group` (
  `g_id` INT UNSIGNED AUTO_INCREMENT NOT NULL COMMENT '主键id',
  `g_name` VARCHAR(127) NOT NULL DEFAULT '' COMMENT '城市名称',
  `g_desc` VARCHAR(255) NOT NULL DEFAULT '' COMMENT '描述信息',
  `g_creator` BIGINT UNSIGNED NOT NULL DEFAULT 0 COMMENT '创建者id',
  `g_create_at` DATETIME NOT NULL DEFAULT NOW() COMMENT '创建时间',
  PRIMARY KEY (`g_id`),
  INDEX `idx_name`(`g_name` DESC)
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COMMENT = '策略组表'
;

CREATE TABLE `t_policy` (
  `p_id` INT UNSIGNED AUTO_INCREMENT NOT NULL COMMENT '主键id',
  `p_name` VARCHAR(127) NOT NULL DEFAULT '' COMMENT '城市名称',
  `p_desc` VARCHAR(255) NOT NULL DEFAULT '' COMMENT '描述信息',
  `p_dynamic_content` MEDIUMTEXT NOT NULL COMMENT '动态脚本内容，json字符串格式',
  `p_creator` BIGINT UNSIGNED NOT NULL DEFAULT 0 COMMENT '创建者id',
  `p_create_at` DATETIME NOT NULL DEFAULT NOW() COMMENT '创建时间',
  PRIMARY KEY (`p_id`),
  INDEX `idx_name`(`p_name` DESC)
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COMMENT = '策略表'
;

CREATE TABLE `t_group_policy` (
  `gp_id` INT UNSIGNED AUTO_INCREMENT NOT NULL COMMENT '主键id',
  `gp_group_id` INT UNSIGNED NOT NULL DEFAULT 0 COMMENT '策略所属的group id',
  `gp_policy_id` INT UNSIGNED NOT NULL DEFAULT 0 COMMENT '策略 id',
  `gp_order`  DOUBLE NOT NULL DEFAULT 0.0 COMMENT '同一组中，策略的先后顺序',
  `gp_creator` BIGINT UNSIGNED NOT NULL DEFAULT 0 COMMENT '创建者id',
  `gp_create_at` DATETIME NOT NULL DEFAULT NOW() COMMENT '创建时间',
  PRIMARY KEY (`gp_id`),
  INDEX `idx_group_order`(`gp_group_id` DESC, `gp_order` ASC),
  INDEX `idx_policy`(`gp_policy_id`),
  INDEX `idx_order`(`gp_order` ASC)
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COMMENT = '策略组和策略关系表'
;



CREATE TABLE `t_tag_country` (
  `tcr_country_id` INT UNSIGNED NOT NULL DEFAULT 0 COMMENT '国家id',
  `tcr_creator` BIGINT UNSIGNED NOT NULL DEFAULT 0 COMMENT '创建者id',
  `tcr_create_at` DATETIME NOT NULL DEFAULT NOW() COMMENT '创建时间',
  PRIMARY KEY (`tcr_country_id`)
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COMMENT = '特征表，国家'
;

CREATE TABLE `t_tag_city` (
  `tct_city_id` INT UNSIGNED NOT NULL DEFAULT 0 COMMENT '城市id',
  `tct_creator` BIGINT UNSIGNED NOT NULL DEFAULT 0 COMMENT '创建者id',
  `tct_create_at` DATETIME NOT NULL DEFAULT NOW() COMMENT '创建时间',
  PRIMARY KEY (`tct_city_id`)
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COMMENT = '特征表，城市'
;

CREATE TABLE `t_tag_phone` (
  `tp_phone_hash` BIGINT NOT NULL COMMENT '手机号hash',
  `tp_phone` VARCHAR(127) NOT NULL DEFAULT 0 COMMENT 'phone',
  `tp_creator` BIGINT UNSIGNED NOT NULL DEFAULT 0 COMMENT '创建者id',
  `tp_create_at` DATETIME NOT NULL DEFAULT NOW() COMMENT '创建时间',
  PRIMARY KEY (`tp_phone_hash`)
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COMMENT = '特征表，手机号'
PARTITION BY HASH(`tp_phone_hash`)
PARTITIONS 100
;

CREATE TABLE `t_tag_email` (
  `te_email_hash` BIGINT NOT NULL COMMENT '邮箱hash',
  `te_email` VARCHAR(255) NOT NULL DEFAULT '' COMMENT 'email',
  `te_creator` BIGINT UNSIGNED NOT NULL DEFAULT 0 COMMENT '创建者id',
  `te_create_at` DATETIME NOT NULL DEFAULT NOW() COMMENT '创建时间',
  PRIMARY KEY (`te_email_hash`)
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COMMENT = '特征表，邮箱号'
PARTITION BY HASH(`te_email_hash`)
PARTITIONS 100
;

CREATE TABLE `t_tag_uid` (
  `tu_uid` BIGINT UNSIGNED NOT NULL DEFAULT 0 COMMENT 'uid',
  `tu_creator` BIGINT UNSIGNED NOT NULL DEFAULT 0 COMMENT '创建者id',
  `tu_create_at` DATETIME NOT NULL DEFAULT NOW() COMMENT '创建时间',
  PRIMARY KEY (`tu_uid`)
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COMMENT = '特征表，用户'
PARTITION BY HASH(`tu_uid`)
PARTITIONS 100
;

CREATE TABLE `t_tag_cardid` (
  `tc_cardid` BIGINT UNSIGNED NOT NULL DEFAULT 0 COMMENT 'cardid',
  `tc_creator` BIGINT UNSIGNED NOT NULL DEFAULT 0 COMMENT '创建者id',
  `tc_create_at` DATETIME NOT NULL DEFAULT NOW() COMMENT '创建时间',
  PRIMARY KEY (`tc_cardid`)
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COMMENT = '特征表，用户'
PARTITION BY HASH(`tc_cardid`)
PARTITIONS 100
;

CREATE TABLE `t_tag_ip_address` (
  `tia_ip_address` BIGINT UNSIGNED NOT NULL DEFAULT 0 COMMENT 'ip地址',
  `tia_creator` BIGINT UNSIGNED NOT NULL DEFAULT 0 COMMENT '创建者id',
  `tia_create_at` DATETIME NOT NULL DEFAULT NOW() COMMENT '创建时间',
  PRIMARY KEY (`tia_ip_address`)
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COMMENT = '特征表，用户'
PARTITION BY HASH(`tia_ip_address`)
PARTITIONS 100
;