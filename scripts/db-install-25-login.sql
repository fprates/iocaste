\p running login script.
insert into docs001(docid, tname) values ('LOGIN', 'USERS001');
insert into docs003(ename, decim, lngth, etype, upcas) values('LOGIN.USERNAME', 0, 12, 0, 1);
insert into docs003(ename, decim, lngth, etype, upcas) values('LOGIN.SECRET', 0, 12, 0, 0);
insert into docs003(ename, decim, lngth, etype, upcas) values('LOGIN.ID', 0, 5, 3, 0);
insert into docs002(iname, docid, index, fname, ename) values('LOGIN.USERNAME', 'LOGIN', 0, 'UNAME', 'LOGIN.USERNAME');
insert into docs002(iname, docid, index, fname, ename) values('LOGIN.SECRET', 'LOGIN', 1, 'SECRT', 'LOGIN.SECRET');
insert into docs002(iname, docid, index, fname, ename) values('LOGIN.ID', 'LOGIN', 2, 'USRID', 'LOGIN.ID');
insert into docs004(iname, docid) values('LOGIN.USERNAME', 'LOGIN');
insert into docs005(tname, docid) values('USERS001', 'LOGIN');

insert into docs001(docid, tname) values('AUTHORIZATION', 'AUTH001');
insert into docs003(ename, decim, lngth, etype, upcas) values('AUTHORIZATION.NAME', 0, 24, 0, 1);
insert into docs003(ename, decim, lngth, etype, upcas) values('AUTHORIZATION.OBJECT', 0, 12, 0, 1);
insert into docs003(ename, decim, lngth, etype, upcas) values('AUTHORIZATION.ACTION', 0, 12, 0, 1);
insert into docs003(ename, decim, lngth, etype, upcas) values('AUTHORIZATION.INDEX', 0, 5, 3, 0);
insert into docs002(iname, docid, index, fname, ename) values('AUTHORIZATION.NAME', 'AUTHORIZATION', 0, 'AUTNM', 'AUTHORIZATION.NAME');
insert into docs002(iname, docid, index, fname, ename) values('AUTHORIZATION.OBJECT', 'AUTHORIZATION', 1, 'OBJCT', 'AUTHORIZATION.OBJECT');
insert into docs002(iname, docid, index, fname, ename) values('AUTHORIZATION.ACTION', 'AUTHORIZATION', 2, 'ACTIO', 'AUTHORIZATION.ACTION');
insert into docs002(iname, docid, index, fname, ename) values('AUTHORIZATION.INDEX', 'AUTHORIZATION', 3, 'AUTID', 'AUTHORIZATION.INDEX');
insert into docs004(iname, docid) values('AUTHORIZATION.NAME', 'AUTHORIZATION');
insert into docs005(tname, docid) values('AUTH001', 'AUTHORIZATION');

insert into docs001(docid, tname) values('AUTHORIZATION_ITEM', 'AUTH002');
insert into docs003(ename, decim, lngth, etype, upcas) values('AUTHORIZATION_ITEM.ID', 0, 8, 3, 0);
insert into docs003(ename, decim, lngth, etype, upcas) values('AUTHORIZATION_ITEM.NAME', 0, 12, 0, 1);
insert into docs003(ename, decim, lngth, etype, upcas) values('AUTHORIZATION_ITEM.VALUE', 0, 64, 0, 1);
insert into docs002(iname, docid, index, fname, ename) values('AUTHORIZATION_ITEM.ID', 'AUTHORIZATION_ITEM', 0, 'IDENT', 'AUTHORIZATION_ITEM.ID');
insert into docs002(iname, docid, index, fname, ename, itref) values('AUTHORIZATION_ITEM.AUTHORIZATION', 'AUTHORIZATION_ITEM', 1, 'AUTNM', 'AUTHORIZATION.NAME', 'AUTHORIZATION.NAME');
insert into docs002(iname, docid, index, fname, ename) values('AUTHORIZATION_ITEM.NAME', 'AUTHORIZATION_ITEM', 2, 'PARAM', 'AUTHORIZATION_ITEM.NAME');
insert into docs002(iname, docid, index, fname, ename) values('AUTHORIZATION_ITEM.VALUE', 'AUTHORIZATION_ITEM', 3, 'VALUE', 'AUTHORIZATION_ITEM.VALUE');
insert into docs004(iname, docid) values('AUTHORIZATION_ITEM.ID', 'AUTHORIZATION_ITEM');
insert into docs005(tname, docid) values('AUTH002', 'AUTHORIZATION_ITEM');

insert into docs001(docid, tname) values('USER_PROFILE', 'AUTH003');
insert into docs003(ename, decim, lngth, etype, upcas) values('USER_PROFILE.NAME', 0, 12, 0, 1);
insert into docs003(ename, decim, lngth, etype, upcas) values('USER_PROFILE.ID', 0, 5, 3, 0);
insert into docs002(iname, docid, index, fname, ename) values('USER_PROFILE.NAME', 'USER_PROFILE', 0, 'PRFNM', 'USER_PROFILE.NAME');
insert into docs002(iname, docid, index, fname, ename) values('USER_PROFILE.ID', 'USER_PROFILE', 1, 'PRFID', 'USER_PROFILE.ID');
insert into docs004(iname, docid) values('USER_PROFILE.NAME', 'USER_PROFILE');
insert into docs005(tname, docid) values('AUTH003', 'USER_PROFILE');
   
insert into docs001(docid, tname) values('USER_PROFILE_ITEM', 'AUTH004');
insert into docs003(ename, decim, lngth, etype, upcas) values('USER_PROFILE_ITEM.ID', 0, 8, 3, 0);
insert into docs002(iname, docid, index, fname, ename) values('USER_PROFILE_ITEM.ID', 'USER_PROFILE_ITEM', 0, 'IDENT', 'USER_PROFILE_ITEM.ID');
insert into docs002(iname, docid, index, fname, ename, itref) values('USER_PROFILE_ITEM.PROFILE', 'USER_PROFILE_ITEM', 1, 'PRFNM', 'USER_PROFILE.NAME', 'USER_PROFILE.NAME');
insert into docs002(iname, docid, index, fname, ename, itref) values('USER_PROFILE_ITEM.NAME', 'USER_PROFILE_ITEM', 2, 'AUTNM', 'AUTHORIZATION.NAME', 'AUTHORIZATION.NAME');
insert into docs002(iname, docid, index, fname, ename) values('USER_PROFILE_ITEM.OBJECT', 'USER_PROFILE_ITEM', 3, 'OBJCT', 'AUTHORIZATION.OBJECT');
insert into docs002(iname, docid, index, fname, ename) values('USER_PROFILE_ITEM.ACTION', 'USER_PROFILE_ITEM', 4, 'ACTIO', 'AUTHORIZATION.ACTION');
insert into docs004(iname, docid) values('USER_PROFILE_ITEM.ID', 'USER_PROFILE_ITEM');
insert into docs005(tname, docid) values('AUTH004', 'USER_PROFILE_ITEM');
   
insert into docs001(docid, tname) values('USER_AUTHORITY', 'USERS002');
insert into docs003(ename, decim, lngth, etype, upcas) values('USER_AUTHORITY.ID', 0, 8, 3, 0);
insert into docs002(iname, docid, index, fname, ename) values('USER_AUTHORITY.ID', 'USER_AUTHORITY', 0, 'IDENT', 'USER_AUTHORITY.ID');
insert into docs002(iname, docid, index, fname, ename, itref) values('USER_AUTHORITY.USERNAME', 'USER_AUTHORITY', 1, 'UNAME', 'LOGIN.USERNAME', 'LOGIN.USERNAME');
insert into docs002(iname, docid, index, fname, ename, itref) values('USER_AUTHORITY.PROFILE', 'USER_AUTHORITY', 2, 'PRFNM', 'USER_PROFILE.NAME', 'USER_PROFILE.NAME');
insert into docs004(iname, docid) values('USER_AUTHORITY.ID', 'USER_AUTHORITY');
insert into docs005(tname, docid) values('USERS002', 'USER_AUTHORITY');

\p initial configuration saved.
commit work;
