create table user
(
    id          int auto_increment comment 'id'
        primary key,
    user_no     char(20)     default ''                not null comment '用户编号',
    username    varchar(50)  default ''                not null comment '用户名称',
    password    varchar(255) default ''                not null comment '密码',
    mobile      varchar(20)  default ''                not null comment '手机号',
    email       varchar(255) default ''                not null comment '邮箱',
    avatar      varchar(255) default ''                not null comment '头像',
    gender      tinyint      default 0                 not null comment ' 性别 0 未知 1 男 2 女',
    create_time datetime     default CURRENT_TIMESTAMP null,
    update_time datetime     default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP
);