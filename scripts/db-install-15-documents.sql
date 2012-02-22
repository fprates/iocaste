/* tokens \c e \p nao sao aceitos. nao existe parametro 'if exists' para 'drop user' */
drop table range001 if exists;
drop table docs004 if exists;
drop table docs003 if exists;
drop table docs002 if exists;
drop table docs001 if exists;
\p document tables dropped.

/* range numérico */
create table range001 (
   ident varchar(12) primary key,
   crrnt numeric(12)
);

/* documentos */
create table docs001 (
   docid varchar(12) primary key,
   tname varchar(12),
   class varchar(255)
);

/* elementos de dados */
create table docs003 (
   ename varchar(36) primary key,
   decim numeric(2),
   lngth numeric(4),
   etype numeric(1),
   upcas bit
);

/* itens de documento */
create table docs002 (
   iname varchar(36) primary key,
   docid varchar(12) foreign key references docs001(docid),
   index numeric(3),
   fname varchar(12),
   ename varchar(36),
   attrb varchar(64)
);

/* chaves do documento */
create table docs004 (
   iname varchar(36) primary key,
   docid varchar(12) foreign key references docs001(docid)
);
\p document tables has been generated

grant select, insert, update, delete on range001 to iocastedb;
grant select, insert, update, delete on docs001 to iocastedb;
grant select, insert, update, delete on docs002 to iocastedb;
grant select, insert, update, delete on docs003 to iocastedb;
grant select, insert, update, delete on docs004 to iocastedb;
\p permissions granted.

insert into docs001(docid, tname, class) values('MODEL', 'DOCS001', 'org.iocaste.documents.common.DocumentModel');
insert into docs002(iname, docid, index, fname, ename, attrb) values('MODEL.NAME', 'MODEL', 0, 'DOCID', 'MODEL.NAME', 'name');
insert into docs002(iname, docid, index, fname, ename, attrb) values('MODEL.TABLE', 'MODEL', 1, 'TNAME', 'MODEL.TABLE', 'tableName');
insert into docs002(iname, docid, index, fname, ename, attrb) values('MODEL.CLASS', 'MODEL', 2, 'CLASS', 'MODEL.CLASS', 'className');
insert into docs003(ename, decim, lngth, etype, upcas) values('MODEL.NAME', 0, 12, 0, 1);
insert into docs003(ename, decim, lngth, etype, upcas) values('MODEL.TABLE', 0, 12, 0, 1);
insert into docs003(ename, decim, lngth, etype, upcas) values('MODEL.CLASS', 0, 255, 0, 0);
insert into docs004(iname, docid) values('MODEL.NAME', 'MODEL');

insert into docs001(docid, tname, class) values('MODELITEM', 'DOCS002', 'org.iocaste.documents.common.DocumentModelItem');
insert into docs002(iname, docid, index, fname, ename, attrb) values('MODELITEM.NAME', 'MODELITEM', 0, 'INAME', 'MODELITEM.NAME', 'name');
insert into docs002(iname, docid, index, fname, ename, attrb) values('MODELITEM.MODEL', 'MODELITEM', 1, 'DOCID', 'MODEL.NAME', 'documentModel');
insert into docs002(iname, docid, index, fname, ename, attrb) values('MODELITEM.INDEX', 'MODELITEM', 2, 'INDEX', 'MODELITEM.INDEX', 'index');
insert into docs002(iname, docid, index, fname, ename, attrb) values('MODELITEM.FIELDNAME', 'MODELITEM', 3, 'FNAME', 'MODELITEM.FIELDNAME', 'tableFieldName');
insert into docs002(iname, docid, index, fname, ename, attrb) values('MODELITEM.ELEMENT', 'MODELITEM', 4, 'ENAME', 'DATAELEMENT.NAME', 'dataElement');
insert into docs002(iname, docid, index, fname, ename, attrb) values('MODELITEM.ATTRIB', 'MODELITEM', 5, 'ATTRB', 'MODELITEM.ATTRIB', 'attributeName');
insert into docs003(ename, decim, lngth, etype, upcas) values('MODELITEM.NAME', 0, 24, 0, 1);
insert into docs003(ename, decim, lngth, etype, upcas) values('MODELITEM.INDEX', 1, 3, 3, 0);
insert into docs003(ename, decim, lngth, etype, upcas) values('MODELITEM.FIELDNAME', 0, 12, 0, 1);
insert into docs003(ename, decim, lngth, etype, upcas) values('MODELITEM.ATTRIB', 0, 64, 0, 0);
insert into docs004(iname, docid) values('MODELITEM.NAME', 'MODELITEM');

insert into docs001(docid, tname, class) values('DATAELEMENT', 'DOCS003', 'org.iocaste.documents.common.DataElement');
insert into docs002(iname, docid, index, fname, ename, attrb) values('DATAELEMENT.NAME', 'DATAELEMENT', 0, 'ENAME', 'DATAELEMENT.NAME', 'name');
insert into docs002(iname, docid, index, fname, ename, attrb) values('DATAELEMENT.DECIMALS', 'DATAELEMENT', 1, 'DECIM', 'DATAELEMENT.DECIMALS', 'decimals');
insert into docs002(iname, docid, index, fname, ename, attrb) values('DATAELEMENT.LENGTH', 'DATAELEMENT', 2, 'LNGTH', 'DATAELEMENT.LENGTH', 'length');
insert into docs002(iname, docid, index, fname, ename, attrb) values('DATAELEMENT.TYPE', 'DATAELEMENT', 3, 'ETYPE', 'DATAELEMENT.TYPE', 'type');
insert into docs002(iname, docid, index, fname, ename, attrb) values('DATAELEMENT.UPCASE', 'DATAELEMENT', 4, 'UPCAS', 'DATAELEMENT.UPCASE', 'upcase');
insert into docs003(ename, decim, lngth, etype, upcas) values('DATAELEMENT.NAME', 0, 36, 0, 1);
insert into docs003(ename, decim, lngth, etype, upcas) values('DATAELEMENT.DECIMALS', 0, 2, 3, 0);
insert into docs003(ename, decim, lngth, etype, upcas) values('DATAELEMENT.LENGTH', 0, 4, 3, 0);
insert into docs003(ename, decim, lngth, etype, upcas) values('DATAELEMENT.TYPE', 0, 1, 3, 0);
insert into docs003(ename, decim, lngth, etype, upcas) values('DATAELEMENT.UPCASE', 0, 1, 1, 0);
insert into docs004(iname, docid) values('DATAELEMENT.NAME', 'DATAELEMENT');

insert into docs001(docid, tname, class) values('NUMBER_RANGE', 'RANGE001', '');
insert into docs002(iname, docid, index, fname, ename, attrb) values('NUMBER_RANGE.IDENT', 'NUMBER_RANGE', 0, 'IDENT', 'NUMBER_RANGE.IDENT', '');
insert into docs002(iname, docid, index, fname, ename, attrb) values('NUMBER_RANGE.CURRENT', 'NUMBER_RANGE', 1, 'CRRNT', 'NUMBER_RANGE.CURRENT', '');
insert into docs003(ename, decim, lngth, etype, upcas) values('NUMBER_RANGE.IDENT', 0, 12, 0, 1);
insert into docs003(ename, decim, lngth, etype, upcas) values('NUMBER_RANGE.CURRENT', 0, 12, 3, 0);
insert into docs004(iname, docid) values('NUMBER_RANGE.IDENT', 'NUMBER_RANGE');

commit work;

