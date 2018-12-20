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

-- 1、客户表
DROP TABLE IF EXISTS `cf_customer`;
CREATE TABLE `cf_customer` (
  `id`         BIGINT(20) NOT NULL AUTO_INCREMENT
  COMMENT '主键',
  `name`       VARCHAR(256)        DEFAULT NULL
  COMMENT '客户名称',
  `nickname`   VARCHAR(256)        DEFAULT NULL
  COMMENT '客户昵称',
  `country`    VARCHAR(50)         DEFAULT NULL
  COMMENT '客户国家',
  `province`   VARCHAR(50)         DEFAULT NULL
  COMMENT '客户省份',
  `city`       VARCHAR(50)         DEFAULT NULL
  COMMENT '客户城市',
  `mobile`     VARCHAR(20)         DEFAULT NULL
  COMMENT '客户手机号',
  `sex`        VARCHAR(2)          DEFAULT NULL
  COMMENT '客户性别',
  `head`       VARCHAR(256)        DEFAULT NULL
  COMMENT '头像',
  `openid`     VARCHAR(256)        DEFAULT NULL
  COMMENT 'openid',
  `status`     VARCHAR(1)          DEFAULT NULL
  COMMENT '客户状态:0:禁用;1:启用',
  `createdate` TIMESTAMP  NOT NULL DEFAULT CURRENT_TIMESTAMP
  COMMENT '创建时间',
  `updatedate` TIMESTAMP  NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
  COMMENT '更新时间',
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8
  COMMENT = '客户表';

-- 2、地址表
DROP TABLE IF EXISTS `cf_address`;
CREATE TABLE `cf_address` (
  `id`         BIGINT(20) NOT NULL AUTO_INCREMENT
  COMMENT '主键',
  `province`   VARCHAR(50)         DEFAULT NULL
  COMMENT '省',
  `city`       VARCHAR(50)         DEFAULT NULL
  COMMENT '市',
  `area`       VARCHAR(50)         DEFAULT NULL
  COMMENT '区',
  `detail`     VARCHAR(256)        DEFAULT NULL
  COMMENT '具体地址',
  `isdefault`  VARCHAR(256)        DEFAULT NULL
  COMMENT '是否默认',
  `status`     VARCHAR(1)          DEFAULT NULL
  COMMENT '地址状态:0:禁用;1:启用',
  `createdate` TIMESTAMP  NOT NULL DEFAULT CURRENT_TIMESTAMP
  COMMENT '创建时间',
  `updatedate` TIMESTAMP  NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
  COMMENT '更新时间',
  `isdeleted`  VARCHAR(2)          DEFAULT '0'
  COMMENT '是否删除',
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8
  COMMENT = '地址表';

-- 3、商品表
DROP TABLE IF EXISTS `cf_good`;
CREATE TABLE `cf_good` (
  `id`         BIGINT(20) NOT NULL AUTO_INCREMENT
  COMMENT '主键',
  `name`       VARCHAR(50)         DEFAULT NULL
  COMMENT '商品名称',
  `title`      VARCHAR(50)         DEFAULT NULL
  COMMENT '商品标题',
  `price`      VARCHAR(50)         DEFAULT NULL
  COMMENT '商品价格',
  `desc`       VARCHAR(256)        DEFAULT NULL
  COMMENT '商品描述',
  `status`     VARCHAR(1)          DEFAULT NULL
  COMMENT '上架状态:0:未上架;1:已上架',
  `createdate` TIMESTAMP  NOT NULL DEFAULT CURRENT_TIMESTAMP
  COMMENT '创建时间',
  `updatedate` TIMESTAMP  NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
  COMMENT '更新时间',
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8
  COMMENT = '商品表';





