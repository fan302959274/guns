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

SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for test
-- ----------------------------
DROP TABLE IF EXISTS `test`;
CREATE TABLE `test` (
  `id`    INT(11) NOT NULL AUTO_INCREMENT,
  `value` VARCHAR(255)     DEFAULT NULL,
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 23
  DEFAULT CHARSET = utf8;

-- ----------------------------
-- Records of test
-- ----------------------------
INSERT INTO `test` VALUES ('1', 'qwe');

-- 1、广告表
DROP TABLE IF EXISTS `pk_ad`;
CREATE TABLE `pk_ad` (
  `id`            BIGINT(20) NOT NULL  AUTO_INCREMENT
  COMMENT '主键',
  `mainhead`  VARCHAR(256)        DEFAULT NULL
  COMMENT '广告主标题',
  `subhead`   VARCHAR(256)        DEFAULT NULL
  COMMENT '广告副标题',
  `starttime` DATETIME            DEFAULT NULL
  COMMENT '开始时间',
  `endtime`   DATETIME            DEFAULT NULL
  COMMENT '结束时间',
  `url`        VARCHAR(1000)       DEFAULT NULL
  COMMENT '广告链接',
  `status`     VARCHAR(1)          DEFAULT NULL
  COMMENT '广告状态:0:预上线;1:已上线',
  `createdate`   TIMESTAMP  NOT NULL DEFAULT CURRENT_TIMESTAMP
  COMMENT '创建时间',
  `updatedate`   TIMESTAMP  NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
  COMMENT '更新时间',
  `creator`       VARCHAR(100)        DEFAULT NULL
  COMMENT '创建人',
  `updator`       VARCHAR(100)        DEFAULT NULL
  COMMENT '更新人',
  `isdeleted`    VARCHAR(2)          DEFAULT '0'
  COMMENT '是否删除',
  PRIMARY KEY (`id`)
)
  ENGINE = INNODB
  AUTO_INCREMENT = 15
  DEFAULT CHARSET = utf8
  COMMENT = '广告表';

-- 2、队员表
DROP TABLE IF EXISTS `pk_member`;
CREATE TABLE `pk_member` (
  `id`                BIGINT(20) NOT NULL AUTO_INCREMENT
  COMMENT '主键',
  `name`       VARCHAR(100)        DEFAULT NULL
  COMMENT '队员名称',
  `sex`        VARCHAR(1)          DEFAULT NULL
  COMMENT '队员性别0:男;1:女',
  `mobile`     VARCHAR(256)        DEFAULT NULL
  COMMENT '手机号',
  `birth`      DATETIME            DEFAULT NULL
  COMMENT '出生年月',
  `position`   VARCHAR(100)        DEFAULT NULL
  COMMENT '主攻位置',
  `habitfeet` VARCHAR(100)        DEFAULT NULL
  COMMENT '惯用脚',
  `height`     DECIMAL(10, 2)      DEFAULT NULL
  COMMENT '身高',
  `weight`     DECIMAL(10, 2)      DEFAULT NULL
  COMMENT '体重',
  `type`       VARCHAR(10) NOT NULL
  COMMENT '队员类型:1:队长;2:普通球员',
  `status`     VARCHAR(1)          DEFAULT '0'
  COMMENT '队员状态:0:禁用1:启用',
  `createdate`       TIMESTAMP  NOT NULL DEFAULT CURRENT_TIMESTAMP
  COMMENT '创建时间',
  `updatedate`       TIMESTAMP  NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
  COMMENT '更新时间',
  `creator`           VARCHAR(100)        DEFAULT NULL
  COMMENT '创建人',
  `updator`           VARCHAR(100)        DEFAULT NULL
  COMMENT '更新人',
  `isdeleted`        VARCHAR(2)          DEFAULT '0'
  COMMENT '是否删除',
  PRIMARY KEY (`id`)
)
  ENGINE = INNODB
  AUTO_INCREMENT = 15
  DEFAULT CHARSET = utf8
  COMMENT = '队员表';

-- 3、球队表
DROP TABLE IF EXISTS `pk_team`;
CREATE TABLE `pk_team` (
  `id`               BIGINT(20) NOT NULL AUTO_INCREMENT
  COMMENT '主键',
  `name`        VARCHAR(50)         DEFAULT NULL
  COMMENT '球队名称',
  `level`       VARCHAR(20)         DEFAULT NULL
  COMMENT '球队级别',
  `membernum`  INTEGER             DEFAULT NULL
  COMMENT '球员数量',
  `winnum`     INTEGER             DEFAULT NULL
  COMMENT '球队胜利场数',
  `debtnum`    INTEGER             DEFAULT NULL
  COMMENT '球队失败场数',
  `drawnum`    INTEGER             DEFAULT NULL
  COMMENT '球队平局场数',
  `point`       INTEGER             DEFAULT NULL
  COMMENT '球队积分',
  `prov`    VARCHAR(20)         DEFAULT NULL
  COMMENT '球队省份',
  `city`        VARCHAR(20)         DEFAULT NULL
  COMMENT '球队市',
  `area`        VARCHAR(20)         DEFAULT NULL
  COMMENT '球队区',
  `desc` VARCHAR(256)        DEFAULT NULL
  COMMENT '球队描述',
  `ownerid`    BIGINT(20) NOT NULL
  COMMENT '所属人',
  `status`      VARCHAR(10)         DEFAULT '0'
  COMMENT '球队状态:0:禁用1:启用',
  `createdate`      TIMESTAMP  NOT NULL DEFAULT CURRENT_TIMESTAMP
  COMMENT '创建时间',
  `updatedate`      TIMESTAMP  NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
  COMMENT '更新时间',
  `creator`          VARCHAR(100)        DEFAULT NULL
  COMMENT '创建人',
  `updator`          VARCHAR(100)        DEFAULT NULL
  COMMENT '更新人',
  `isdeleted`       VARCHAR(2)          DEFAULT '0'
  COMMENT '是否删除',
  PRIMARY KEY (`id`)
)
  ENGINE = INNODB
  AUTO_INCREMENT = 15
  DEFAULT CHARSET = utf8
  COMMENT = '球队表';

-- 4、球队队员表
DROP TABLE IF EXISTS `pk_team_member`;
CREATE TABLE `pk_team_member` (
  `id`        BIGINT(20) NOT NULL AUTO_INCREMENT
  COMMENT '主键',
  `teamid`   BIGINT(20) NOT NULL
  COMMENT '球队id',
  `memberid` BIGINT(20) NOT NULL
  COMMENT '队员id',
  PRIMARY KEY (`id`)
)
  ENGINE = INNODB
  AUTO_INCREMENT = 15
  DEFAULT CHARSET = utf8
  COMMENT = '球队队员表';

-- 5、比赛表
DROP TABLE IF EXISTS `pk_match`;
CREATE TABLE `pk_match` (
  `id`                      BIGINT(20)   NOT NULL AUTO_INCREMENT
  COMMENT '主键',
  `name`              VARCHAR(50)           DEFAULT NULL
  COMMENT '比赛名称',
  `time`              INTEGER               DEFAULT NULL
  COMMENT '比赛时间',
  `place`             VARCHAR(256)          DEFAULT NULL
  COMMENT '比赛地点',
  `initiatorid`      VARCHAR(256) NOT NULL
  COMMENT '比赛发起人',
  `hostteamid`      BIGINT(20)   NOT NULL
  COMMENT '东道主team',
  `challengeteamid` BIGINT(20)            DEFAULT NULL
  COMMENT '挑战team',
  `prov`          VARCHAR(20)           DEFAULT NULL
  COMMENT '比赛省份',
  `city`              VARCHAR(20)           DEFAULT NULL
  COMMENT '比赛市',
  `area`              VARCHAR(20)           DEFAULT NULL
  COMMENT '比赛区',
  `starttime`        DATETIME              DEFAULT NULL
  COMMENT '球赛开始时间',
  `endtime`          DATETIME              DEFAULT NULL
  COMMENT '球赛结束时间',
  `parkid`           BIGINT(20)   NOT NULL
  COMMENT '球场id',
  `status`            VARCHAR(10)           DEFAULT NULL
  COMMENT '比赛状态(待定)',
  `createdate`             TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP
  COMMENT '创建时间',
  `updatedate`             TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
  COMMENT '更新时间',
  `creator`                 VARCHAR(100)          DEFAULT NULL
  COMMENT '创建人',
  `updator`                 VARCHAR(100)          DEFAULT NULL
  COMMENT '更新人',
  `isdeleted`              VARCHAR(2)            DEFAULT '0'
  COMMENT '是否删除',
  PRIMARY KEY (`id`)
)
  ENGINE = INNODB
  AUTO_INCREMENT = 15
  DEFAULT CHARSET = utf8
  COMMENT = '比赛表';

-- 6、球场表
DROP TABLE IF EXISTS `pk_park`;
CREATE TABLE `pk_park` (
  `id`                 BIGINT(20) NOT NULL AUTO_INCREMENT
  COMMENT '主键',
  `name`          VARCHAR(50)         DEFAULT NULL
  COMMENT '球场名称',
  `prov`      VARCHAR(20)         DEFAULT NULL
  COMMENT '球场省份',
  `city`          VARCHAR(20)         DEFAULT NULL
  COMMENT '球场市',
  `area`          VARCHAR(20)         DEFAULT NULL
  COMMENT '球场区',
  `desc`          VARCHAR(200)        DEFAULT NULL
  COMMENT '球场简介',
  `businesstime` VARCHAR(200)        DEFAULT NULL
  COMMENT '球场可用时间',
  `createdate`        TIMESTAMP  NOT NULL DEFAULT CURRENT_TIMESTAMP
  COMMENT '创建时间',
  `updatedate`        TIMESTAMP  NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
  COMMENT '更新时间',
  `creator`            VARCHAR(100)        DEFAULT NULL
  COMMENT '创建人',
  `updator`            VARCHAR(100)        DEFAULT NULL
  COMMENT '更新人',
  `isdeleted`         VARCHAR(2)          DEFAULT '0'
  COMMENT '是否删除',
  PRIMARY KEY (`id`)
)
  ENGINE = INNODB
  AUTO_INCREMENT = 15
  DEFAULT CHARSET = utf8
  COMMENT = '球场表';

-- 7、附件表
DROP TABLE IF EXISTS `pk_attachment`;
CREATE TABLE `pk_attachment` (
  `id`                BIGINT(20)  NOT NULL AUTO_INCREMENT
  COMMENT '主键',
  `name`   VARCHAR(50)          DEFAULT NULL
  COMMENT '附件名称',
  `osskey` VARCHAR(100)         DEFAULT NULL
  COMMENT '附件osskey',
  `url`    VARCHAR(500)         DEFAULT NULL
  COMMENT '附件链接',
  `type`   VARCHAR(10)  NOT NULL
  COMMENT '附件类型1:队员附件;2:球队附件;3:球场附件;4:广告附件',
  `linkid`    BIGINT(20) NOT NULL
  COMMENT '关联主键:队员id|球队id|球场id|广告id',
  `suffix` VARCHAR(500)         DEFAULT NULL
  COMMENT '附件后缀',
  `createdate`       TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP
  COMMENT '创建时间',
  `updatedate`       TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
  COMMENT '更新时间',
  `creator`           VARCHAR(100)         DEFAULT NULL
  COMMENT '创建人',
  `updator`           VARCHAR(100)         DEFAULT NULL
  COMMENT '更新人',
  `isdeleted`        VARCHAR(2)           DEFAULT '0'
  COMMENT '是否删除',
  PRIMARY KEY (`id`)
)
  ENGINE = INNODB
  AUTO_INCREMENT = 15
  DEFAULT CHARSET = utf8
  COMMENT = '队员附件表';
