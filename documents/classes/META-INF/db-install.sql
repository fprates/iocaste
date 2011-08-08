/* tokens '\c e \p nao sao aceitos. nao existe parametro 'if exists' para 'drop user' */
drop table range001 if exists;
drop table docs002 if exists;
drop table docs001 if exists;
\p document tables dropped.

/* range numérico */
create table range001 (
   ident char(12) primary key,
   crrnt numeric(12)
);

/* documentos */
create table docs001 (
   docid char(12) primary key
);

create table docs002 (
   iname char(12) primary key,
   docid char(12) foreign key references docs001(docid)
);

\p document tables has been generated

grant select, insert, update, delete on range001 to iocastedb;
grant select, insert, update, delete on docs001 to iocastedb;
grant select, insert, update, delete on docs002 to iocastedb;
\p permissions granted.

insert into range001 (ident, crrnt) values ('OFFICEMSGNR', 0);

commit work;

