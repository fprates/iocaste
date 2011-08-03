/* tokens '\c e \p nao sao aceitos. nao existe parametro 'if exists' para 'drop user' */
drop table range001 if exists;
\p document tables dropped.

/* range numérico */
create table range001 (
   ident char(12) primary key,
   crrnt numeric(12)
);

\p document tables generated.

grant select, insert, update, delete on range001 to iocastedb;
\p permissions granted.

insert into range001 (ident, crrnt) values ('OFFICEMSGNR', 0);

commit work;

