-- auto-generated definition
create table user
(
    id              bigint auto_increment comment '主键id'
        primary key,
    nick_name       varchar(20)                              null comment '昵称',
    email           varchar(150)                             null comment '邮箱',
    qq_open_id      varchar(35)                              null comment 'qqOpenId',
    qq_avatar       varchar(150)                             null comment 'qq头像',
    password        varchar(32)                              null comment '密码',
    status          tinyint(1)  default 0                    not null comment '0-禁用 1-启用',
    last_login_time datetime                                 null comment '最近一次登录时间',
    create_time     datetime(3) default CURRENT_TIMESTAMP(3) not null comment '创建时间',
    update_time     datetime(3) default CURRENT_TIMESTAMP(3) not null on update CURRENT_TIMESTAMP(3) comment '修改时间',
    is_delete       tinyint(1)  default 0                    not null comment '逻辑删除 0-存在 1-删除'
)
    comment '用户表';

