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
  `id`            BIGINT(11) NOT NULL AUTO_INCREMENT
  COMMENT '主键',
  `admainhead`  VARCHAR(256)        DEFAULT NULL
  COMMENT '广告主标题',
  `adsubhead`   VARCHAR(256)        DEFAULT NULL
  COMMENT '广告副标题',
  `adstarttime` DATETIME            DEFAULT NULL
  COMMENT '开始时间',
  `adendtime`   DATETIME            DEFAULT NULL
  COMMENT '结束时间',
  `adurl`        VARCHAR(1000)       DEFAULT NULL
  COMMENT '广告链接',
  `adstatus`     VARCHAR(1)          DEFAULT NULL
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
  `id`                BIGINT(11) NOT NULL AUTO_INCREMENT
  COMMENT '主键',
  `membername`       VARCHAR(100)        DEFAULT NULL
  COMMENT '队员名称',
  `membersex`        VARCHAR(1)          DEFAULT NULL
  COMMENT '队员性别0:男;1:女',
  `membermobile`     VARCHAR(256)        DEFAULT NULL
  COMMENT '手机号',
  `memberbirth`      DATETIME            DEFAULT NULL
  COMMENT '出生年月',
  `memberposition`   VARCHAR(100)        DEFAULT NULL
  COMMENT '主攻位置',
  `memberhabitfeet` VARCHAR(100)        DEFAULT NULL
  COMMENT '惯用脚',
  `memberheight`     DECIMAL(10, 2)      DEFAULT NULL
  COMMENT '身高',
  `memberweight`     DECIMAL(10, 2)      DEFAULT NULL
  COMMENT '体重',
  `membertype`       VARCHAR(1) NOT NULL
  COMMENT '队员类型:1:队长;2:普通球员',
  `memberstatus`     VARCHAR(1)          DEFAULT '0'
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
  `id`               BIGINT(11) NOT NULL AUTO_INCREMENT
  COMMENT '主键',
  `teamname`        VARCHAR(50)         DEFAULT NULL
  COMMENT '球队名称',
  `teamlevel`       VARCHAR(20)         DEFAULT NULL
  COMMENT '球队级别',
  `teammembernum`  INTEGER             DEFAULT NULL
  COMMENT '球员数量',
  `teamwinnum`     INTEGER             DEFAULT NULL
  COMMENT '球队胜利场数',
  `teamdebtnum`    INTEGER             DEFAULT NULL
  COMMENT '球队失败场数',
  `teamdrawnum`    INTEGER             DEFAULT NULL
  COMMENT '球队平局场数',
  `teampoint`       INTEGER             DEFAULT NULL
  COMMENT '球队积分',
  `teamprov`    VARCHAR(20)         DEFAULT NULL
  COMMENT '球队省份',
  `teamcity`        VARCHAR(20)         DEFAULT NULL
  COMMENT '球队市',
  `teamarea`        VARCHAR(20)         DEFAULT NULL
  COMMENT '球队区',
  `teamdesc` VARCHAR(256)        DEFAULT NULL
  COMMENT '球队描述',
  `teamownerid`    BIGINT(11) NOT NULL
  COMMENT '所属人',
  `teamstatus`      VARCHAR(10)         DEFAULT '0'
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
  `id`        BIGINT(11) NOT NULL AUTO_INCREMENT
  COMMENT '主键',
  `teamid`   BIGINT(11) NOT NULL
  COMMENT '球队id',
  `memberid` BIGINT(11) NOT NULL
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
  `id`                      BIGINT(11)   NOT NULL AUTO_INCREMENT
  COMMENT '主键',
  `matchname`              VARCHAR(50)           DEFAULT NULL
  COMMENT '比赛名称',
  `matchtime`              INTEGER               DEFAULT NULL
  COMMENT '比赛时间',
  `matchplace`             VARCHAR(256)          DEFAULT NULL
  COMMENT '比赛地点',
  `matchinitiatorid`      VARCHAR(256) NOT NULL
  COMMENT '比赛发起人',
  `matchhostteamid`      BIGINT(11)   NOT NULL
  COMMENT '东道主team',
  `matchchallengeteamid` BIGINT(11)            DEFAULT NULL
  COMMENT '挑战team',
  `matchprov`          VARCHAR(20)           DEFAULT NULL
  COMMENT '比赛省份',
  `matchcity`              VARCHAR(20)           DEFAULT NULL
  COMMENT '比赛市',
  `matcharea`              VARCHAR(20)           DEFAULT NULL
  COMMENT '比赛区',
  `matchstarttime`        DATETIME              DEFAULT NULL
  COMMENT '球赛开始时间',
  `matchendtime`          DATETIME              DEFAULT NULL
  COMMENT '球赛结束时间',
  `matchparkid`           BIGINT(11)   NOT NULL
  COMMENT '球场id',
  `matchstatus`            VARCHAR(10)           DEFAULT NULL
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
  `id`                 BIGINT(11) NOT NULL AUTO_INCREMENT
  COMMENT '主键',
  `parkname`          VARCHAR(50)         DEFAULT NULL
  COMMENT '球场名称',
  `parkprov`      VARCHAR(20)         DEFAULT NULL
  COMMENT '球场省份',
  `parkcity`          VARCHAR(20)         DEFAULT NULL
  COMMENT '球场市',
  `parkarea`          VARCHAR(20)         DEFAULT NULL
  COMMENT '球场区',
  `parkdesc`          VARCHAR(200)        DEFAULT NULL
  COMMENT '球场简介',
  `parkbusinesstime` VARCHAR(200)        DEFAULT NULL
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
  `id`                BIGINT(11)  NOT NULL AUTO_INCREMENT
  COMMENT '主键',
  `attachname`   VARCHAR(50)          DEFAULT NULL
  COMMENT '附件名称',
  `attachosskey` VARCHAR(100)         DEFAULT NULL
  COMMENT '附件osskey',
  `attachurl`    VARCHAR(500)         DEFAULT NULL
  COMMENT '附件链接',
  `attachtype`   VARCHAR(3)  NOT NULL
  COMMENT '附件类型1:队员附件;2:球队附件;3:球场附件;4:广告附件',
  `attachkey`    VARCHAR(40) NOT NULL
  COMMENT '关联主键:队员id|球队id|球场id|广告id',
  `attachsuffix` VARCHAR(500)         DEFAULT NULL
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
