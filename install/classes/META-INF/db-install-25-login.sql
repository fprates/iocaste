insert into DOCS001(docid, tname) values ('USER_CONFIG', 'USERS000');
insert into DOCS003(ename, decim, lngth, etype, upcas) values('USER_CONFIG.CURRENT', 0, 5, 3, 0);
insert into DOCS002(iname, docid, nritm, fname, ename) values('USER_CONFIG.CURRENT', 'USER_CONFIG', 0, 'CRRNT', 'USER_CONFIG.CURRENT');
insert into DOCS005(tname, docid) values('USERS000', 'USER_CONFIG');
 
insert into DOCS001(docid, tname) values ('LOGIN', 'USERS001');
insert into DOCS003(ename, decim, lngth, etype, upcas) values('LOGIN.USERNAME', 0, 12, 0, 1);
insert into DOCS003(ename, decim, lngth, etype, upcas) values('LOGIN.SECRET', 0, 12, 0, 0);
insert into DOCS003(ename, decim, lngth, etype, upcas) values('LOGIN.ID', 0, 5, 3, 0);
insert into DOCS002(iname, docid, nritm, fname, ename) values('LOGIN.USERNAME', 'LOGIN', 0, 'UNAME', 'LOGIN.USERNAME');
insert into DOCS002(iname, docid, nritm, fname, ename) values('LOGIN.SECRET', 'LOGIN', 1, 'SECRT', 'LOGIN.SECRET');
insert into DOCS002(iname, docid, nritm, fname, ename) values('LOGIN.ID', 'LOGIN', 2, 'USRID', 'LOGIN.ID');
insert into DOCS004(iname, docid) values('LOGIN.USERNAME', 'LOGIN');
insert into DOCS005(tname, docid) values('USERS001', 'LOGIN');

insert into DOCS001(docid, tname) values('AUTHORIZATION', 'AUTH001');
insert into DOCS003(ename, decim, lngth, etype, upcas) values('AUTHORIZATION.NAME', 0, 24, 0, 1);
insert into DOCS003(ename, decim, lngth, etype, upcas) values('AUTHORIZATION.OBJECT', 0, 12, 0, 1);
insert into DOCS003(ename, decim, lngth, etype, upcas) values('AUTHORIZATION.ACTION', 0, 12, 0, 1);
insert into DOCS003(ename, decim, lngth, etype, upcas) values('AUTHORIZATION.INDEX', 0, 5, 3, 0);
insert into DOCS002(iname, docid, nritm, fname, ename) values('AUTHORIZATION.NAME', 'AUTHORIZATION', 0, 'AUTNM', 'AUTHORIZATION.NAME');
insert into DOCS002(iname, docid, nritm, fname, ename) values('AUTHORIZATION.OBJECT', 'AUTHORIZATION', 1, 'OBJCT', 'AUTHORIZATION.OBJECT');
insert into DOCS002(iname, docid, nritm, fname, ename) values('AUTHORIZATION.ACTION', 'AUTHORIZATION', 2, 'ACTIO', 'AUTHORIZATION.ACTION');
insert into DOCS002(iname, docid, nritm, fname, ename) values('AUTHORIZATION.INDEX', 'AUTHORIZATION', 3, 'AUTID', 'AUTHORIZATION.INDEX');
insert into DOCS004(iname, docid) values('AUTHORIZATION.NAME', 'AUTHORIZATION');
insert into DOCS005(tname, docid) values('AUTH001', 'AUTHORIZATION');

insert into DOCS001(docid, tname) values('AUTHORIZATION_ITEM', 'AUTH002');
insert into DOCS003(ename, decim, lngth, etype, upcas) values('AUTHORIZATION_ITEM.ID', 0, 8, 3, 0);
insert into DOCS003(ename, decim, lngth, etype, upcas) values('AUTHORIZATION_ITEM.NAME', 0, 12, 0, 1);
insert into DOCS003(ename, decim, lngth, etype, upcas) values('AUTHORIZATION_ITEM.VALUE', 0, 64, 0, 1);
insert into DOCS002(iname, docid, nritm, fname, ename) values('AUTHORIZATION_ITEM.ID', 'AUTHORIZATION_ITEM', 0, 'IDENT', 'AUTHORIZATION_ITEM.ID');
insert into DOCS002(iname, docid, nritm, fname, ename, itref) values('AUTHORIZATION_ITEM.AUTHORIZATION', 'AUTHORIZATION_ITEM', 1, 'AUTNM', 'AUTHORIZATION.NAME', 'AUTHORIZATION.NAME');
insert into DOCS002(iname, docid, nritm, fname, ename) values('AUTHORIZATION_ITEM.NAME', 'AUTHORIZATION_ITEM', 2, 'PARAM', 'AUTHORIZATION_ITEM.NAME');
insert into DOCS002(iname, docid, nritm, fname, ename) values('AUTHORIZATION_ITEM.VALUE', 'AUTHORIZATION_ITEM', 3, 'VALUE', 'AUTHORIZATION_ITEM.VALUE');
insert into DOCS004(iname, docid) values('AUTHORIZATION_ITEM.ID', 'AUTHORIZATION_ITEM');
insert into DOCS005(tname, docid) values('AUTH002', 'AUTHORIZATION_ITEM');

insert into DOCS001(docid, tname) values('USER_PROFILE', 'AUTH003');
insert into DOCS003(ename, decim, lngth, etype, upcas) values('USER_PROFILE.NAME', 0, 12, 0, 1);
insert into DOCS003(ename, decim, lngth, etype, upcas) values('USER_PROFILE.ID', 0, 5, 3, 0);
insert into DOCS003(ename, decim, lngth, etype, upcas) values('USER_PROFILE.CURRENT', 0, 8, 3, 0);
insert into DOCS002(iname, docid, nritm, fname, ename) values('USER_PROFILE.NAME', 'USER_PROFILE', 0, 'PRFNM', 'USER_PROFILE.NAME');
insert into DOCS002(iname, docid, nritm, fname, ename) values('USER_PROFILE.ID', 'USER_PROFILE', 1, 'PRFID', 'USER_PROFILE.ID');
insert into DOCS002(iname, docid, nritm, fname, ename) values('USER_PROFILE.CURRENT', 'USER_PROFILE', 2, 'CRRNT', 'USER_PROFILE.CURRENT');
insert into DOCS004(iname, docid) values('USER_PROFILE.NAME', 'USER_PROFILE');
insert into DOCS005(tname, docid) values('AUTH003', 'USER_PROFILE');
   
insert into DOCS001(docid, tname) values('USER_PROFILE_ITEM', 'AUTH004');
insert into DOCS003(ename, decim, lngth, etype, upcas) values('USER_PROFILE_ITEM.ID', 0, 8, 3, 0);
insert into DOCS002(iname, docid, nritm, fname, ename) values('USER_PROFILE_ITEM.ID', 'USER_PROFILE_ITEM', 0, 'IDENT', 'USER_PROFILE_ITEM.ID');
insert into DOCS002(iname, docid, nritm, fname, ename, itref) values('USER_PROFILE_ITEM.PROFILE', 'USER_PROFILE_ITEM', 1, 'PRFNM', 'USER_PROFILE.NAME', 'USER_PROFILE.NAME');
insert into DOCS002(iname, docid, nritm, fname, ename, itref) values('USER_PROFILE_ITEM.NAME', 'USER_PROFILE_ITEM', 2, 'AUTNM', 'AUTHORIZATION.NAME', 'AUTHORIZATION.NAME');
insert into DOCS002(iname, docid, nritm, fname, ename) values('USER_PROFILE_ITEM.OBJECT', 'USER_PROFILE_ITEM', 3, 'OBJCT', 'AUTHORIZATION.OBJECT');
insert into DOCS002(iname, docid, nritm, fname, ename) values('USER_PROFILE_ITEM.ACTION', 'USER_PROFILE_ITEM', 4, 'ACTIO', 'AUTHORIZATION.ACTION');
insert into DOCS004(iname, docid) values('USER_PROFILE_ITEM.ID', 'USER_PROFILE_ITEM');
insert into DOCS005(tname, docid) values('AUTH004', 'USER_PROFILE_ITEM');
   
insert into DOCS001(docid, tname) values('USER_AUTHORITY', 'USERS002');
insert into DOCS003(ename, decim, lngth, etype, upcas) values('USER_AUTHORITY.ID', 0, 8, 3, 0);
insert into DOCS002(iname, docid, nritm, fname, ename) values('USER_AUTHORITY.ID', 'USER_AUTHORITY', 0, 'IDENT', 'USER_AUTHORITY.ID');
insert into DOCS002(iname, docid, nritm, fname, ename, itref) values('USER_AUTHORITY.USERNAME', 'USER_AUTHORITY', 1, 'UNAME', 'LOGIN.USERNAME', 'LOGIN.USERNAME');
insert into DOCS002(iname, docid, nritm, fname, ename, itref) values('USER_AUTHORITY.PROFILE', 'USER_AUTHORITY', 2, 'PRFNM', 'USER_PROFILE.NAME', 'USER_PROFILE.NAME');
insert into DOCS004(iname, docid) values('USER_AUTHORITY.ID', 'USER_AUTHORITY');
insert into DOCS005(tname, docid) values('USERS002', 'USER_AUTHORITY');
