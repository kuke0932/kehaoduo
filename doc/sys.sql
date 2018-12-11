/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2017/10/20 9:00:21                           */
/*==============================================================*/


drop table if exists sys_function;

drop table if exists sys_role;

drop table if exists sys_role_function;

drop table if exists sys_role_user;

drop table if exists sys_user;

drop table if exists web_user;

/*==============================================================*/
/* Table: sys_function                                          */
/*==============================================================*/
create table sys_function
(
   id                   int(11) not null auto_increment comment '主键',
   parent_id            int(11) comment '父ID',
   function_name        varchar(100) comment '菜单名称',
   function_url         varchar(255) comment '菜单地址',
   function_type        tinyint(1) comment '菜单类型1 菜单 2 按钮',
   function_sort        int(11) comment '菜单顺序',
   click_function       varchar(45) comment '如果是按钮，表示js点击事件

            如js中的add delete方法',
   button_style         varchar(45) comment '按钮样式',
   icon_style           varchar(45) comment '按钮或者菜单上图标样式',
   is_visible           tinyint(4) default 1 comment '是否展示在页面上  1 展示 0  隐藏',
   logical_del          tinyint default 0 comment '逻辑删除标志',
   create_by            int(11) comment '创建者ID',
   create_name          varchar(20) comment '创建者名字',
   create_time          datetime comment '创建时间',
   update_by            int(11) comment '更新者ID',
   update_name          varchar(20) comment '更新者名字',
   update_time          datetime comment '更新时间',
   primary key (id)
)
engine = innodb
default character set = utf8
collate = utf8_general_ci;

alter table sys_function comment '菜单表';

/*==============================================================*/
/* Table: sys_role                                              */
/*==============================================================*/
create table sys_role
(
   id                   int(11) not null auto_increment comment '主键',
   parent_id            int(11) comment '父ID',
   role_name            varchar(100) comment '角色名称',
   role_value           varchar(50) comment '角色值',
   role_desc            varchar(200) comment '角色描述',
   role_type            int(11) comment '角色类型',
   logical_del          tinyint default 0 comment '逻辑删除标志',
   create_by            int(11) comment '创建者ID',
   create_name          varchar(20) comment '创建者名字',
   create_time          datetime comment '创建时间',
   update_by            int(11) comment '更新者ID',
   update_name          varchar(20) comment '更新者名字',
   update_time          datetime comment '更新时间',
   primary key (id),
   key role_id_unique (id),
   key role_name_unique (role_name),
   key role_value_unique (role_value)
)
engine = innodb
default character set = utf8
collate = utf8_general_ci;

alter table sys_role comment '角色表';

/*==============================================================*/
/* Table: sys_role_function                                     */
/*==============================================================*/
create table sys_role_function
(
   id                   int(11) not null auto_increment comment '主键',
   role_id              int(11) comment '角色ID',
   function_id          int(11) comment '菜单ID',
   logical_del          tinyint default 0 comment '逻辑删除标志',
   create_by            int(11) comment '创建者ID',
   create_name          varchar(20) comment '创建者名字',
   create_time          datetime comment '创建时间',
   update_by            int(11) comment '更新者ID',
   update_name          varchar(20) comment '更新者名字',
   update_time          datetime comment '更新时间',
   primary key (id)
)
engine = innodb
default character set = utf8
collate = utf8_general_ci;

alter table sys_role_function comment '角色菜单关联表';

/*==============================================================*/
/* Table: sys_role_user                                         */
/*==============================================================*/
create table sys_role_user
(
   id                   int(11) not null auto_increment comment '主键',
   role_id              int(11) comment '角色ID',
   user_id              int(11) comment '用户ID',
   logical_del          tinyint default 0 comment '逻辑删除标志',
   create_by            int(11) comment '创建者ID',
   create_name          varchar(20) comment '创建者名字',
   create_time          datetime comment '创建时间',
   update_by            int(11) comment '更新者ID',
   update_name          varchar(20) comment '更新者名字',
   update_time          datetime comment '更新时间',
   primary key (id)
)
engine = innodb
default character set = utf8
collate = utf8_general_ci;

alter table sys_role_user comment '角色用户关联表';

/*==============================================================*/
/* Table: sys_user                                              */
/*==============================================================*/
create table sys_user
(
   id                   int(11) not null auto_increment comment '主键',
   login_name           varchar(20) not null comment '登录名',
   login_pwd            varchar(32) not null comment '登录密码',
   salt                 varchar(6) comment '盐',
   user_name            varchar(50) comment '用户真实姓名名',
   email                varchar(50) comment '邮件地址',
   mobile               varchar(12) not null default '0' comment '手机号码',
   telephone            varchar(20) comment '座机号码',
   status               tinyint(1) default 1 comment '状态.1: 正常,0:冻结',
   last_login_time      timestamp comment '最后登录时间',
   last_login_ip        varchar(20) comment '最后登录IP',
   web_user_id          int(11) comment '前台注册用户ID',
   api_key              varchar(16) comment 'WEBAPI key',
   api_secret           varchar(32) comment 'WEBAPI secret',
   logical_del          tinyint default 0 comment '逻辑删除标志   1 删除 0 未删除',
   create_by            int(11) comment '创建者ID',
   create_name          varchar(20) comment '创建者名字',
   create_time          datetime comment '创建时间',
   update_by            int(11) comment '更新者ID',
   update_name          varchar(20) comment '更新者名字',
   update_time          datetime comment '更新时间',
   primary key (id),
   key login_name_unique (login_name),
   unique key ak_telephone_unique (telephone)
)
engine = innodb
default character set = utf8
collate = utf8_general_ci;

alter table sys_user comment '系统用户表';

/*==============================================================*/
/* Table: sys_web_user                                          */
/*==============================================================*/
create table web_user
(
   id                   int(11) not null auto_increment comment '主键',
   login_name           varchar(20) not null comment '登录名',
   login_pwd            varchar(32) not null comment '登录密码',
   salt                 varchar(6) comment '盐',
   user_name            varchar(50) comment '用户真实姓名名',
   email                varchar(50) comment '邮件地址',
   mobile               varchar(12) default '0' comment '手机号码',
   telephone            varchar(20) comment '座机号码',
   status               tinyint(1) default 1 comment '状态.1: 正常,0:冻结',
   last_login_time      timestamp comment '最后登录时间',
   last_login_ip        varchar(20) comment '最后登录IP',
   api_key              varchar(16) comment 'WEBAPI key',
   api_secret           varchar(32) comment 'WEBAPI secret',
   logical_del          tinyint default 0 comment '逻辑删除标志   1 删除 0 未删除',
   create_by            int(11) comment '创建者ID',
   create_name          varchar(20) comment '创建者名字',
   create_time          datetime comment '创建时间',
   update_by            int(11) comment '更新者ID',
   update_name          varchar(20) comment '更新者名字',
   update_time          datetime comment '更新时间',
   primary key (id),
   unique key login_name_unique (login_name),
   unique key ak_telephone_unique (mobile)
)
engine = innodb
default character set = utf8
collate = utf8_general_ci;

alter table sys_web_user comment '前台注册用户表';




-- ----------------------------
-- Records of sys_function
-- ----------------------------
INSERT INTO `sys_function` VALUES ('1', '0', '菜单管理', null, '1', '1', null, null, null, '1', '0', null, null, null, null, null, null);
INSERT INTO `sys_function` VALUES ('54', '58', '用户管理', '/admin/system/sysuser/sys_user_list.html', '1', '1', null, null, null, '1', '0', null, null, null, null, null, null);
INSERT INTO `sys_function` VALUES ('56', '58', '角色管理', '/admin/system/sysrole/sys_role_manager.html', '1', '3', null, '', '', '1', '0', null, null, null, null, null, null);
INSERT INTO `sys_function` VALUES ('58', '1', '系统管理', '1', '1', '1', null, null, 'fa fa fa-bar-chart-o', '1', '0', null, null, null, null, null, null);
INSERT INTO `sys_function` VALUES ('59', '56', '添加', '/system/admin/sysRole/addRole', '2', '1', null, null, null, '1', '0', null, null, null, null, null, null);
INSERT INTO `sys_function` VALUES ('60', '56', '修改', '/system/admin/sysRole/updateRole', '2', '1', null, null, null, '1', '0', null, null, null, null, null, null);
INSERT INTO `sys_function` VALUES ('61', '56', '删除', '/system/admin/sysRole/delRole', '2', '1', null, null, null, '1', '0', null, null, null, null, null, null);
INSERT INTO `sys_function` VALUES ('62', '58', '菜单管理', '/admin/system/sysfunction/sys_function_manager.html', '1', '2', null, '', '', '1', '0', null, null, null, null, null, null);
INSERT INTO `sys_function` VALUES ('63', '62', '添加', '/system/admin/sysFunction/addFunction', '2', '2', null, null, null, '1', '0', null, null, null, null, null, null);
INSERT INTO `sys_function` VALUES ('64', '62', '修改', '/system/admin/sysFunction/updateFunction', '2', '2', null, null, null, '1', '0', null, null, null, null, null, null);
INSERT INTO `sys_function` VALUES ('66', '58', '授权管理', '/admin/system/authorization/authorization_manager.html', '1', '4', null, null, null, '1', '0', null, null, null, null, null, null);
INSERT INTO `sys_function` VALUES ('68', '66', '保存', '/system/admin/sysRoleFunction/setRoleFunctions', '2', '1', null, null, null, '1', '0', null, null, null, null, null, null);
INSERT INTO `sys_function` VALUES ('70', '54', '删除', '/system/admin/sysUser/deleteById', '2', '2', null, null, null, '1', '0', null, null, null, null, null, null);
INSERT INTO `sys_function` VALUES ('71', '62', '删除', '/system/admin/sysFunction/delFunction', '2', '3', null, null, null, '1', '0', null, null, null, null, null, null);
INSERT INTO `sys_function` VALUES ('72', '66', '添加', '/system/admin/sysRoleUser/addUserToRole', '2', '1', null, null, null, '1', '0', null, null, null, null, null, null);
INSERT INTO `sys_function` VALUES ('73', '66', '移除', '/system/admin/sysRoleUser/removeUserFromRole', '2', '1', null, null, null, '1', '0', null, null, null, null, null, null);
INSERT INTO `sys_function` VALUES ('74', '54', '添加', 'aa', '2', '1', null, null, null, '1', '0', null, null, null, null, null, null);
INSERT INTO `sys_function` VALUES ('75', '1', '问卷调查', '1', '1', '2', null, null, null, '1', '0', null, null, null, null, null, null);
INSERT INTO `sys_function` VALUES ('76', '75', '问卷调查管理', '/admin/survey/survey/survey_list.html', '1', '1', null, '', '', '1', '0', null, null, null, null, null, null);
INSERT INTO `sys_function` VALUES ('77', '1', '通讯录', '/admin/contact/contacts.html', '1', '3', null, '', '', '1', '0', null, null, null, null, null, null);
INSERT INTO `sys_function` VALUES ('78', '75', '回答管理', '/admin/survey/answer/answer_list.html', '1', '2', null, '', '', '1', '0', null, null, null, null, null, null);
INSERT INTO `sys_function` VALUES ('79', '76', '添加', '/survey/admin/surveySurvey/addSurveySurvey', '2', '1', null, '', '', '1', '0', null, null, null, null, null, null);
INSERT INTO `sys_function` VALUES ('80', '76', '修改问卷', '/survey/admin/surveySurvey/edit', '2', '2', null, '', '', '1', '0', null, null, null, null, null, null);
INSERT INTO `sys_function` VALUES ('81', '76', '删除', '/survey/admin/surveySurvey/remove', '2', '4', null, '', '', '1', '0', null, null, null, null, null, null);
INSERT INTO `sys_function` VALUES ('82', '78', '查看详情', '/survey/admin/surveyAnswer/listAll', '2', '1', null, '', '', '1', '0', null, null, null, null, null, null);
INSERT INTO `sys_function` VALUES ('83', '79', '添加详情', '/survey/admin/surveySurvey/addDetail', '2', '1', null, '', '', '0', '0', null, null, null, null, null, null);
INSERT INTO `sys_function` VALUES ('84', '79', '添加问题', '/survey/admin/surveySurvey/addQuestions', '2', '2', null, '', '', '0', '0', null, null, null, null, null, null);
INSERT INTO `sys_function` VALUES ('85', '76', '修改问题', '/survey/', '2', '3', null, '', '', '1', '0', null, null, null, null, null, null);

INSERT INTO `sys_role` VALUES ('1', '0', '角色管理', null, null, null, '0', null, null, null, null, null, null);
INSERT INTO `sys_role` VALUES ('2', '1', '后台管理员', null, null, null, '0', null, null, null, null, null, null);
INSERT INTO `sys_role` VALUES ('3', '1', '用户', null, null, null, '0', null, null, null, null, null, null);

INSERT INTO `sys_role_function` VALUES ('1', '3', '1', '0', null, null, null, null, null, null);
INSERT INTO `sys_role_function` VALUES ('2', '3', '77', '0', null, null, null, null, null, null);
INSERT INTO `sys_role_function` VALUES ('3', '2', '1', '0', null, null, null, null, null, null);
INSERT INTO `sys_role_function` VALUES ('4', '2', '58', '0', null, null, null, null, null, null);
INSERT INTO `sys_role_function` VALUES ('5', '2', '54', '0', null, null, null, null, null, null);
INSERT INTO `sys_role_function` VALUES ('6', '2', '74', '0', null, null, null, null, null, null);
INSERT INTO `sys_role_function` VALUES ('7', '2', '70', '0', null, null, null, null, null, null);
INSERT INTO `sys_role_function` VALUES ('8', '2', '62', '0', null, null, null, null, null, null);
INSERT INTO `sys_role_function` VALUES ('9', '2', '64', '0', null, null, null, null, null, null);
INSERT INTO `sys_role_function` VALUES ('10', '2', '63', '0', null, null, null, null, null, null);
INSERT INTO `sys_role_function` VALUES ('11', '2', '71', '0', null, null, null, null, null, null);
INSERT INTO `sys_role_function` VALUES ('12', '2', '56', '0', null, null, null, null, null, null);
INSERT INTO `sys_role_function` VALUES ('13', '2', '60', '0', null, null, null, null, null, null);
INSERT INTO `sys_role_function` VALUES ('14', '2', '61', '0', null, null, null, null, null, null);
INSERT INTO `sys_role_function` VALUES ('15', '2', '59', '0', null, null, null, null, null, null);
INSERT INTO `sys_role_function` VALUES ('16', '2', '66', '0', null, null, null, null, null, null);
INSERT INTO `sys_role_function` VALUES ('17', '2', '68', '0', null, null, null, null, null, null);
INSERT INTO `sys_role_function` VALUES ('18', '2', '72', '0', null, null, null, null, null, null);
INSERT INTO `sys_role_function` VALUES ('19', '2', '73', '0', null, null, null, null, null, null);
INSERT INTO `sys_role_function` VALUES ('20', '2', '75', '0', null, null, null, null, null, null);
INSERT INTO `sys_role_function` VALUES ('21', '2', '76', '0', null, null, null, null, null, null);
INSERT INTO `sys_role_function` VALUES ('22', '2', '79', '0', null, null, null, null, null, null);
INSERT INTO `sys_role_function` VALUES ('23', '2', '83', '0', null, null, null, null, null, null);
INSERT INTO `sys_role_function` VALUES ('24', '2', '84', '0', null, null, null, null, null, null);
INSERT INTO `sys_role_function` VALUES ('25', '2', '80', '0', null, null, null, null, null, null);
INSERT INTO `sys_role_function` VALUES ('26', '2', '85', '0', null, null, null, null, null, null);
INSERT INTO `sys_role_function` VALUES ('27', '2', '81', '0', null, null, null, null, null, null);
INSERT INTO `sys_role_function` VALUES ('28', '2', '78', '0', null, null, null, null, null, null);
INSERT INTO `sys_role_function` VALUES ('29', '2', '77', '0', null, null, null, null, null, null);

INSERT INTO `sys_role_user` VALUES ('1', '2', '1', '0', null, null, null, null, null, null);


INSERT INTO `sys_user` (`id`, `login_name`, `login_pwd`, `salt`, `user_name`, `email`, `telephone`, `status`, `last_login_time`, `last_login_ip`, `web_user_id`, `logical_del`, `create_by`, `create_name`, `create_time`, `update_by`, `update_name`, `update_time`) VALUES (1, 'admin', '5c9bdddf25f5783e2a98c9831289c760', 'f094uy', 'admin', NULL, '13663218177', 1, '2017-10-12 17:45:38', NULL, NULL, 0, NULL, NULL, NULL, NULL, NULL, NULL);
