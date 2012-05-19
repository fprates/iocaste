\p running login script.
insert into docs001(docid, tname) values ('LOGIN', 'USERS001');
insert into docs003(ename, decim, lngth, etype, upcas) values('LOGIN.USERNAME', 0, 12, 0, 1);
insert into docs003(ename, decim, lngth, etype, upcas) values('LOGIN.SECRET', 0, 12, 0, 0);
insert into docs002(iname, docid, index, fname, ename) values ('LOGIN.USERNAME', 'LOGIN', 0, 'UNAME', 'LOGIN.USERNAME');
insert into docs002(iname, docid, index, fname, ename) values ('LOGIN.SECRET', 'LOGIN', 1, 'SECRT', 'LOGIN.SECRET');
insert into docs004(iname, docid) values('LOGIN.USERNAME', 'LOGIN');
insert into docs005(tname, docid) values('USERS001', 'LOGIN');

\p initial configuration saved.

commit work;

