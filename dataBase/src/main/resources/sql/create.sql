
create table tag
(
  id   bigserial    not null
    constraint tag_pkey
    primary key,
  name varchar(100) not null
    constraint tag_name_pk
    unique
);

comment on table tag
is 'This is table for tags';

create table gift_certificate
(
  id                bigserial        not null
    constraint gift_certificate_pkey
    primary key,
  name              varchar(150)     not null,
  description       varchar(200),
  price             double precision not null,
  creation_date     date default CURRENT_DATE,
  modification_date date default CURRENT_DATE,
  duration          smallint
);

comment on table gift_certificate
is 'This is table for gift certificates';

create unique index gift_certificate_name_uindex
  on gift_certificate (name);

create table tag_for_gift_certificate
(
  tag_id              bigint not null
    constraint tag_id
    references tag,
  gift_certificate_id bigint not null
    constraint gift_certificate_id
    references gift_certificate
);

create index fki_tag_id
  on tag_for_gift_certificate (tag_id);

create index fki_gift_certificate_id
  on tag_for_gift_certificate (gift_certificate_id);

create table order_
(
  id         bigserial not null,
  user_name  varchar(150),
  time_stamp timestamp,
  cost       numeric(19, 2)
);


create unique index order_id_uindex
  on order_ (id);
create table order_gift_certificate
(
  order_id            bigint not null
    constraint fk1vylv8ps9h1yrj9a8xt237rq6
    references order_ (id),
  gift_certificate_id bigint not null
    constraint fkjsut7sjrnmbdqeae9tqfvf3l6
    references gift_certificate
);


