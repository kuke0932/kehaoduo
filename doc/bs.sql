/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2017/10/20 8:59:50                           */
/*==============================================================*/


drop table if exists t_login_sms_log;

drop table if exists t_money_change_log;

drop table if exists t_publicity;

drop table if exists t_publicity_read_log;

drop table if exists t_publicity_result;

drop table if exists t_shop;

drop table if exists t_user_ext;

/*==============================================================*/
/* Table: t_login_sms_log                                       */
/*==============================================================*/
create table t_login_sms_log
(
   id                   int not null auto_increment comment '主键',
   sms_content          varchar(255) comment '短信内容',
   sms_code             char(6) comment '短信验证码',
   send_time            datetime comment '发送时间',
   sms_type             tinyint comment '短信类型',
   logical_del          tinyint default 0 comment '逻辑删除标志   1 删除 0 未删除',
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

alter table t_login_sms_log comment '存缓存';

/*==============================================================*/
/* Table: t_money_change_log                                    */
/*==============================================================*/
create table t_money_change_log
(
  id           int           not null auto_increment comment '主键',
  web_user_id  INT           NOT NULL
  COMMENT '用户id',
  money        decimal(10,2) not null comment '金额',
  operate_type tinyint       not null comment '操作类型，1充值2提现3消费',
  logical_del  tinyint default 0 comment '逻辑删除标志   1 删除 0 未删除',
  create_by    int(11) comment '创建者ID',
  create_name  varchar(20) comment '创建者名字',
  create_time  datetime comment '创建时间',
  update_by    int(11) comment '更新者ID',
  update_name  varchar(20) comment '更新者名字',
  update_time  datetime comment '更新时间',
   primary key (id)
)
engine = innodb
default character set = utf8
collate = utf8_general_ci;

alter table t_money_change_log comment '余额变动';

/*==============================================================*/
/* Table: t_publicity                                           */
/*==============================================================*/
create table t_publicity
(
  id               int not null auto_increment comment '主键',
  first_image_url  varchar(255) comment '图片地址',
  publicity_title  varchar(31) not null comment '标题',
  publicity_intro  varchar(255) comment '简介',
  content          TEXT COMMENT '正文',
  check_status     tinyint default 0 comment '审核状态0 未填写 1 已保存2 未审核 3 审核通过 4 审核不通过',
  content_type     tinyint not null comment '正文类型(1文本2图文)',
  qrcode_url       varchar(255) comment '推广二维码地址',
  fee              varchar(255) not null default '0' comment '推广费用',
  total_read_count int comment '全部推广次数',
  is_ended         tinyint not null default 0 comment '是否结束0 未结束 1已结束',
  valid_area_code  varchar(255) comment '有效区域，(省市县三级，以逗号分隔)',
  is_contain_phone tinyint default 0 comment '是否启用客户电话功能 0 不启用1 启用',
  web_user_id      INT COMMENT '所属于用户ID',
  to_user_type     tinyint not null comment '发送给用户类型1全部用户2男性朋友3女性朋友4群5最近联系人6选择好友',
  static_url       varchar(255) comment '生成的静态地址',
  logical_del      tinyint default 0 comment '逻辑删除标志   1 删除 0 未删除',
  create_by        int(11) comment '创建者ID',
  create_name      varchar(20) comment '创建者名字',
  create_time      datetime comment '创建时间',
  update_by        int(11) comment '更新者ID',
  update_name      varchar(20) comment '更新者名字',
  update_time      datetime comment '更新时间',
   primary key (id)
)
engine = innodb
default character set = utf8mb4
collate = utf8mb4_general_ci;

alter table t_publicity comment '推广信息';

/*==============================================================*/
/* Table: t_publicity_ext                                       */
/*==============================================================*/
CREATE TABLE t_publicity_ext
(
  id            INT NOT NULL AUTO_INCREMENT
  COMMENT '主键',
  publicity_id  INT COMMENT '推广ID',
  content_text  TEXT COMMENT '正文文本',
  content_image VARCHAR(255) COMMENT '正文图片',
  logical_del   TINYINT      DEFAULT 0
  COMMENT '逻辑删除标志   1 删除 0 未删除',
  create_by     INT(11) COMMENT '创建者ID',
  create_name   VARCHAR(20) COMMENT '创建者名字',
  create_time   DATETIME COMMENT '创建时间',
  update_by     INT(11) COMMENT '更新者ID',
  update_name   VARCHAR(20) COMMENT '更新者名字',
  update_time   DATETIME COMMENT '更新时间',
  PRIMARY KEY (id)
)
  ENGINE = innodb
  DEFAULT CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci;

ALTER TABLE t_publicity
  COMMENT '推广信息扩展表';

/*==============================================================*/
/* Table: t_publicity_read_log                                  */
/*==============================================================*/
create table t_publicity_read_log
(
   id                   int not null auto_increment comment 'zhujain',
   publicity_id         int comment '推广ID',
   from_open_id         varchar(255) comment '推广者openid',
   reader_open_id       varchar(255) comment '读者openid',
   is_valid             tinyint default 0 comment '该次阅读文章是否有效0 无效1有效',
   read_time            datetime comment '阅读时间',
   logical_del          tinyint default 0 comment '逻辑删除标志   1 删除 0 未删除',
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

alter table t_publicity_read_log comment '点击日志';

/*==============================================================*/
/* Table: t_publicity_result                                    */
/*==============================================================*/
create table t_publicity_result
(
  id               int          not null auto_increment comment '主键',
  publicity_id     int          not null comment '推广主键',
  total_read_count int          not null comment '全部推广次数，冗余字段',
  user_wechat_id   varchar(255) comment '扫码者微信id, 可能获取不到，转发者openId',
  user_nickname    varchar(255) not null comment '扫码（转发）者昵称',
  user_head_image  VARCHAR(255) NOT NULL
  COMMENT '扫码（转发）者头像',
  operate_time     datetime comment '扫码（转发）时间',
  to_user_count    int comment '发送人数',
  read_count       int comment '阅读次数',
  publicity_type   tinyint      not null comment '推广类型 1扫码2转发',
  logical_del      tinyint default 0 comment '逻辑删除标志   1 删除 0 未删除',
  create_by        int(11) comment '创建者ID',
  create_name      varchar(20) comment '创建者名字',
  create_time      datetime comment '创建时间',
  update_by        int(11) comment '更新者ID',
  update_name      varchar(20) comment '更新者名字',
   update_time          datetime comment '更新时间',
   primary key (id)
)
engine = innodb
default character set = utf8
collate = utf8_general_ci;

alter table t_publicity_result comment '推广效果，包括转发，二维码';

/*==============================================================*/
/* Table: t_shop                                                */
/*==============================================================*/
create table t_shop
(
  id                   int         not null auto_increment comment '主键',
  shop_name            varchar(31) not null comment '店铺名字',
  shop_desc            varchar(255) comment '店铺简介',
  address_codes        varchar(31) not null comment '地址，多个code以逗号分割',
  business_line_codes varchar(31) not null comment '经营种类, 多个code以逗号分割',
  web_user_id          INT         NOT NULL
  COMMENT '用户id',
  logical_del          tinyint default 0 comment '逻辑删除标志   1 删除 0 未删除',
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

alter table t_shop comment '店铺';

/*==============================================================*/
/* Table: t_user_ext                                            */
/*==============================================================*/
create table t_user_ext
(
   id                   int(11) not null comment '用户表外键',
   openid_a             varchar(255) comment '主公众号用户openid',
   openid_b             varchar(255) comment '充值公众号的用户openid',
   last_login_type      tinyint not null default 0 comment '上次登录类型 0 未登录 1 广告主 2 普通用户',
   balance              decimal(10,2) not null default 0.00 comment '余额',
   logical_del          tinyint default 0 comment '逻辑删除标志   1 删除 0 未删除',
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

alter table t_user_ext comment '用户扩展表';

