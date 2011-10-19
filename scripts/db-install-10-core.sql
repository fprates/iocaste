/* tokens '\c e \p nao sao aceitos. nao existe parametro 'if exists' para 'drop user' */
\c true
drop user iocastedb;
\c false
create user iocastedb password initial;
\p user generated.

drop table users001 if exists;
\p core tables dropped.

/* usuários */
create table users001 (
   uname varchar(12) primary key,
   secrt varchar(12),
   fname varchar(64),
   sname varchar(64)
);

\p core tables generated.

grant select, insert, update, delete on users001 to iocastedb;
\p permissions granted.

insert into users001 (uname, secrt, fname, sname) values ('ADMIN', 'iocaste', 'Administrator', '');
\p initial configuration saved.

commit work;

