/* tokens '\c e \p nao sao aceitos. nao existe parametro 'if exists' para 'drop user' */
create user iocastedb password initial;
\p user generated.

drop table users003 if exists;
drop table users002 if exists;
drop table users001 if exists;
\p core tables dropped.

/* usuários */
create table users001 (
   uname varchar(12) primary key,
   secrt varchar(12),
   fname varchar(64),
   sname varchar(64),
   usrid numeric(6)
);

create table users002 (
   ident numeric(9) primary key,
   uname varchar(12) foreign key references users001(uname),
   autnm varchar(24)
);

create table users003 (
   ident numeric(12) primary key,
   autid numeric(9) foreign key references users002(ident),
   param varchar(12),
   value varchar(64)
);

\p core tables generated.

grant select, insert, update, delete on users001 to iocastedb;
grant select, insert, update, delete on users002 to iocastedb;
grant select, insert, update, delete on users003 to iocastedb;
\p permissions granted.

insert into users001(uname, secrt, fname, sname, usrid) values('ADMIN', 'iocaste', 'Administrator', '', 1);
insert into users002(ident, uname, autnm) values(1001, 'ADMIN', 'APPLICATION.EXECUTE');
insert into users003(ident, autid, param, value) values(1001001, 1001, 'APPNAME', 'iocaste-tasksel');
insert into users002(ident, uname, autnm) values(1002, 'ADMIN', 'APPLICATION.EXECUTE');
insert into users003(ident, autid, param, value) values(1002001, 1002, 'APPNAME', 'iocaste-packagetool');
\p initial configuration saved.

commit work;

