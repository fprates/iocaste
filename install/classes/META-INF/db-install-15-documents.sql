create table RANGE001 (
   IDENT varchar(12) primary key,
   CRRNT numeric(12)
);

create table DOCS001 (
   DOCID varchar(24) primary key,
   TNAME varchar(12),
   CLASS varchar(255)
);

create table DOCS003 (
   ENAME varchar(48) primary key,
   DECIM numeric(2),
   LNGTH numeric(4),
   ETYPE numeric(1),
   UPCAS bit
);

create table DOCS002 (
   INAME varchar(48) primary key,
   DOCID varchar(24) foreign key references docs001(docid),
   NRITM numeric(3),
   FNAME varchar(12),
   ENAME varchar(48) foreign key references docs003(ename),
   ATTRB varchar(64),
   ITREF varchar(48)
);

create table DOCS004 (
   INAME varchar(48) primary key,
   DOCID varchar(24) foreign key references docs001(docid)
);

create table DOCS005 (
   TNAME varchar(12) primary key,
   DOCID varchar(24) foreign key references docs001(docid)
);

create table DOCS006 (
   INAME varchar(48) primary key,
   ITREF varchar(48) foreign key references docs002(iname)
);

insert into docs001(docid, tname, class) values('MODEL', 'DOCS001', 'org.iocaste.documents.common.DocumentModel');
insert into docs003(ename, decim, lngth, etype, upcas) values('MODEL.NAME', 0, 24, 0, 1);
insert into docs003(ename, decim, lngth, etype, upcas) values('MODEL.TABLE', 0, 12, 0, 1);
insert into docs003(ename, decim, lngth, etype, upcas) values('MODEL.CLASS', 0, 255, 0, 0);
insert into docs002(iname, docid, nritm, fname, ename, attrb) values('MODEL.NAME', 'MODEL', 0, 'DOCID', 'MODEL.NAME', 'name');
insert into docs002(iname, docid, nritm, fname, ename, attrb) values('MODEL.TABLE', 'MODEL', 1, 'TNAME', 'MODEL.TABLE', 'tableName');
insert into docs002(iname, docid, nritm, fname, ename, attrb) values('MODEL.CLASS', 'MODEL', 2, 'CLASS', 'MODEL.CLASS', 'className');
insert into docs004(iname, docid) values('MODEL.NAME', 'MODEL');
insert into docs005(tname, docid) values('DOCS001', 'MODEL');

insert into docs001(docid, tname, class) values('DATAELEMENT', 'DOCS003', 'org.iocaste.documents.common.DataElement');
insert into docs003(ename, decim, lngth, etype, upcas) values('DATAELEMENT.NAME', 0, 48, 0, 1);
insert into docs003(ename, decim, lngth, etype, upcas) values('DATAELEMENT.DECIMALS', 0, 2, 3, 0);
insert into docs003(ename, decim, lngth, etype, upcas) values('DATAELEMENT.LENGTH', 0, 4, 3, 0);
insert into docs003(ename, decim, lngth, etype, upcas) values('DATAELEMENT.TYPE', 0, 1, 3, 0);
insert into docs003(ename, decim, lngth, etype, upcas) values('DATAELEMENT.UPCASE', 0, 1, 5, 0);
insert into docs002(iname, docid, nritm, fname, ename, attrb) values('DATAELEMENT.NAME', 'DATAELEMENT', 0, 'ENAME', 'DATAELEMENT.NAME', 'name');
insert into docs002(iname, docid, nritm, fname, ename, attrb) values('DATAELEMENT.DECIMALS', 'DATAELEMENT', 1, 'DECIM', 'DATAELEMENT.DECIMALS', 'decimals');
insert into docs002(iname, docid, nritm, fname, ename, attrb) values('DATAELEMENT.LENGTH', 'DATAELEMENT', 2, 'LNGTH', 'DATAELEMENT.LENGTH', 'length');
insert into docs002(iname, docid, nritm, fname, ename, attrb) values('DATAELEMENT.TYPE', 'DATAELEMENT', 3, 'ETYPE', 'DATAELEMENT.TYPE', 'type');
insert into docs002(iname, docid, nritm, fname, ename, attrb) values('DATAELEMENT.UPCASE', 'DATAELEMENT', 4, 'UPCAS', 'DATAELEMENT.UPCASE', 'upcase');
insert into docs004(iname, docid) values('DATAELEMENT.NAME', 'DATAELEMENT');
insert into docs005(tname, docid) values('DOCS003', 'DATAELEMENT');

insert into docs001(docid, tname, class) values('MODELITEM', 'DOCS002', 'org.iocaste.documents.common.DocumentModelItem');
insert into docs003(ename, decim, lngth, etype, upcas) values('MODELITEM.NAME', 0, 48, 0, 1);
insert into docs003(ename, decim, lngth, etype, upcas) values('MODELITEM.INDEX', 1, 3, 3, 0);
insert into docs003(ename, decim, lngth, etype, upcas) values('MODELITEM.FIELDNAME', 0, 12, 0, 1);
insert into docs003(ename, decim, lngth, etype, upcas) values('MODELITEM.ATTRIB', 0, 64, 0, 0);
insert into docs002(iname, docid, nritm, fname, ename, attrb) values('MODELITEM.NAME', 'MODELITEM', 0, 'INAME', 'MODELITEM.NAME', 'name');
insert into docs002(iname, docid, nritm, fname, ename, attrb, itref) values('MODELITEM.MODEL', 'MODELITEM', 1, 'DOCID', 'MODEL.NAME', 'documentModel', 'MODEL.NAME');
insert into docs002(iname, docid, nritm, fname, ename, attrb) values('MODELITEM.INDEX', 'MODELITEM', 2, 'NRITM', 'MODELITEM.INDEX', 'nritm');
insert into docs002(iname, docid, nritm, fname, ename, attrb) values('MODELITEM.FIELDNAME', 'MODELITEM', 3, 'FNAME', 'MODELITEM.FIELDNAME', 'tableFieldName');
insert into docs002(iname, docid, nritm, fname, ename, attrb) values('MODELITEM.ELEMENT', 'MODELITEM', 4, 'ENAME', 'DATAELEMENT.NAME', 'dataElement');
insert into docs002(iname, docid, nritm, fname, ename, attrb) values('MODELITEM.ATTRIB', 'MODELITEM', 5, 'ATTRB', 'MODELITEM.ATTRIB', 'attributeName');
insert into docs002(iname, docid, nritm, fname, ename, attrb) values('MODELITEM.ITEM_REF', 'MODELITEM', 6, 'ITREF', 'MODELITEM.NAME', 'itemReference');
insert into docs004(iname, docid) values('MODELITEM.NAME', 'MODELITEM');
insert into docs005(tname, docid) values('DOCS002', 'MODELITEM');
insert into docs006(iname, itref) values('MODELITEM.MODEL', 'MODEL.NAME');

insert into docs001(docid, tname, class) values('TABLE_MODEL', 'DOCS005', '');
insert into docs002(iname, docid, nritm, fname, ename, attrb) values('TABLE_MODEL.TABLE', 'TABLE_MODEL', 0, 'TNAME', 'MODEL.TABLE', 'tableName');
insert into docs002(iname, docid, nritm, fname, ename, attrb, itref) values('TABLE_MODEL.MODEL', 'TABLE_MODEL', 1, 'MODEL', 'MODEL.NAME', 'model', 'MODEL.NAME');
insert into docs004(iname, docid) values('TABLE_MODEL.TABLE', 'TABLE_MODEL');
insert into docs005(tname, docid) values('DOCS005', 'TABLE_MODEL');
insert into docs006(iname, itref) values('TABLE_MODEL.MODEL', 'MODEL.NAME');

insert into docs001(docid, tname, class) values('FOREIGN_KEY', 'DOCS006', '');
insert into docs002(iname, docid, nritm, fname, ename, attrb) values('FOREIGN_KEY.ITEM_NAME', 'FOREIGN_KEY', 0, 'ITREF', 'MODELITEM.NAME', 'itemName');
insert into docs002(iname, docid, nritm, fname, ename, attrb, itref) values('FOREIGN_KEY.REFERENCE', 'FOREIGN_KEY', 1, 'INAME', 'MODELITEM.NAME', 'reference', 'MODELITEM.NAME');
insert into docs004(iname, docid) values('FOREIGN_KEY.ITEM_NAME', 'FOREIGN_KEY');
insert into docs005(tname, docid) values('DOCS006', 'FOREIGN_KEY');
insert into docs006(iname, itref) values('FOREIGN_KEY.REFERENCE', 'MODELITEM.NAME');
insert into docs006(iname, itref) values('FOREIGN_KEY.ITEM_NAME', 'MODELITEM.NAME');

insert into docs001(docid, tname, class) values('NUMBER_RANGE', 'RANGE001', '');
insert into docs003(ename, decim, lngth, etype, upcas) values('NUMBER_RANGE.IDENT', 0, 12, 0, 1);
insert into docs003(ename, decim, lngth, etype, upcas) values('NUMBER_RANGE.CURRENT', 0, 12, 3, 0);
insert into docs002(iname, docid, nritm, fname, ename, attrb) values('NUMBER_RANGE.IDENT', 'NUMBER_RANGE', 0, 'IDENT', 'NUMBER_RANGE.IDENT', '');
insert into docs002(iname, docid, nritm, fname, ename, attrb) values('NUMBER_RANGE.CURRENT', 'NUMBER_RANGE', 1, 'CRRNT', 'NUMBER_RANGE.CURRENT', '');
insert into docs004(iname, docid) values('NUMBER_RANGE.IDENT', 'NUMBER_RANGE');
insert into docs005(tname, docid) values('RANGE001', 'NUMBER_RANGE');
