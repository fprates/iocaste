create table USERS001 (
   UNAME varchar(12) primary key,
   SECRT varchar(12),
   FNAME varchar(64),
   SNAME varchar(64),
   USRID numeric(5)
);

create table AUTH001 (
   AUTNM varchar(24) primary key,
   OBJCT varchar(12),
   ACTIO varchar(12),
   AUTID numeric(5)
);

create table AUTH002 (
   IDENT numeric(8) primary key,
   AUTNM varchar(24) foreign key references auth001(autnm),
   PARAM varchar(12),
   VALUE varchar(64)
);

create table AUTH003 (
   PRFNM varchar(12) primary key,
   PRFID numeric(5),
   CRRNT numeric(8)
);

create table AUTH004 (
   IDENT numeric(8) primary key,
   PRFNM varchar(12) foreign key references auth003(prfnm),
   AUTNM varchar(24) foreign key references auth001(autnm),
   OBJCT varchar(12),
   ACTIO varchar(12)
);

create table USERS002(
   IDENT numeric(8) primary key,
   UNAME varchar(12) foreign key references users001(uname),
   PRFNM varchar(12) foreign key references auth003(prfnm)
);

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
