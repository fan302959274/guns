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
CREATE TABLE `wb_member` (
	`id` BIGINT (11) NOT NULL AUTO_INCREMENT COMMENT '主键',
	`member_name` VARCHAR (100) DEFAULT NULL COMMENT '队员名称',
	`member_sex` VARCHAR (1) DEFAULT NULL COMMENT '队员性别0:男;1:女',
	`member_mobile` VARCHAR (256) DEFAULT NULL COMMENT '手机号',
	`member_birth` TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '出生年月',
	`member_position` VARCHAR (100) DEFAULT NULL COMMENT '主攻位置',
	`member_habit_feet` VARCHAR (100) DEFAULT NULL COMMENT '惯用脚',
	`member_height` DECIMAL (10, 2) DEFAULT NULL COMMENT '身高',
	`member_weight` DECIMAL (10, 2) DEFAULT NULL COMMENT '体重',
	`member_status` VARCHAR (10) DEFAULT NULL COMMENT '队员状态:0:禁用1:启用',
	`create_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
	`update_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
	`creator` VARCHAR (100) DEFAULT NULL COMMENT '创建人',
	`updator` VARCHAR (100) DEFAULT NULL COMMENT '更新人',
	`is_deleted` VARCHAR (2) DEFAULT '0' COMMENT '是否删除',
	PRIMARY KEY (`id`)
) ENGINE = INNODB AUTO_INCREMENT = 15 DEFAULT CHARSET = utf8 COMMENT = '队员表';

-- 2、队员附件表
CREATE TABLE `wb_member_attachment` (
	`id` BIGINT (11) NOT NULL AUTO_INCREMENT COMMENT '主键',
	`attachment_name` VARCHAR (50) NOT NULL COMMENT '附件名称',
	`attachment_osskey` VARCHAR (100) DEFAULT NULL COMMENT '附件osskey',
	`attachment_type` VARCHAR (40) NOT NULL COMMENT '附件类型',
	`attachment_suffix` VARCHAR (500) NOT NULL COMMENT '附件后缀',
	`create_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
	`update_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
	`creator` VARCHAR (100) DEFAULT NULL COMMENT '创建人',
	`updator` VARCHAR (100) DEFAULT NULL COMMENT '更新人',
	`is_deleted` VARCHAR (2) DEFAULT '0' COMMENT '是否删除',
	PRIMARY KEY (`id`)
) ENGINE = INNODB AUTO_INCREMENT = 15 DEFAULT CHARSET = utf8 COMMENT = '队员附件表';

-- 3、球队表
CREATE TABLE `wb_team` (
	`id` BIGINT (11) NOT NULL AUTO_INCREMENT COMMENT '主键',
	`team_name` VARCHAR (50) NOT NULL COMMENT '球队名称',
	`team_member_num` INTEGER DEFAULT NULL COMMENT '球员数量',
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
CREATE TABLE `wb_team_member` (
	`id` BIGINT (11) NOT NULL AUTO_INCREMENT COMMENT '主键',
	`team_id` BIGINT (11) NOT NULL COMMENT '球队id',
	`member_id` BIGINT (11) NOT NULL COMMENT '队员id',
	`member_duty` VARCHAR (1) NOT NULL COMMENT '队员职责:1:队长;2:普通球员',
	PRIMARY KEY (`id`)
) ENGINE = INNODB AUTO_INCREMENT = 15 DEFAULT CHARSET = utf8 COMMENT = '球队队员表';

-- 5、比赛表
CREATE TABLE `wb_match` (
	`id` BIGINT (11) NOT NULL AUTO_INCREMENT COMMENT '主键',
	`match_name` VARCHAR (50) NOT NULL COMMENT '比赛名称',
	`match_time` INTEGER DEFAULT NULL COMMENT '比赛时间',
	`match_place` VARCHAR (256) NOT NULL COMMENT '比赛地点',
	`match_initiator_id` VARCHAR (256) NOT NULL COMMENT '比赛发起人',
	`match_host_team_id` BIGINT (11) NOT NULL COMMENT '东道主team',
	`match_challenge_team_id` BIGINT (11) NOT NULL COMMENT '挑战team',
	`match_status` VARCHAR (10) DEFAULT NULL COMMENT '比赛状态(待定)',
	`create_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
	`update_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
	`creator` VARCHAR (100) DEFAULT NULL COMMENT '创建人',
	`updator` VARCHAR (100) DEFAULT NULL COMMENT '更新人',
	`is_deleted` VARCHAR (2) DEFAULT '0' COMMENT '是否删除',
	PRIMARY KEY (`id`)
) ENGINE = INNODB AUTO_INCREMENT = 15 DEFAULT CHARSET = utf8 COMMENT = '球队表';
