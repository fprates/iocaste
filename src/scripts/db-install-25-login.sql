\p running login script.
insert into docs001 (docid) values ('LOGIN');

insert into docs002 (iname, docid, index) values ('USERNAME', 'LOGIN', 0);
insert into docs002 (iname, docid, index) values ('SECRET', 'LOGIN', 1);
\p initial configuration saved.

commit work;

