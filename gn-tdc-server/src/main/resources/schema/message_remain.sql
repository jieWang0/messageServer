-- ----------------------------
-- Table structure for `message_remain`
-- ----------------------------
CREATE TABLE IF NOT EXISTS `message_remain` (
  `id` varchar(255) NOT NULL  COMMENT '主键id',
  `topic` varchar(255) DEFAULT NULL COMMENT '主题',
  `message` varchar(1024) DEFAULT NULL COMMENT '消息内容',
  PRIMARY KEY (`id`)
);