create table if not exists cameras
(
    id         serial,
    nasa_id    bigint       not null,
    name       varchar(255) not null,
    created_at timestamp    not null default now(),
    constraint pk_cameras primary key (id),
    constraint uq_cameras unique (nasa_id)
);

create table if not exists photos
(
    id         serial,
    nasa_id    bigint    not null,
    img_src    text      not null,
    camera_id  bigint    not null,
    created_at timestamp not null default now(),
    constraint pk_photos primary key (id),
    constraint uq_photos unique (nasa_id),
    constraint fk_photos_cameras foreign key (camera_id) references cameras (id)
);