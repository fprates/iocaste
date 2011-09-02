/* tokens '\c e \p nao sao aceitos. nao existe parametro 'if exists' para 'drop user' */
drop table shell003 if exists;
drop table shell002 if exists;
drop table shell001 if exists;
\p shell tables dropped.

/* folha de estilo */
create table shell001 (
    sname varchar(12) primary key,
    sindx numeric(12)
);

create table shell002 (
    eindx numeric(12) primary key,
    sname varchar(12) foreign key references shell001(sname),
    ename varchar(60) 
);

create table shell003 (
    pindx numeric(12) primary key,
    eindx numeric(12) foreign key references shell002(eindx),
    pname varchar(60),
    value varchar(60)
);

\p tables generated.

grant select, insert, update, delete on shell001 to iocastedb;
grant select, insert, update, delete on shell002 to iocastedb;
grant select, insert, update, delete on shell003 to iocastedb;
\p permissions granted.

insert into shell001 (sname, sindx) values ('DEFAULT', 000100000000);
insert into shell002 (eindx, sname, ename) values (000100010000, 'DEFAULT', 'body');
insert into shell003 (pindx, eindx, pname, value) values (000100010001, 000100010000, 'font-family', 'sans-serif');
insert into shell003 (pindx, eindx, pname, value) values (000100010002, 000100010000, 'font-size', '10pt');

insert into shell002 (eindx, sname, ename) values (000100020000, 'DEFAULT', 'p.form');
insert into shell003 (pindx, eindx, pname, value) values (000100020001, 000100020000, 'text-align', 'right');
        
insert into shell002 (eindx, sname, ename) values (000100030000, 'DEFAULT', 'input.form');
insert into shell003 (pindx, eindx, pname, value) values (000100030001, 000100030000, 'border-width', '1px');
insert into shell003 (pindx, eindx, pname, value) values (000100030002, 000100030000, 'border-color', '#000000');
        
insert into shell002 (eindx, sname, ename) values (000100040000, 'DEFAULT', '.submit');
insert into shell003 (pindx, eindx, pname, value) values (000100040001, 000100040000, 'border-width', '1px');
insert into shell003 (pindx, eindx, pname, value) values (000100040002, 000100040000, 'border-color', '#000000');
insert into shell003 (pindx, eindx, pname, value) values (000100040003, 000100040000, 'font-size', '8pt');
\p initial configuration saved.

commit work;

