/* tokens '\c e \p nao sao aceitos. nao existe parametro 'if exists' para 'drop user' */
create user iocastedb password initial;
\p user generated.

drop table users001 if exists;
drop table authorobj if exists;
drop table authoract if exists;
drop table authorpar if exists;
\p core tables dropped.

/* usuários */
create table users001 (
   uname varchar(12) primary key,
   secrt varchar(12),
   fname varchar(64),
   sname varchar(64)
);

create table authorobj (
   objct varchar(24) primary key,
   objid numeric(5)
);

create table authoract (
   actid numeric(8) primary key,
   objid numeric(5),
   action varchar(12)
);

create table authorpar (
   parid numeric(10) primary key,
   actid numeric(8) foreign key references authoract(actid),
   param varchar(12)
);
\p core tables generated.

grant select, insert, update, delete on users001 to iocastedb;
grant select, insert, update, delete on authorobj to iocastedb;
grant select, insert, update, delete on authoract to iocastedb;
grant select, insert, update, delete on authorpar to iocastedb;
\p permissions granted.

insert into users001 (uname, secrt, fname, sname) values ('ADMIN', 'iocaste', 'Administrator', '');
insert into authorobj (objct, objid) values ('APPLICATION', 1);
insert into authoract (actid, objid, action) values (1001, 1, 'EXECUTE');
insert into authorpar (parid, actid, param) values (1001001, 1001, 'APPNAME');
insert into authorpar (parid, actid, param) values (1001002, 1001, 'PAGENAME');
\p initial configuration saved.

commit work;

