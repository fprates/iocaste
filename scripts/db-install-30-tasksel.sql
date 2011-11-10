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

delete from docs004 where docid='task_entry';
delete from docs003 where ename='entry_name';
delete from docs003 where ename='entry_app';
delete from docs003 where ename='entry_point';
delete from docs002 where docid='task_entry';
delete from docs001 where docid='task_entry';


/* insert into task001 (tname, appli, entry) values ('infosis', 'iocaste-infosis', 'main'); */
/* insert into task001 (tname, appli, entry) values ('task_add', 'iocaste-tasksel', 'add'); */

insert into docs001 (docid, tname, class) values ('task_entry', 'task001', 'org.iocaste.tasksel.Task');

insert into docs003 (ename, decim, lngth, etype) values ('entry_name', 0, 12, 0);
insert into docs003 (ename, decim, lngth, etype) values ('entry_app', 0, 60, 0);
insert into docs003 (ename, decim, lngth, etype) values ('entry_point', 0, 12, 0);

insert into docs002 (iname, docid, index, fname, ename, attrb) values ('entry', 'task_entry', 0, 'TNAME', 'entry_name', 'name');
insert into docs002 (iname, docid, index, fname, ename, attrb) values ('app', 'task_entry', 1, 'APPLI', 'entry_app', 'app');
insert into docs002 (iname, docid, index, fname, ename, attrb) values ('point', 'task_entry', 2, 'ENTRY', 'entry_point', 'entry');

insert into docs004 (docid, iname) values ('task_entry', 'entry');

\p initial configuration saved.

commit work;

