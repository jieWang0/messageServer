-- ----------------------------
-- Table structure for `message_remain`
-- ----------------------------
CREATE TABLE IF NOT EXISTS `message_remain` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `topic` varchar(32) DEFAULT NULL COMMENT '主题',
  `message` varchar(32) DEFAULT NULL COMMENT '消息内容',
  PRIMARY KEY (`id`)
);