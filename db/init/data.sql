create table carpool
(
    carpool_id     varchar(36)  not null
        primary key,
    created_at     datetime(6)  not null,
    deleted_at     datetime(6)  null,
    modified_at    datetime(6)  not null,
    end_area       varchar(255) null,
    boarding_place varchar(255) null,
    boarding_price int          null,
    creator_id     varchar(36) null,
    start_area     varchar(255) null,
    start_time     datetime(6)  null,
    open_chat_url  varchar(255) null,
    recruit_person int          null,
    status         varchar(255) null
);

create table driver
(
    driver_id     varchar(36)  not null
        primary key,
    car_image_url varchar(255) null,
    car_number    varchar(255) null,
    member_id     varchar(36) null,
    phone_number  varchar(255) null
);

create table member
(
    member_id         varchar(36)  not null
        primary key,
    created_at        datetime(6)  not null,
    deleted_at        datetime(6)  null,
    modified_at       datetime(6)  not null,
    password          varchar(255) null,
    profile_image_url varchar(255) null,
    member_type       varchar(255) null,
    username          varchar(255) null,
    email             varchar(255) null
);

create table notice
(
    notice_id   varchar(36)  not null
        primary key,
    created_at  datetime(6)  not null,
    deleted_at  datetime(6)  null,
    modified_at datetime(6)  not null,
    content     text         null,
    title       varchar(255) null,
    writer_id   varchar(36) null
);

create table passenger
(
    passenger_id varchar(36)  not null
        primary key,
    created_at   datetime(6)  not null,
    deleted_at   datetime(6)  null,
    modified_at  datetime(6)  not null,
    member_id    varchar(36)  null,
    carpool_id   varchar(36)  null,
    status       varchar(255) null
);


create table question
(
    question_id varchar(36)  not null
        primary key,
    created_at  datetime(6)  not null,
    deleted_at  datetime(6)  null,
    modified_at datetime(6)  not null,
    content     text         null,
    writer_id   varchar(36) null
);


create table report
(
    report_id   varchar(36)  not null
        primary key,
    created_at  datetime(6)  not null,
    deleted_at  datetime(6)  null,
    modified_at datetime(6)  not null,
    content     text         null,
    reported_id varchar(36) null,
    reporter_id varchar(36) null,
    carpool_id varchar(36) null
);