/* tokens \c e \p nao sao aceitos. nao existe parametro 'if exists' para 'drop user' */

create table shcab (
    ident varchar(24) primary key,
    docid varchar(24) foreign key references docs001(docid)
);

create table shitm (
    iname varchar(48) primary key,
    shcab varchar(24) foreign key references shcab(ident),
    mditm varchar(48) foreign key references docs002(iname)
);

\p sh tables has been generated

grant select, insert, update, delete on shcab to iocastedb;
grant select, insert, update, delete on shitm to iocastedb;
\p permissions granted.

insert into docs001(docid, tname, class) values('SEARCH_HELP', 'SHCAB', '');
insert into docs002(iname, docid, index, fname, ename, attrb) values('SEARCH_HELP.NAME', 'SEARCH_HELP', 0, 'IDENT', 'SEARCH_HELP.NAME', 'name');
insert into docs002(iname, docid, index, fname, ename, attrb, mdref, itref) values('SEARCH_HELP.MODEL', 'SEARCH_HELP', 1, 'MODEL', 'MODEL.NAME', 'model', 'MODEL', 'NAME');
insert into docs003(ename, decim, lngth, etype, upcas) values('SEARCH_HELP.NAME', 0, 12, 0, 1);
insert into docs004(iname, docid) values('SEARCH_HELP.NAME', 'SEARCH_HELP');
insert into docs005(tname, docid) values('SHCAB', 'SEARCH_HELP');

insert into docs001(docid, tname, class) values('SH_ITENS', 'SHITM', '');
insert into docs002(iname, docid, index, fname, ename, attrb) values('SH_ITENS.NAME', 'SH_ITENS', 0, 'INAME', 'SH_ITENS.NAME', 'name');
insert into docs002(iname, docid, index, fname, ename, attrb, mdref, itref) values('SH_ITENS.SEARCH_HELP', 'SH_ITENS', 1, 'SHCAB', 'SEARCH_HELP.NAME', 'searchHelp', 'SEARCH_HELP', 'NAME');
insert into docs002(iname, docid, index, fname, ename, attrb, mdref, itref) values('SH_ITENS.ITEM', 'SH_ITENS', 2, 'MDITEM', 'MODELITEM.NAME', 'modelItem', 'MODELITEM', 'NAME');
insert into docs003(ename, decim, lngth, etype, upcas) values('SH_ITENS.NAME', 0, 48, 0, 1);
insert into docs004(iname, docid) values('SH_ITENS.NAME', 'SH_ITENS');
insert into docs005(tname, docid) values('SHITM', 'SH_ITENS');

commit work;