/* tokens \c e \p nao sao aceitos. nao existe parametro 'if exists' para 'drop user' */

/* pacotes instalados */
create table package001 (
    ident varchar(60) primary key,
    pkgid numeric(12)
);

create table package002 (
    ident numeric(12) primary key,
    packg varchar(60) foreign key references package001(ident),
    iname varchar(60),
    model varchar(24),
);

\p package tables has been generated

grant select, insert, update, delete on package001 to iocastedb;
grant select, insert, update, delete on package002 to iocastedb;
\p permissions granted.

insert into docs001(docid, tname, class) values('PACKAGE', 'PACKAGE001', '');
insert into docs003(ename, decim, lngth, etype, upcas) values('PACKAGE.NAME', 0, 60, 0, 1);
insert into docs003(ename, decim, lngth, etype, upcas) values('PACKAGE.CODE', 0, 12, 3, 0);
insert into docs002(iname, docid, index, fname, ename, attrb) values('PACKAGE.NAME', 'PACKAGE', 0, 'IDENT', 'PACKAGE.NAME', '');
insert into docs002(iname, docid, index, fname, ename, attrb) values('PACKAGE.CODE', 'PACKAGE', 1, 'PKGID', 'PACKAGE.CODE', '');
insert into docs004(iname, docid) values('PACKAGE.NAME', 'PACKAGE');
insert into docs005(tname, docid) values('PACKAGE001', 'PACKAGE');

insert into docs001(docid, tname, class) values('PACKAGE_ITEM', 'PACKAGE002', '');
insert into docs003(ename, decim, lngth, etype, upcas) values('PACKAGE_ITEM.NAME', 0, 60, 0, 1);
insert into docs003(ename, decim, lngth, etype, upcas) values('PACKAGE_ITEM.MODEL', 0, 24, 0, 1);
insert into docs002(iname, docid, index, fname, ename, attrb) values('PACKAGE_ITEM.CODE', 'PACKAGE_ITEM', 0, 'IDENT', 'PACKAGE.CODE', '');
insert into docs002(iname, docid, index, fname, ename, attrb) values('PACKAGE_ITEM.PACKAGE', 'PACKAGE_ITEM', 1, 'PACKG', 'PACKAGE.NAME', '');
insert into docs002(iname, docid, index, fname, ename, attrb) values('PACKAGE_ITEM.NAME', 'PACKAGE_ITEM', 2, 'INAME', 'PACKAGE_ITEM.NAME', '');
insert into docs002(iname, docid, index, fname, ename, attrb) values('PACKAGE_ITEM.MODEL', 'PACKAGE_ITEM', 3, 'MODEL', 'PACKAGE_ITEM.MODEL', '');
insert into docs004(iname, docid) values('PACKAGE_ITEM.CODE', 'PACKAGE_ITEM');
insert into docs005(tname, docid) values('PACKAGE002', 'PACKAGE_ITEM');

insert into range001(ident, crrnt) values('PKGCODE', 0);

commit work;

