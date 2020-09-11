1. 本项目用来测试 mysql 的行级锁
2. 建表语句
CREATE TABLE `tb_student` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(255) DEFAULT NULL COMMENT '没有索引',
  `age` int(11) DEFAULT NULL COMMENT '非唯一索引',
  `idcard` varchar(255) DEFAULT NULL COMMENT '唯一索引',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_s_i` (`idcard`),
  KEY `idx_s_a` (`age`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4;

INSERT INTO `tb_student` VALUES (1, 'miaomiao1', 1, '110');
INSERT INTO `tb_student` VALUES (2, 'miaomiao2', 1, '111');
INSERT INTO `tb_student` VALUES (3, 'miaomiao3', 20, '112');
INSERT INTO `tb_student` VALUES (4, 'miaomiao4', 100, '113');
INSERT INTO `tb_student` VALUES (5, 'miaomiao5', 79, '114');