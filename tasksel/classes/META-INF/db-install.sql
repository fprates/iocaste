/* tokens '\c e \p nao sao aceitos. nao existe parametro 'if exists' para 'drop user' */
drop table task001 if exists;
\p tasksel tables dropped.

/* tarefa */
create table task001 (
   tname varchar(12) primary key,
   appli varchar(60),
   entry varchar(12)
);

\p tasksel tables has been generated

grant select, insert, update, delete on task001 to iocastedb;
\p permissions granted.

insert into task001 (tname, appli, entry) values ('infosis', 'iocaste-infosis', 'main');
insert into task001 (tname, appli, entry) values ('office', 'iocaste-office', 'main');
insert into task001 (tname, appli, entry) values ('tools', 'iocaste-core-utils', 'main');
insert into task001 (tname, appli, entry) values ('task_add', 'iocaste-tasksel', 'add');

commit work;

