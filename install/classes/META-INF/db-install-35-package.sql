create table PACKAGE001 (
    IDENT varchar(60) primary key,
    PKGID numeric(12)
);

create table PACKAGE002 (
    IDENT numeric(12) primary key,
@hsqldb:PACKG varchar(60) foreign key references PACKAGE001(IDENT),
@mssql:PACKG varchar(60) foreign key references PACKAGE001(IDENT),
@mysql:PACKG varchar(60) references PACKAGE001(IDENT),
    INAME varchar(60),
    MODEL varchar(24)
);

insert into DOCS001(docid, tname, class) values('PACKAGE', 'PACKAGE001', '');
insert into DOCS003(ename, decim, lngth, etype, upcas) values('PACKAGE.NAME', 0, 60, 0, 0);
insert into DOCS003(ename, decim, lngth, etype, upcas) values('PACKAGE.CODE', 0, 12, 3, 0);
insert into DOCS002(iname, docid, nritm, fname, ename, attrb) values('PACKAGE.NAME', 'PACKAGE', 0, 'IDENT', 'PACKAGE.NAME', '');
insert into DOCS002(iname, docid, nritm, fname, ename, attrb) values('PACKAGE.CODE', 'PACKAGE', 1, 'PKGID', 'PACKAGE.CODE', '');
insert into DOCS004(iname, docid) values('PACKAGE.NAME', 'PACKAGE');
insert into DOCS005(tname, docid) values('PACKAGE001', 'PACKAGE');

insert into DOCS001(docid, tname, class) values('PACKAGE_ITEM', 'PACKAGE002', '');
insert into DOCS003(ename, decim, lngth, etype, upcas) values('PACKAGE_ITEM.NAME', 0, 60, 0, 1);
insert into DOCS003(ename, decim, lngth, etype, upcas) values('PACKAGE_ITEM.MODEL', 0, 24, 0, 1);
insert into DOCS002(iname, docid, nritm, fname, ename, attrb) values('PACKAGE_ITEM.CODE', 'PACKAGE_ITEM', 0, 'IDENT', 'PACKAGE.CODE', '');
insert into DOCS002(iname, docid, nritm, fname, ename, attrb) values('PACKAGE_ITEM.PACKAGE', 'PACKAGE_ITEM', 1, 'PACKG', 'PACKAGE.NAME', '');
insert into DOCS002(iname, docid, nritm, fname, ename, attrb) values('PACKAGE_ITEM.NAME', 'PACKAGE_ITEM', 2, 'INAME', 'PACKAGE_ITEM.NAME', '');
insert into DOCS002(iname, docid, nritm, fname, ename, attrb) values('PACKAGE_ITEM.MODEL', 'PACKAGE_ITEM', 3, 'MODEL', 'PACKAGE_ITEM.MODEL', '');
insert into DOCS004(iname, docid) values('PACKAGE_ITEM.CODE', 'PACKAGE_ITEM');
insert into DOCS005(tname, docid) values('PACKAGE002', 'PACKAGE_ITEM');

insert into RANGE001(ident, crrnt) values('PKGCODE', 0);
