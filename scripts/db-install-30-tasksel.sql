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
insert into task001 (tname, appli, entry) values ('task_add', 'iocaste-tasksel', 'add');

insert into docs001 (docid) values ('task_entry');

insert into docs003 (ename, decim, lngth, etype) values ('entry_name', 0, 12, 0);
insert into docs003 (ename, decim, lngth, etype) values ('entry_app', 0, 60, 0);
insert into docs003 (ename, decim, lngth, etype) values ('entry_point', 0, 12, 0);

insert into docs002 (iname, docid, ename) values ('entry', 'task_entry', 'entry_name');
insert into docs002 (iname, docid, ename) values ('app', 'task_entry', 'entry_app');
insert into docs002 (iname, docid, ename) values ('point', 'task_entry', 'entry_point');
\p initial configuration saved.

commit work;

