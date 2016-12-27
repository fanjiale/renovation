/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50015
Source Host           : localhost:3306
Source Database       : wechat_restaurant

Target Server Type    : MYSQL
Target Server Version : 50015
File Encoding         : 65001

Date: 2016-12-16 17:17:26
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for access_token
-- ----------------------------
DROP TABLE IF EXISTS `access_token`;
CREATE TABLE `access_token` (
  `id` int(11) NOT NULL auto_increment,
  `access_token` varchar(1000) default NULL,
  `last_date` datetime default NULL COMMENT '最近更新日期',
  `period` decimal(10,0) default NULL COMMENT '有效时间',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of access_token
-- ----------------------------
INSERT INTO `access_token` VALUES ('1', 'ItqPAj9_M_ZLNUUfMEtx2wSGlNkX4YtoQyQWtKGsytUR_Z_GwoDXkYcCkjAeLD_zpcZGbjbr3CefANopM4gbtgnSFYtQmIYBTGOw_GLqdBMPsPTSqoXG9OVfK8kfyE2kVFHcABARBD', '2016-12-16 11:10:09', '7200');

-- ----------------------------
-- Table structure for s_login_log
-- ----------------------------
DROP TABLE IF EXISTS `s_login_log`;
CREATE TABLE `s_login_log` (
  `pkid` varchar(40) NOT NULL,
  `user_name` varchar(20) default NULL,
  `login_time` datetime default NULL,
  `ip_address` varchar(20) default NULL,
  PRIMARY KEY  (`pkid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of s_login_log
-- ----------------------------
INSERT INTO `s_login_log` VALUES ('059740bd-8585-4232-a9ea-9da09eb798fb', '管理员', '2016-11-15 09:09:48', '127.0.0.1');
INSERT INTO `s_login_log` VALUES ('08c975d6-068a-44ba-8ef4-e569cf1b9812', 'admin', '2016-11-10 12:21:30', '127.0.0.1');
INSERT INTO `s_login_log` VALUES ('0bd8f039-2b07-4539-be6c-4a00080cb2f4', '管理员', '2016-11-10 15:07:57', '127.0.0.1');
INSERT INTO `s_login_log` VALUES ('0c236599-3e96-4212-8145-39aee66c1d04', '管理员', '2016-12-14 10:31:07', '127.0.0.1');
INSERT INTO `s_login_log` VALUES ('0d58cb09-2947-453b-b9bc-460fc7cefd05', '管理员', '2016-12-12 14:26:18', '127.0.0.1');
INSERT INTO `s_login_log` VALUES ('22406021-d3c6-48b1-8b45-fe4a115d4938', '管理员', '2016-11-11 14:16:42', '127.0.0.1');
INSERT INTO `s_login_log` VALUES ('264dd6ec-427e-46ec-b6ec-8ad4367ae5fa', '管理员', '2016-11-10 15:06:13', '127.0.0.1');
INSERT INTO `s_login_log` VALUES ('28116d1d-53d5-44f1-8eea-aa3904f8ba7a', 'admin', '2016-11-07 09:03:47', '127.0.0.1');
INSERT INTO `s_login_log` VALUES ('48d42990-068a-4355-9340-084d8e68c35b', '管理员', '2016-12-13 11:21:20', '127.0.0.1');
INSERT INTO `s_login_log` VALUES ('528b09ff-fdfa-4cc3-9549-28c0d396a16e', '管理员', '2016-12-13 11:08:30', '127.0.0.1');
INSERT INTO `s_login_log` VALUES ('5945df24-cc3c-499b-b341-3497c8a3a7f8', '管理员', '2016-12-13 14:07:46', '127.0.0.1');
INSERT INTO `s_login_log` VALUES ('5baeeded-7299-4bd1-8343-e0c35eb6abc9', '管理员', '2016-12-13 12:31:21', '127.0.0.1');
INSERT INTO `s_login_log` VALUES ('60294faa-90c7-4f68-9088-4ce5b3ce4c48', 'admin', '2016-11-10 12:40:56', '127.0.0.1');
INSERT INTO `s_login_log` VALUES ('61754684-85e8-42dc-a5e3-594fec8e92ea', '管理员', '2016-12-14 10:31:07', '127.0.0.1');
INSERT INTO `s_login_log` VALUES ('6a25872b-d792-4a6b-ac14-1777a97a52aa', '管理员', '2016-11-11 10:18:35', '127.0.0.1');
INSERT INTO `s_login_log` VALUES ('7b77dbbc-ae0e-4925-847a-1cf24c1ded2a', '管理员', '2016-12-13 13:43:44', '127.0.0.1');
INSERT INTO `s_login_log` VALUES ('7bca62de-4575-4976-9ecd-99c0132fd8ff', '管理员', '2016-12-13 11:21:21', '127.0.0.1');
INSERT INTO `s_login_log` VALUES ('7e99b528-4590-4d89-bd05-df829d515bd9', 'admin', '2016-11-10 15:05:03', '127.0.0.1');
INSERT INTO `s_login_log` VALUES ('827e4b76-4645-4a5d-8e4c-3054d427ad13', '管理员', '2016-12-13 14:07:46', '127.0.0.1');
INSERT INTO `s_login_log` VALUES ('877155c1-629b-4ea7-a180-be65516f21da', '管理员', '2016-12-13 11:08:30', '127.0.0.1');
INSERT INTO `s_login_log` VALUES ('8f89bea3-805a-4b81-9df8-f1e2ec34abd9', '管理员', '2016-12-13 16:41:06', '127.0.0.1');
INSERT INTO `s_login_log` VALUES ('90e55ad7-c0b0-44e5-baf9-4f68190d3d15', 'admin', '2016-11-07 10:00:26', '127.0.0.1');
INSERT INTO `s_login_log` VALUES ('91089887-aa4c-41f1-b97e-c90608235672', '管理员', '2016-12-14 12:32:40', '127.0.0.1');
INSERT INTO `s_login_log` VALUES ('9875bd3c-e367-4635-8838-ce42e51d59a0', 'admin', '2016-11-10 14:04:29', '127.0.0.1');
INSERT INTO `s_login_log` VALUES ('a36ae71e-a7ed-4b81-8fe2-48a356ae1ad0', 'admin', '2016-11-10 12:40:56', '127.0.0.1');
INSERT INTO `s_login_log` VALUES ('ad1f89b7-7248-402f-8023-71f8ca6054f2', 'admin', '2016-11-10 14:04:29', '127.0.0.1');
INSERT INTO `s_login_log` VALUES ('ccfa4ebf-d31c-4e89-b703-5a41f572342f', '管理员', '2016-12-13 11:32:14', '127.0.0.1');
INSERT INTO `s_login_log` VALUES ('d08e02dc-56d2-43a8-96eb-83538cb8f78a', '管理员', '2016-12-13 11:32:14', '127.0.0.1');
INSERT INTO `s_login_log` VALUES ('d137db5f-5533-4119-84fd-a483f4b5f6f5', '管理员', '2016-12-12 14:26:18', '127.0.0.1');
INSERT INTO `s_login_log` VALUES ('d8f6173d-ea30-4911-8622-40c23fd80da7', '管理员', '2016-12-13 13:48:27', '127.0.0.1');
INSERT INTO `s_login_log` VALUES ('e55bdba3-fb77-4c6a-b453-66d1f269b579', '管理员', '2016-12-13 16:41:06', '127.0.0.1');
INSERT INTO `s_login_log` VALUES ('f6a78ca2-50ea-40bf-b343-31e0f863b8c3', '管理员', '2016-11-10 15:06:52', '127.0.0.1');

-- ----------------------------
-- Table structure for s_user
-- ----------------------------
DROP TABLE IF EXISTS `s_user`;
CREATE TABLE `s_user` (
  `pkid` varchar(40) NOT NULL,
  `user_code` varchar(40) default NULL,
  `user_name` varchar(20) default NULL,
  `password` varchar(40) default NULL,
  `create_time` datetime default NULL,
  `update_time` datetime default NULL,
  `last_access_time` datetime default NULL COMMENT '最近访问时间',
  PRIMARY KEY  (`pkid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of s_user
-- ----------------------------
INSERT INTO `s_user` VALUES ('ddc08152-90f3-4c7e-9bab-e0588ded88a1', 'admin', '管理员', 'c4ca4238a0b923820dcc509a6f75849b', '2016-11-10 14:51:29', '2016-11-10 14:51:29', '2016-12-14 12:32:41');
INSERT INTO `s_user` VALUES ('ddc08152-90f3-4c7e-9bab-e0588ded88a2', 'admin1', '管理员1', 'c4ca4238a0b923820dcc509a6f75849b', '2016-11-10 12:51:29', '2016-11-10 12:51:29', '2016-11-10 12:05:03');
INSERT INTO `s_user` VALUES ('ddc08152-90f3-4c7e-9bab-e0588ded88a3', 'admin2', '管理员3', 'c4ca4238a0b923820dcc509a6f75849b', '2016-11-10 11:51:29', '2016-11-10 11:51:29', '2016-11-10 11:07:57');

-- ----------------------------
-- Table structure for user_bound_info
-- ----------------------------
DROP TABLE IF EXISTS `user_bound_info`;
CREATE TABLE `user_bound_info` (
  `id` int(11) NOT NULL auto_increment,
  `open_id` varchar(40) default NULL,
  `phone_num` varchar(15) default NULL COMMENT '用户手机号',
  `msg_id` varchar(11) default NULL COMMENT '内部校验短信ID',
  `eff_time` datetime default NULL COMMENT '生效时间',
  `exp_time` datetime default NULL COMMENT '失效时间',
  `ver_code` varchar(10) default NULL COMMENT '验证码',
  `create_time` datetime default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user_bound_info
-- ----------------------------

-- ----------------------------
-- Table structure for validate_message
-- ----------------------------
DROP TABLE IF EXISTS `validate_message`;
CREATE TABLE `validate_message` (
  `id` int(11) NOT NULL auto_increment,
  `open_id` varchar(40) default NULL,
  `phone_num` varchar(15) default NULL COMMENT '用户手机号',
  `ver_code` varchar(10) default NULL COMMENT '验证码',
  `expires_in` int(11) default NULL COMMENT '有效期',
  `create_time` datetime default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of validate_message
-- ----------------------------

-- ----------------------------
-- Table structure for wechat_user
-- ----------------------------
DROP TABLE IF EXISTS `wechat_user`;
CREATE TABLE `wechat_user` (
  `id` int(11) NOT NULL auto_increment,
  `open_id` varchar(40) default NULL,
  `subscribe_status` int(1) default NULL COMMENT '用户是否关注 1 已关注 0取消关注',
  `status_time` datetime default NULL COMMENT '状态时间',
  `subscribe_time` datetime default NULL,
  `nick_name` varchar(100) default NULL COMMENT '用户昵称',
  `sex` int(1) default NULL COMMENT '性别1男 0女',
  `language` varchar(10) default NULL COMMENT '语言',
  `city` varchar(100) default NULL COMMENT '所在城市',
  `province` varchar(100) default NULL COMMENT '所在省份',
  `country` varchar(100) default NULL COMMENT '所在国家',
  `head_img_url` varchar(1024) default NULL COMMENT '微信头像url',
  `union_id` varchar(100) default NULL COMMENT '用户全局ID',
  `remark` varchar(100) default NULL,
  `group_id` varchar(100) default NULL COMMENT '所属组ID',
  `create_time` datetime default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of wechat_user
-- ----------------------------
INSERT INTO `wechat_user` VALUES ('1', 'oZ4yrwK61B81cp9bQdglLtGXO99U', '1', '2016-12-16 09:26:19', '2016-12-16 09:26:19', 'pis', '1', 'zh_CN', '南京', '江苏', '中国', 'http://wx.qlogo.cn/mmopen/NrO5iaf29SjrlHv0b10ialuIcQvJw6dbZNfsTYH60CDL08o9Hv0cWLZHibgiagro0XCZGNb239PvoRWEXwRuMCAxZw/0', null, '', '0', '2016-12-14 16:18:52');
