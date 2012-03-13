/* tokens \c e \p nao sao aceitos. nao existe parametro 'if exists' para 'drop user' */
drop table shcab if exists;
\p sh tables dropped.

create table shcab (
    ident varchar(12) primary key
);
\p sh tables has been generated

grant select, insert, update, delete on shcab to iocastedb;
\p permissions granted.

insert into docs001(docid, tname, class) values('SEARCH_HELP', 'SHCAB', '');
insert into docs002(iname, docid, index, fname, ename, attrb) values('SEARCH_HELP.NAME', 'SEARCH_HELP', 0, 'IDENT', 'SEARCH_HELP.NAME', 'name');
insert into docs003(ename, decim, lngth, etype, upcas) values('SEARCH_HELP.NAME', 0, 12, 0, 1);
insert into docs004(iname, docid) values('SEARCH_HELP.NAME', 'SEARCH_HELP');
insert into docs005(tname, docid) values('SHCAB', 'SEARCH_HELP');

commit work;