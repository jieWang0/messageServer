--
-- Database: tdc_gn
-- ------------------------------------------------------
-- Server version	5.1.66

--
-- Table structure for table `consumer_offset`
--
DROP TABLE IF EXISTS `consumer_offset`;
CREATE TABLE `consumer_offset` (
  `topic`          VARCHAR(45) NOT NULL
  COMMENT '消息主题',
  `subscriber`     VARCHAR(45) NOT NULL
  COMMENT '消费者',
  `current_offset` BIGINT(20)  NOT NULL
  COMMENT '当前消费的位置',
  `commit_time`    BIGINT(20)  NOT NULL
  COMMENT '消费时间',
  UNIQUE KEY `index1` (`topic`, `subscriber`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COMMENT ='消费记录表';

--
-- Table structure for table `distributed_lock`
--
DROP TABLE IF EXISTS `distributed_lock`;
CREATE TABLE `distributed_lock` (
  `id`          INT(11)    NOT NULL AUTO_INCREMENT,
  `name`        VARCHAR(255)        DEFAULT NULL,
  `locked`      TINYINT(1) NOT NULL DEFAULT '0',
  `token`       VARCHAR(255)        DEFAULT NULL,
  `start_time`  DATETIME            DEFAULT NULL,
  `end_time`    DATETIME            DEFAULT NULL,
  `expire_time` DATETIME            DEFAULT NULL,
  `attributes`  TEXT,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 3
  DEFAULT CHARSET = utf8;

--
-- Table structure for table `record`
--
DROP TABLE IF EXISTS `record`;
CREATE TABLE `record` (
  `id`          BIGINT(20)  NOT NULL AUTO_INCREMENT,
  `offset`      BIGINT(20)  NOT NULL
  COMMENT '记录的唯一标识',
  `topic`       VARCHAR(45) NOT NULL
  COMMENT '记录所在的主题',
  `payload`     TEXT COMMENT '记录的消息体',
  `create_time` BIGINT(20)  NOT NULL
  COMMENT '记录创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `offset_UNIQUE` (`offset`)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 2
  DEFAULT CHARSET = utf8
  COMMENT ='消息系统的消息记录';

--
-- Table structure for table `subscription`
--
DROP TABLE IF EXISTS `subscription`;
CREATE TABLE `subscription` (
  `subscriber`     VARCHAR(45) NOT NULL
  COMMENT '订阅者',
  `topic`          VARCHAR(45) NOT NULL
  COMMENT '订阅的主题',
  `subscribe_time` BIGINT(20)  NOT NULL
  COMMENT '订阅时间',
  UNIQUE KEY `unique_idx` (`subscriber`, `topic`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COMMENT ='主题订阅';

--
-- Table structure for table `topic`
--
DROP TABLE IF EXISTS `topic`;
CREATE TABLE `topic` (
  `id`          BIGINT(20)  NOT NULL AUTO_INCREMENT,
  `name`        VARCHAR(45) NOT NULL
  COMMENT '消息主题名称',
  `creator`     VARCHAR(45)          DEFAULT NULL
  COMMENT '消息主题的创建者',
  `create_time` BIGINT(20)  NOT NULL
  COMMENT '消息主题创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `name_UNIQUE` (`name`)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 4
  DEFAULT CHARSET = utf8
  COMMENT ='消息主题';