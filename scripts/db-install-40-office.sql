/* tokens '\c e \p nao sao aceitos. nao existe parametro 'if exists' para 'drop user' */
drop table office001 if exists;
\p office tables dropped.

/* mensagem */
create table office001 (
   ident numeric(12) primary key,
   sendr varchar(12),
   recvr varchar(12),
   sbjct varchar(60)
);

\p office tables generated.

grant select, insert, update, delete on office001 to iocastedb;
\p permissions granted.

commit work;


