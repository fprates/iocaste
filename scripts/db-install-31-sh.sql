insert into shcab(ident, docid, exprt) values('SH_MODEL', 'MODEL', 'MODEL.NAME');
insert into shitm(iname, shcab, mditm) values('SH_MODEL.NAME', 'SH_MODEL', 'MODEL.NAME');
insert into shref(iname, shcab) values('MODEL.NAME', 'SH_MODEL');

commit work;
