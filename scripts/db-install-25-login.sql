\p running login script.
insert into docs001(docid, tname) values ('LOGIN', 'USERS001');
insert into docs002(iname, docid, index, fname) values ('LOGIN.USERNAME', 'LOGIN', 0, 'UNAME');
insert into docs002(iname, docid, index, fname) values ('LOGIN.SECRET', 'LOGIN', 1, 'SECRT');
insert into docs004(iname, docid) values('LOGIN.USERNAME', 'LOGIN');

\p initial configuration saved.

commit work;

