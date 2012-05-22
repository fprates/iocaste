\p running login script.
insert into docs001(docid, tname) values ('LOGIN', 'USERS001');
insert into docs003(ename, decim, lngth, etype, upcas) values('LOGIN.USERNAME', 0, 12, 0, 1);
insert into docs003(ename, decim, lngth, etype, upcas) values('LOGIN.SECRET', 0, 12, 0, 0);
insert into docs002(iname, docid, index, fname, ename) values ('LOGIN.USERNAME', 'LOGIN', 0, 'UNAME', 'LOGIN.USERNAME');
insert into docs002(iname, docid, index, fname, ename) values ('LOGIN.SECRET', 'LOGIN', 1, 'SECRT', 'LOGIN.SECRET');
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
insert into docs002(iname, docid, index, fname, ename) values('AUTHORIZATION_ITEM.ID', 'AUTHORIZATION_ITEM', 0, 'IDENT', 'AUTHORIZATION_ITEM.ID');
insert into docs002(iname, docid, index, fname, ename, itref) values('AUTHORIZATION_ITEM.AUTHORIZATION', 'AUTHORIZATION_ITEM', 1, 'AUTNM', 'AUTHORIZATION.NAME', 'AUTHORIZATION.NAME');
insert into docs002(iname, docid, index, fname, ename) values('AUTHORIZATION_ITEM.NAME', 'AUTHORIZATION_ITEM', 2, 'PRMNM', 'AUTHORIZATION_ITEM.NAME');
insert into docs004(iname, docid) values('AUTHORIZATION_ITEM.ID', 'AUTHORIZATION_ITEM');
insert into docs005(tname, docid) values('AUTH002', 'AUTHORIZATION_ITEM');

insert into docs001(docid, tname) values('USER_PROFILE', 'USERS004');
insert into docs003(ename, decim, lngth, etype, upcas) values('USER_PROFILE.ID', 0, 9, 3, 0);
insert into docs003(ename, decim, lngth, etype, upcas) values('USER_PROFILE.PROFILE', 0, 12, 0, 1);
insert into docs003(ename, decim, lngth, etype, upcas) values('USER_PROFILE.CURRENT', 0, 12, 3, 0);
insert into docs002(iname, docid, index, fname, ename) values('USER_PROFILE.ID', 'USER_PROFILE', 0, 'IDENT', 'USER_PROFILE.ID');
insert into docs002(iname, docid, index, fname, ename, itref) values('USER_PROFILE.USERNAME', 'USER_PROFILE', 1, 'UNAME', 'LOGIN.USERNAME', 'LOGIN.USERNAME');
insert into docs002(iname, docid, index, fname, ename) values('USER_PROFILE.PROFILE', 'USER_PROFILE', 2, 'PRFNM', 'USER_PROFILE.PROFILE');
insert into docs002(iname, docid, index, fname, ename) values('USER_PROFILE.CURRENT', 'USER_PROFILE', 3, 'CRRNT', 'USER_PROFILE.CURRENT');
insert into docs004(iname, docid) values('USER_PROFILE.ID', 'USER_PROFILE');
insert into docs005(tname, docid) values('USERS004', 'USER_PROFILE');

insert into docs001(docid, tname) values('USER_AUTHORITY', 'USERS002');
insert into docs003(ename, decim, lngth, etype, upcas) values('USER_AUTHORITY.ID', 0, 12, 3, 0);
insert into docs003(ename, decim, lngth, etype, upcas) values('USER_AUTHORITY.NAME', 0, 24, 0, 1);
insert into docs002(iname, docid, index, fname, ename) values('USER_AUTHORITY.ID', 'USER_AUTHORITY', 0, 'IDENT', 'USER_AUTHORITY.ID');
insert into docs002(iname, docid, index, fname, ename, itref) values('USER_AUTHORITY.PROFILE', 'USER_AUTHORITY', 1, 'PRFID', 'USER_PROFILE.PROFILE', 'USER_PROFILE.ID');
insert into docs002(iname, docid, index, fname, ename) values('USER_AUTHORITY.NAME', 'USER_AUTHORITY', 2, 'AUTNM', 'USER_AUTHORITY.NAME');
insert into docs004(iname, docid) values('USER_AUTHORITY.ID', 'USER_AUTHORITY');
insert into docs005(tname, docid) values('USERS002', 'USER_AUTHORITY');

insert into docs001(docid, tname) values('USER_AUTHORITY_ITEM', 'USERS003');
insert into docs003(ename, decim, lngth, etype, upcas) values('USER_AUTHORITY_ITEM.ID', 0, 15, 3, 0);
insert into docs003(ename, decim, lngth, etype, upcas) values('USER_AUTHORITY_ITEM.NAME', 0, 24, 0, 1);
insert into docs003(ename, decim, lngth, etype, upcas) values('USER_AUTHORITY_ITEM.VALUE', 0, 64, 0, 1);
insert into docs002(iname, docid, index, fname, ename) values('USER_AUTHORITY_ITEM.ID', 'USER_AUTHORITY_ITEM', 0, 'IDENT', 'USER_AUTHORITY_ITEM.ID');
insert into docs002(iname, docid, index, fname, ename, itref) values('USER_AUTHORITY_ITEM.AUTHORIZATION', 'USER_AUTHORITY_ITEM', 1, 'AUTID', 'USER_AUTHORITY.ID', 'USER_AUTHORITY.ID');
insert into docs002(iname, docid, index, fname, ename) values('USER_AUTHORITY_ITEM.NAME', 'USER_AUTHORITY_ITEM', 2, 'PARAM', 'USER_AUTHORITY_ITEM.NAME');
insert into docs002(iname, docid, index, fname, ename) values('USER_AUTHORITY_ITEM.VALUE', 'USER_AUTHORITY_ITEM', 3, 'VALUE', 'USER_AUTHORITY_ITEM.VALUE');
insert into docs004(iname, docid) values('USER_AUTHORITY_ITEM.ID', 'USER_AUTHORITY_ITEM');
insert into docs005(tname, docid) values('USERS003', 'USER_AUTHORITY_ITEM');

\p initial configuration saved.
commit work;

