/* tokens '\c e \p nao sao aceitos. nao existe parametro 'if exists' para 'drop user' */
create user iocastedb password initial;
\p user generated.

drop table users002 if exists;
drop table auth004 if exists;
drop table auth003 if exists;
drop table auth002 if exists;
drop table auth001 if exists;
drop table users001 if exists;
\p core tables dropped.

/* usuários */
create table users001 (
   uname varchar(12) primary key,
   secrt varchar(12),
   fname varchar(64),
   sname varchar(64),
   usrid numeric(5)
);

/* autorizações */
create table auth001 (
   autnm varchar(24) primary key,
   objct varchar(12),
   actio varchar(12),
   autid numeric(5)
);

/* parâmetros da autorização */
create table auth002 (
   ident numeric(8) primary key,
   autnm varchar(24) foreign key references auth001(autnm),
   param varchar(12),
   value varchar(64)
);

/* perfil de autorização */
create table auth003 (
   prfnm varchar(12) primary key,
   prfid numeric(5),
   crrnt numeric(8)
);

/* autorizações do perfil */
create table auth004 (
   ident numeric(8) primary key,
   prfnm varchar(12) foreign key references auth003(prfnm),
   autnm varchar(24) foreign key references auth001(autnm),
   objct varchar(12),
   actio varchar(12)
);

create table users002(
   ident numeric(8) primary key,
   uname varchar(12) foreign key references users001(uname),
   prfnm varchar(12) foreign key references auth003(prfnm)
);

\p core tables generated.

grant select, insert, update, delete on users001 to iocastedb;
grant select, insert, update, delete on users002 to iocastedb;
grant select, insert, update, delete on auth001 to iocastedb;
grant select, insert, update, delete on auth002 to iocastedb;
grant select, insert, update, delete on auth003 to iocastedb;
grant select, insert, update, delete on auth004 to iocastedb;
\p permissions granted.

insert into users001(uname, secrt, fname, sname, usrid) values('ADMIN', 'iocaste', 'Administrator', '', 1);
insert into auth001(autnm, objct, actio, autid) values('PACKAGE.EXECUTE', 'APPLICATION', 'EXECUTE', 1);
insert into auth002(ident, autnm, param, value) values(101, 'PACKAGE.EXECUTE', 'APPNAME', 'iocaste-packagetool');

insert into auth001(autnm, objct, actio, autid) values('TASKSEL.EXECUTE', 'APPLICATION', 'EXECUTE', 2);
insert into auth002(ident, autnm, param, value) values(201, 'TASKSEL.EXECUTE', 'APPNAME', 'iocaste-tasksel');

insert into auth001(autnm, objct, actio, autid) values('SH.EXECUTE', 'APPLICATION', 'EXECUTE', 3);
insert into auth002(ident, autnm, param, value) values(301, 'SH.EXECUTE', 'APPNAME', 'iocaste-search-help');

insert into auth001(autnm, objct, actio, autid) values('HELP.EXECUTE', 'APPLICATION', 'EXECUTE', 4);
insert into auth002(ident, autnm, param, value) values(401, 'HELP.EXECUTE', 'APPNAME', 'iocaste-help');

insert into auth001(autnm, objct, actio, autid) values('EXHANDLER.EXECUTE', 'APPLICATION', 'EXECUTE', 5);
insert into auth002(ident, autnm, param, value) values(501, 'EXHANDLER.EXECUTE', 'APPNAME', 'iocaste-exhandler');

insert into auth003(prfnm, prfid, crrnt) values('ALL', 1, 101);
insert into auth004(ident, prfnm, autnm, objct, actio) values(101, 'ALL', 'PACKAGE.EXECUTE', 'APPLICATION', 'EXECUTE');

insert into auth003(prfnm, prfid, crrnt) values('BASE', 2, 204);
insert into auth004(ident, prfnm, autnm, objct, actio) values(201, 'BASE', 'TASKSEL.EXECUTE', 'APPLICATION', 'EXECUTE');
insert into auth004(ident, prfnm, autnm, objct, actio) values(202, 'BASE', 'SH.EXECUTE', 'APPLICATION', 'EXECUTE');
insert into auth004(ident, prfnm, autnm, objct, actio) values(203, 'BASE', 'HELP.EXECUTE', 'APPLICATION', 'EXECUTE');
insert into auth004(ident, prfnm, autnm, objct, actio) values(204, 'BASE', 'EXHANDLER.EXECUTE', 'APPLICATION', 'EXECUTE');

insert into users002(ident, uname, prfnm) values(101, 'ADMIN', 'ALL');
insert into users002(ident, uname, prfnm) values(102, 'ADMIN', 'BASE');

\p initial configuration saved.

commit work;

