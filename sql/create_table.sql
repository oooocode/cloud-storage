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


-- auto-generated definition
create table file_info
(
    id            bigint auto_increment comment '主键id'
        primary key,
    user_id       bigint               not null comment '用户id',
    file_name     varchar(512)         null comment '文件名',
    parent_id     bigint               null comment '父id',
    size          bigint               null comment '文件大小',
    file_md5      varchar(32)          null comment '文件md5值',
    cover         varchar(100)         null comment '文件封面',
    path          varchar(100)         null comment '文件路径',
    folder_type   tinyint(1)           not null comment '目录类型 0-文件 1-目录',
    file_type     varchar(255)         not null comment '文件类型 0-视频 1-音频 2-图片 3-pdf 4-doc 5-excel 6-txt 7-code 8-zip 9-其他',
    category      tinyint(1)           not null comment '文件分类 0-视频 1-音频 2-图片 3-文档 4-其他',
    status        tinyint(1)           not null comment '文件状态 0-转码成功 1-转码中 2-转码失败',
    file_location tinyint(1) default 0 not null comment '0-正常 1-回收站',
    recover_time  datetime             null comment '进入回收站时间',
    create_time   datetime(3)  default CURRENT_TIMESTAMP(3) not null comment '创建时间',
    update_time   datetime(3)  default CURRENT_TIMESTAMP(3) not null on update CURRENT_TIMESTAMP(3) comment '修改时间',
    is_delete     tinyint(1) default 0 not null comment '0- 正常 1--删除'
);

create index idx_create_time
    on file_info (create_time);

create index idx_file_md5
    on file_info (file_md5);

create index idx_parent_id
    on file_info (parent_id);

create index idx_recover_time
    on file_info (recover_time);

create index idx_user_id
    on file_info (user_id);


