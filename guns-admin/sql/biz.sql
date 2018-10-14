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
  `account`    VARCHAR(100)        DEFAULT NULL
  COMMENT '队员账号',
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
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(50) DEFAULT NULL COMMENT '球队名称',
  `level` varchar(20) DEFAULT NULL COMMENT '球队级别',
  `membernum` int(11) DEFAULT NULL COMMENT '球员数量',
  `winnum` int(11) DEFAULT NULL COMMENT '球队胜利场数',
  `debtnum` int(11) DEFAULT NULL COMMENT '球队失败场数',
  `drawnum` int(11) DEFAULT NULL COMMENT '球队平局场数',
  `point` int(11) DEFAULT NULL COMMENT '球队积分',
  `prov` varchar(20) DEFAULT NULL COMMENT '球队省份',
  `city` varchar(20) DEFAULT NULL COMMENT '球队市',
  `area` varchar(20) DEFAULT NULL COMMENT '球队区',
  `descteam` varchar(256) DEFAULT NULL COMMENT '球队描述',
  `ownerid` bigint(20) NOT NULL COMMENT '所属人',
  `status` varchar(10) DEFAULT '0' COMMENT '球队状态:0:禁用1:启用',
  `createdate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updatedate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `creator` varchar(100) DEFAULT NULL COMMENT '创建人',
  `updator` varchar(100) DEFAULT NULL COMMENT '更新人',
  `isdeleted` varchar(2) DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8 COMMENT='球队表';

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
  `hostpaystatus`      VARCHAR(10)   DEFAULT NULL
  COMMENT '发起方支付状态',
  `challengepaystatus`      VARCHAR(10)   DEFAULT NULL
  COMMENT '挑战者支付状态',
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
CREATE TABLE `pk_team` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(50) DEFAULT NULL COMMENT '球队名称',
  `level` varchar(20) DEFAULT NULL COMMENT '球队级别',
  `membernum` int(11) DEFAULT NULL COMMENT '球员数量',
  `winnum` int(11) DEFAULT NULL COMMENT '球队胜利场数',
  `debtnum` int(11) DEFAULT NULL COMMENT '球队失败场数',
  `drawnum` int(11) DEFAULT NULL COMMENT '球队平局场数',
  `point` int(11) DEFAULT NULL COMMENT '球队积分',
  `prov` varchar(20) DEFAULT NULL COMMENT '球队省份',
  `city` varchar(20) DEFAULT NULL COMMENT '球队市',
  `area` varchar(20) DEFAULT NULL COMMENT '球队区',
  `descteam` varchar(256) DEFAULT NULL COMMENT '球队描述',
  `ownerid` bigint(20) NOT NULL COMMENT '所属人',
  `status` varchar(10) DEFAULT '0' COMMENT '球队状态:0:禁用1:启用',
  `createdate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updatedate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `creator` varchar(100) DEFAULT NULL COMMENT '创建人',
  `updator` varchar(100) DEFAULT NULL COMMENT '更新人',
  `isdeleted` varchar(2) DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8 COMMENT='球队表';

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
  `category` VARCHAR(10)  NOT NULL
  COMMENT '附件种类1:队员附件;2:球队附件;3:球场附件;4:广告附件',
  `type`   VARCHAR(10)   DEFAULT NULL
  COMMENT '附件类型:针对不同种类区分',
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



CREATE TABLE `provinces` (
   `id` int(11) NOT NULL AUTO_INCREMENT,
   `provinceid` int(11) NOT NULL,
   `province` varchar(100) NOT NULL DEFAULT '',
   PRIMARY KEY (`id`)
 ) ENGINE=MyISAM AUTO_INCREMENT=392 DEFAULT CHARSET=utf8

CREATE TABLE `cities` (
   `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
   `cityid` char(6) NOT NULL COMMENT '城市编码',
   `city` varchar(40) NOT NULL COMMENT '城市名称',
   `provinceid` char(6) NOT NULL COMMENT '所属省份编码',
   PRIMARY KEY (`id`)
 ) ENGINE=InnoDB AUTO_INCREMENT=346 DEFAULT CHARSET=utf8 COMMENT='城市信息表'

-- 7、区域表
DROP TABLE IF EXISTS `areas`;
CREATE TABLE `areas`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `areaid` char(6) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '区县编码',
  `area` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '区县名称',
  `cityid` char(6) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '所属城市编码',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3145 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '区县信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of areas
-- ----------------------------
INSERT INTO `areas` VALUES (802, '320101', '市辖区', '320100');
INSERT INTO `areas` VALUES (803, '320102', '玄武区', '320100');
INSERT INTO `areas` VALUES (804, '320103', '白下区', '320100');
INSERT INTO `areas` VALUES (805, '320104', '秦淮区', '320100');
INSERT INTO `areas` VALUES (806, '320105', '建邺区', '320100');
INSERT INTO `areas` VALUES (807, '320106', '鼓楼区', '320100');
INSERT INTO `areas` VALUES (808, '320107', '下关区', '320100');
INSERT INTO `areas` VALUES (809, '320111', '浦口区', '320100');
INSERT INTO `areas` VALUES (810, '320113', '栖霞区', '320100');
INSERT INTO `areas` VALUES (811, '320114', '雨花台区', '320100');
INSERT INTO `areas` VALUES (812, '320115', '江宁区', '320100');
INSERT INTO `areas` VALUES (813, '320116', '六合区', '320100');
INSERT INTO `areas` VALUES (814, '320124', '溧水县', '320100');
INSERT INTO `areas` VALUES (815, '320125', '高淳县', '320100');

SET FOREIGN_KEY_CHECKS = 1;

