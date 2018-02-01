-- ----------------------------
-- Table structure for `message_remain`
-- ----------------------------
DROP TABLE IF EXISTS `message_remain`;
CREATE TABLE `message_remain` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `topic` varchar(32) DEFAULT NULL COMMENT '主题',
  `message` varchar(32) DEFAULT NULL COMMENT '消息内容',
  PRIMARY KEY (`id`)
);