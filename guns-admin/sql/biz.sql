/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50621
Source Host           : localhost:3306
Source Database       : biz

Target Server Type    : MYSQL
Target Server Version : 50621
File Encoding         : 65001

Date: 2017-09-04 16:17:33
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for test
-- ----------------------------
DROP TABLE IF EXISTS `test`;
CREATE TABLE `test` (
	`id` int(11) NOT NULL AUTO_INCREMENT,
	`value` varchar(255) DEFAULT NULL,
	PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of test
-- ----------------------------
INSERT INTO `test` VALUES ('1', 'qwe');




-- 1、队员表
DROP TABLE IF EXISTS `pk_member`;
CREATE TABLE `pk_member` (
	`id` BIGINT (11) NOT NULL AUTO_INCREMENT COMMENT '主键',
	`member_name` VARCHAR (100) DEFAULT NULL COMMENT '队员名称',
	`member_sex` VARCHAR (1) DEFAULT NULL COMMENT '队员性别0:男;1:女',
	`member_mobile` VARCHAR (256) DEFAULT NULL COMMENT '手机号',
	`member_birth` TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '出生年月',
	`member_position` VARCHAR (100) DEFAULT NULL COMMENT '主攻位置',
	`member_habit_feet` VARCHAR (100) DEFAULT NULL COMMENT '惯用脚',
	`member_height` DECIMAL (10, 2) DEFAULT NULL COMMENT '身高',
	`member_weight` DECIMAL (10, 2) DEFAULT NULL COMMENT '体重',
	`member_type` VARCHAR (1) NOT NULL COMMENT '队员类型:1:队长;2:普通球员',
	`member_status` VARCHAR (10) DEFAULT NULL COMMENT '队员状态:0:禁用1:启用',
	`create_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
	`update_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
	`creator` VARCHAR (100) DEFAULT NULL COMMENT '创建人',
	`updator` VARCHAR (100) DEFAULT NULL COMMENT '更新人',
	`is_deleted` VARCHAR (2) DEFAULT '0' COMMENT '是否删除',
	PRIMARY KEY (`id`)
) ENGINE = INNODB AUTO_INCREMENT = 15 DEFAULT CHARSET = utf8 COMMENT = '队员表';

-- 2、附件表
DROP TABLE IF EXISTS `pk_attachment`;
CREATE TABLE `pk_attachment` (
	`id` BIGINT (11) NOT NULL AUTO_INCREMENT COMMENT '主键',
	`attachment_name` VARCHAR (50) NOT NULL COMMENT '附件名称',
	`attachment_osskey` VARCHAR (100) DEFAULT NULL COMMENT '附件osskey',
	`attachment_type` VARCHAR (40) NOT NULL COMMENT '附件类型1:队员附件;2:球队附件;3:球场附件',
	`attachment_key` VARCHAR (40) NOT NULL COMMENT '关联主键:队员id|球队id|球场id',
	`attachment_suffix` VARCHAR (500) NOT NULL COMMENT '附件后缀',
	`create_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
	`update_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
	`creator` VARCHAR (100) DEFAULT NULL COMMENT '创建人',
	`updator` VARCHAR (100) DEFAULT NULL COMMENT '更新人',
	`is_deleted` VARCHAR (2) DEFAULT '0' COMMENT '是否删除',
	PRIMARY KEY (`id`)
) ENGINE = INNODB AUTO_INCREMENT = 15 DEFAULT CHARSET = utf8 COMMENT = '队员附件表';

-- 3、球队表
DROP TABLE IF EXISTS `pk_team`;
CREATE TABLE `pk_team` (
	`id` BIGINT (11) NOT NULL AUTO_INCREMENT COMMENT '主键',
	`team_name` VARCHAR (50) NOT NULL COMMENT '球队名称',
	`team_level` VARCHAR (20) NOT NULL COMMENT '球队级别',
	`team_member_num` INTEGER DEFAULT NULL COMMENT '球员数量',
	`team_win_num` INTEGER DEFAULT NULL COMMENT '球队胜利场数',
	`team_debt_num` INTEGER DEFAULT NULL COMMENT '球队失败场数',
	`team_draw_num` INTEGER DEFAULT NULL COMMENT '球队平局场数',
	`team_point` INTEGER DEFAULT NULL COMMENT '球队积分',
	`team_province` VARCHAR (20) NOT NULL COMMENT '球队省份',
	`team_city` VARCHAR (20) NOT NULL COMMENT '球队市',
	`team_area` VARCHAR (20) NOT NULL COMMENT '球队区',
	`team_member_desc` VARCHAR (256) NOT NULL COMMENT '球队描述',
	`team_owner_id` BIGINT (11) NOT NULL COMMENT '所属人',
	`team_status` VARCHAR (10) DEFAULT NULL COMMENT '球队状态:0:禁用1:启用',
	`create_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
	`update_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
	`creator` VARCHAR (100) DEFAULT NULL COMMENT '创建人',
	`updator` VARCHAR (100) DEFAULT NULL COMMENT '更新人',
	`is_deleted` VARCHAR (2) DEFAULT '0' COMMENT '是否删除',
	PRIMARY KEY (`id`)
) ENGINE = INNODB AUTO_INCREMENT = 15 DEFAULT CHARSET = utf8 COMMENT = '球队表';

-- 4、球队队员表
DROP TABLE IF EXISTS `pk_team_member`;
CREATE TABLE `pk_team_member` (
	`id` BIGINT (11) NOT NULL AUTO_INCREMENT COMMENT '主键',
	`team_id` BIGINT (11) NOT NULL COMMENT '球队id',
	`member_id` BIGINT (11) NOT NULL COMMENT '队员id',
	PRIMARY KEY (`id`)
) ENGINE = INNODB AUTO_INCREMENT = 15 DEFAULT CHARSET = utf8 COMMENT = '球队队员表';

-- 5、比赛表
DROP TABLE IF EXISTS `pk_match`;
CREATE TABLE `pk_match` (
	`id` BIGINT (11) NOT NULL AUTO_INCREMENT COMMENT '主键',
	`match_name` VARCHAR (50) NOT NULL COMMENT '比赛名称',
	`match_time` INTEGER DEFAULT NULL COMMENT '比赛时间',
	`match_place` VARCHAR (256) NOT NULL COMMENT '比赛地点',
	`match_initiator_id` VARCHAR (256) NOT NULL COMMENT '比赛发起人',
	`match_host_team_id` BIGINT (11) NOT NULL COMMENT '东道主team',
	`match_challenge_team_id` BIGINT (11) NOT NULL COMMENT '挑战team',
	`match_province` VARCHAR (20) NOT NULL COMMENT '比赛省份',
	`match_city` VARCHAR (20) NOT NULL COMMENT '比赛市',
	`match_area` VARCHAR (20) NOT NULL COMMENT '比赛区',
	`match_start_time` datetime  DEFAULT NULL COMMENT '球赛开始时间',
	`match_end_time` datetime  DEFAULT NULL COMMENT '球赛结束时间',
	`match_park_id` BIGINT (11) NOT NULL COMMENT '球场id',
	`match_status` VARCHAR (10) DEFAULT NULL COMMENT '比赛状态(待定)',
	`create_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
	`update_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
	`creator` VARCHAR (100) DEFAULT NULL COMMENT '创建人',
	`updator` VARCHAR (100) DEFAULT NULL COMMENT '更新人',
	`is_deleted` VARCHAR (2) DEFAULT '0' COMMENT '是否删除',
	PRIMARY KEY (`id`)
) ENGINE = INNODB AUTO_INCREMENT = 15 DEFAULT CHARSET = utf8 COMMENT = '比赛表';


-- 6、球场表
DROP TABLE IF EXISTS `pk_park`;
CREATE TABLE `pk_park` (
	`id` BIGINT (11) NOT NULL AUTO_INCREMENT COMMENT '主键',
	`park_name` VARCHAR (50) NOT NULL COMMENT '球场名称',
	`park_province` VARCHAR (20) NOT NULL COMMENT '球场省份',
	`park_city` VARCHAR (20) NOT NULL COMMENT '球场市',
	`park_area` VARCHAR (20) NOT NULL COMMENT '球场区',
	`park_desc` VARCHAR (200) NOT NULL COMMENT '球场简介',
	`park_business_time` VARCHAR (200) DEFAULT NULL COMMENT '球场可用时间',
	`create_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
	`update_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
	`creator` VARCHAR (100) DEFAULT NULL COMMENT '创建人',
	`updator` VARCHAR (100) DEFAULT NULL COMMENT '更新人',
	`is_deleted` VARCHAR (2) DEFAULT '0' COMMENT '是否删除',
	PRIMARY KEY (`id`)
) ENGINE = INNODB AUTO_INCREMENT = 15 DEFAULT CHARSET = utf8 COMMENT = '球场表';
