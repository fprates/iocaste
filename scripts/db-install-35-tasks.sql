create table tasks (
    tsknm varchar(18) primary key,
    cmdln varchar(128)
);

grant select, insert, update, delete on tasks to iocastedb;

insert into docs001(docid, tname, class) values('TASKS', 'TASKS', '');
insert into docs002(iname, docid, index, fname, ename, attrb) values('TASKS.NAME', 'TASKS', 0, 'TSKNM', 'TASKS.NAME', 'name');
insert into docs002(iname, docid, index, fname, ename, attrb) values('TASKS.COMMAND', 'TASKS', 1, 'CMDLN', 'TASKS.COMMAND', 'command');
insert into docs003(ename, decim, lngth, etype, upcas) values('TASKS.NAME', 0, 18, 0, 1);
insert into docs003(ename, decim, lngth, etype, upcas) values('TASKS.COMMAND', 0, 128, 0, 0);
insert into docs004(iname, docid) values('TASKS.NAME', 'TASKS');
insert into docs005(tname, docid) values('TASKS', 'TASKS');

insert into tasks (tsknm, cmdln) values('DATADICT', 'iocaste-datadict');
insert into tasks (tsknm, cmdln) values('SE11', 'iocaste-datadict');
insert into tasks (tsknm, cmdln) values('DATAVIEW', 'iocaste-dataview');
insert into tasks (tsknm, cmdln) values('SE16', 'iocaste-dataview');
insert into tasks (tsknm, cmdln) values('INFOSIS', 'iocaste-infosis');
insert into tasks (tsknm, cmdln) values('TRANSPORT', 'iocaste-transport');
insert into tasks (tsknm, cmdln) values('EXTERNAL', 'iocaste-external');
