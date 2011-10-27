/* tokens '\c e \p nao sao aceitos. nao existe parametro 'if exists' para 'drop user' */
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
   etype numeric(1)
);

/* itens de documento */
create table docs002 (
   iname varchar(12) primary key,
   docid varchar(12) foreign key references docs001(docid),
   fname varchar(12),
   ename varchar(36),
   attrb varchar(64)
);

/* chaves do documento */
create table docs004 (
   docid varchar(12) foreign key references docs001(docid),
   iname varchar(12) foreign key references docs002(iname)
);
\p document tables has been generated

grant select, insert, update, delete on range001 to iocastedb;
grant select, insert, update, delete on docs001 to iocastedb;
grant select, insert, update, delete on docs002 to iocastedb;
grant select, insert, update, delete on docs003 to iocastedb;
\p permissions granted.

commit work;

