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
@hsqldb:AUTNM varchar(24) foreign key references AUTH001(AUTNM),
@mssql:AUTNM varchar(24) foreign key references AUTH001(AUTNM),
@mysql:AUTNM varchar(24) references AUTH001(AUTNM),
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
@hsqldb:PRFNM varchar(12) foreign key references AUTH003(PRFNM),
@mssql:PRFNM varchar(12) foreign key references AUTH003(PRFNM),
@mysql:PRFNM varchar(12) references AUTH003(PRFNM),
@hsqldb:AUTNM varchar(24) foreign key references AUTH001(AUTNM),
@mssql:AUTNM varchar(24) foreign key references AUTH001(AUTNM),
@mysql:AUTNM varchar(24) references AUTH001(AUTNM),
   OBJCT varchar(12),
   ACTIO varchar(12)
);

create table USERS002(
   IDENT numeric(8) primary key,
@hsqldb:UNAME varchar(12) foreign key references USERS001(UNAME),
@mssql:UNAME varchar(12) foreign key references USERS001(UNAME),
@mysql:UNAME varchar(12) references USERS001(UNAME),
@hsqldb:PRFNM varchar(12) foreign key references AUTH003(PRFNM)
@mssql:PRFNM varchar(12) foreign key references AUTH003(PRFNM)
@mysql:PRFNM varchar(12) references AUTH003(PRFNM)
);

insert into USERS001(uname, secrt, fname, sname, usrid) values('ADMIN', 'iocaste', 'Administrator', '', 1);
insert into AUTH001(autnm, objct, actio, autid) values('PACKAGE.EXECUTE', 'APPLICATION', 'EXECUTE', 1);
insert into AUTH002(ident, autnm, param, value) values(101, 'PACKAGE.EXECUTE', 'APPNAME', 'iocaste-packagetool');

insert into AUTH001(autnm, objct, actio, autid) values('TASKSEL.EXECUTE', 'APPLICATION', 'EXECUTE', 2);
insert into AUTH002(ident, autnm, param, value) values(201, 'TASKSEL.EXECUTE', 'APPNAME', 'iocaste-tasksel');

insert into AUTH001(autnm, objct, actio, autid) values('SH.EXECUTE', 'APPLICATION', 'EXECUTE', 3);
insert into AUTH002(ident, autnm, param, value) values(301, 'SH.EXECUTE', 'APPNAME', 'iocaste-search-help');

insert into AUTH001(autnm, objct, actio, autid) values('HELP.EXECUTE', 'APPLICATION', 'EXECUTE', 4);
insert into AUTH002(ident, autnm, param, value) values(401, 'HELP.EXECUTE', 'APPNAME', 'iocaste-help');

insert into AUTH001(autnm, objct, actio, autid) values('EXHANDLER.EXECUTE', 'APPLICATION', 'EXECUTE', 5);
insert into AUTH002(ident, autnm, param, value) values(501, 'EXHANDLER.EXECUTE', 'APPNAME', 'iocaste-exhandler');

insert into AUTH003(prfnm, prfid, crrnt) values('ALL', 1, 101);
insert into AUTH004(ident, prfnm, autnm, objct, actio) values(101, 'ALL', 'PACKAGE.EXECUTE', 'APPLICATION', 'EXECUTE');

insert into AUTH003(prfnm, prfid, crrnt) values('BASE', 2, 204);
insert into AUTH004(ident, prfnm, autnm, objct, actio) values(201, 'BASE', 'TASKSEL.EXECUTE', 'APPLICATION', 'EXECUTE');
insert into AUTH004(ident, prfnm, autnm, objct, actio) values(202, 'BASE', 'SH.EXECUTE', 'APPLICATION', 'EXECUTE');
insert into AUTH004(ident, prfnm, autnm, objct, actio) values(203, 'BASE', 'HELP.EXECUTE', 'APPLICATION', 'EXECUTE');
insert into AUTH004(ident, prfnm, autnm, objct, actio) values(204, 'BASE', 'EXHANDLER.EXECUTE', 'APPLICATION', 'EXECUTE');

insert into USERS002(ident, uname, prfnm) values(101, 'ADMIN', 'ALL');
insert into USERS002(ident, uname, prfnm) values(102, 'ADMIN', 'BASE');
