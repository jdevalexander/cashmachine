create database cash_machine
	with owner postgres;

create table card_info
(
    id          serial                not null
        constraint card_info_pk
            primary key,
    card_number bigint                not null,
    pin_code    integer               not null,
    balance     bigint                not null,
    blocked     boolean default false not null
);

comment on table card_info is 'Full Card information ';

alter table card_info
    owner to postgres;

create unique index card_info_card_number_uindex
    on card_info (card_number);

INSERT INTO public.card_info (id, card_number, pin_code, balance, blocked) VALUES (1, 6666777734211234, 1234, 1244, false);
INSERT INTO public.card_info (id, card_number, pin_code, balance, blocked) VALUES (2, 7773123487654321, 4565, 676, false);
INSERT INTO public.card_info (id, card_number, pin_code, balance, blocked) VALUES (3, 4213554409877890, 4355, 1000, false);
INSERT INTO public.card_info (id, card_number, pin_code, balance, blocked) VALUES (4, 7654432156780987, 7755, 12344523876, true);


create table operation_catalog
(
    id          serial  not null
        constraint operation_catalog_pk
            primary key,
    code        varchar not null,
    description varchar not null
);

comment on table operation_catalog is 'Catalog of various operations with cash machine';

alter table operation_catalog
    owner to postgres;

create unique index operation_catalog_code_uindex
    on operation_catalog (code);

INSERT INTO public.operation_catalog (id, code, description) VALUES (1, 'show_balance', 'Checking card balance ');
INSERT INTO public.operation_catalog (id, code, description) VALUES (2, 'withdraw_money', 'Withdraw money');



create table operation_log
(
    id             serial  not null
        constraint operation_log_pk
            primary key,
    card_id        integer not null
        constraint operation_log_card_info_id_fk
            references card_info,
    date           bigint  not null,
    operation_code varchar not null,
    withdrew_money bigint
);

comment on table operation_log is 'Logs of operation with cash machine';

alter table operation_log
    owner to postgres;

INSERT INTO public.operation_log (id, card_id, date, operation_code, withdrew_money) VALUES (6, 3, 1601234414104, 'show_balance', null);
INSERT INTO public.operation_log (id, card_id, date, operation_code, withdrew_money) VALUES (7, 3, 1601234918903, 'show_balance', null);
INSERT INTO public.operation_log (id, card_id, date, operation_code, withdrew_money) VALUES (8, 3, 1601235284054, 'withdraw_money', 332);
INSERT INTO public.operation_log (id, card_id, date, operation_code, withdrew_money) VALUES (9, 3, 1601235299955, 'show_balance', null);
INSERT INTO public.operation_log (id, card_id, date, operation_code, withdrew_money) VALUES (10, 3, 1601235765529, 'withdraw_money', 2000);
INSERT INTO public.operation_log (id, card_id, date, operation_code, withdrew_money) VALUES (11, 4, 1601236236689, 'withdraw_money', 2000);
INSERT INTO public.operation_log (id, card_id, date, operation_code, withdrew_money) VALUES (12, 3, 1601237583745, 'show_balance', null);
INSERT INTO public.operation_log (id, card_id, date, operation_code, withdrew_money) VALUES (13, 4, 1601237643240, 'withdraw_money', 2000);
INSERT INTO public.operation_log (id, card_id, date, operation_code, withdrew_money) VALUES (14, 4, 1601237652548, 'withdraw_money', 20007);
INSERT INTO public.operation_log (id, card_id, date, operation_code, withdrew_money) VALUES (15, 3, 1601238458629, 'show_balance', null);
INSERT INTO public.operation_log (id, card_id, date, operation_code, withdrew_money) VALUES (16, 4, 1601238499673, 'withdraw_money', 20007);