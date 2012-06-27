create table SHCAB (
    IDENT varchar(24) primary key,
@hsqldb:DOCID varchar(24) foreign key references DOCS001(DOCID),
@mssql:DOCID varchar(24) foreign key references DOCS001(DOCID),
@mysql:DOCID varchar(24) references DOCS001(DOCID),
@hsqldb:EXPRT varchar(48) foreign key references DOCS002(INAME)
@mssql:EXPRT varchar(48) foreign key references DOCS002(INAME)
@mysql:EXPRT varchar(48) references DOCS002(INAME)
);

create table SHITM (
    INAME varchar(48) primary key,
@hsqldb:SHCAB varchar(24) foreign key references SHCAB(IDENT),
@mssql:SHCAB varchar(24) foreign key references SHCAB(IDENT),
@mysql:SHCAB varchar(24) references SHCAB(IDENT),
@hsqldb:MDITM varchar(48) foreign key references DOCS002(INAME)
@mssql:MDITM varchar(48) foreign key references DOCS002(INAME)
@mysql:MDITM varchar(48) references DOCS002(INAME)
);

create table SHREF (
    INAME varchar(48) primary key,
@hsqldb:SHCAB varchar(24) foreign key references SHCAB(IDENT)
@mssql:SHCAB varchar(24) foreign key references SHCAB(IDENT)
@mysql:SHCAB varchar(24) references SHCAB(IDENT)
);

insert into DOCS001(docid, tname, class) values('SEARCH_HELP', 'SHCAB', '');
insert into DOCS003(ename, decim, lngth, etype, upcas) values('SEARCH_HELP.NAME', 0, 12, 0, 1);
insert into DOCS002(iname, docid, nritm, fname, ename, attrb) values('SEARCH_HELP.NAME', 'SEARCH_HELP', 0, 'IDENT', 'SEARCH_HELP.NAME', 'name');
insert into DOCS002(iname, docid, nritm, fname, ename, attrb, itref) values('SEARCH_HELP.MODEL', 'SEARCH_HELP', 1, 'DOCID', 'MODEL.NAME', 'model', 'MODEL.NAME');
insert into DOCS002(iname, docid, nritm, fname, ename, attrb, itref) values('SEARCH_HELP.EXPORT', 'SEARCH_HELP', 2, 'EXPRT', 'MODELITEM.NAME', 'export', 'MODELITEM.NAME');
insert into DOCS004(iname, docid) values('SEARCH_HELP.NAME', 'SEARCH_HELP');
insert into DOCS005(tname, docid) values('SHCAB', 'SEARCH_HELP');
insert into DOCS006(iname, itref) values('SEARCH_HELP.MODEL', 'MODEL.NAME');
insert into DOCS006(iname, itref) values('SEARCH_HELP.EXPORT', 'MODELITEM.NAME');

insert into DOCS001(docid, tname, class) values('SH_ITENS', 'SHITM', '');
insert into DOCS003(ename, decim, lngth, etype, upcas) values('SH_ITENS.NAME', 0, 48, 0, 1);
insert into DOCS002(iname, docid, nritm, fname, ename, attrb) values('SH_ITENS.NAME', 'SH_ITENS', 0, 'INAME', 'SH_ITENS.NAME', 'name');
insert into DOCS002(iname, docid, nritm, fname, ename, attrb, itref) values('SH_ITENS.SEARCH_HELP', 'SH_ITENS', 1, 'SHCAB', 'SEARCH_HELP.NAME', 'searchHelp', 'SEARCH_HELP.NAME');
insert into DOCS002(iname, docid, nritm, fname, ename, attrb, itref) values('SH_ITENS.ITEM', 'SH_ITENS', 2, 'MDITM', 'MODELITEM.NAME', 'modelItem', 'MODELITEM.NAME');
insert into DOCS004(iname, docid) values('SH_ITENS.NAME', 'SH_ITENS');
insert into DOCS005(tname, docid) values('SHITM', 'SH_ITENS');
insert into DOCS006(iname, itref) values('SH_ITENS.SEARCH_HELP', 'SEARCH_HELP.NAME');
insert into DOCS006(iname, itref) values('SH_ITENS.ITEM', 'MODELITEM.NAME');

insert into DOCS001(docid, tname, class) values('SH_REFERENCE', 'SHREF', '');
insert into DOCS002(iname, docid, nritm, fname, ename, attrb) values('SH_REFERENCE.MODEL_ITEM', 'SH_REFERENCE', 0, 'INAME', 'MODELITEM.NAME', 'modelItem');
insert into DOCS002(iname, docid, nritm, fname, ename, attrb, itref) values('SH_REFERENCE.SEARCH_HELP', 'SH_REFERENCE', 1, 'SHCAB', 'SEARCH_HELP.NAME', 'searchHelp', 'SEARCH_HELP.NAME');
insert into DOCS004(iname, docid) values('SH_REFERENCE.MODEL_ITEM', 'SH_REFERENCE');
insert into DOCS005(tname, docid) values('SHREF', 'SH_REFERENCE');
insert into DOCS006(iname, itref) values('SH_REFERENCE.SEARCH_HELP', 'SEARCH_HELP.NAME');
