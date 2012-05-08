/* tokens '\c e \p nao sao aceitos. nao existe parametro 'if exists' para 'drop user' */

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
insert into shell003 (pindx, eindx, pname, value) values (000100010003, 000100010000, 'background-color', '#333333');

insert into shell002 (eindx, sname, ename) values (000100020000, 'DEFAULT', '.button');
insert into shell003 (pindx, eindx, pname, value) values (000100020001, 000100020000, 'font-size', '12pt');
insert into shell003 (pindx, eindx, pname, value) values (000100020002, 000100020000, 'font-family', 'sans-serif');
insert into shell003 (pindx, eindx, pname, value) values (000100020003, 000100020000, 'text-align', 'center');
insert into shell003 (pindx, eindx, pname, value) values (000100020004, 000100020000, 'background-color', '#808080');
insert into shell003 (pindx, eindx, pname, value) values (000100020005, 000100020000, 'color', '#ffffff');
insert into shell003 (pindx, eindx, pname, value) values (000100020006, 000100020000, 'font-weight', 'bold');
insert into shell003 (pindx, eindx, pname, value) values (000100020007, 000100020000, 'border-style', 'none');
insert into shell003 (pindx, eindx, pname, value) values (000100020008, 000100020000, 'padding-top', '0.10cm');
insert into shell003 (pindx, eindx, pname, value) values (000100020009, 000100020000, 'padding-bottom', '0.10cm');
insert into shell003 (pindx, eindx, pname, value) values (000100020010, 000100020000, 'padding-left', '0.50cm');
insert into shell003 (pindx, eindx, pname, value) values (000100020011, 000100020000, 'padding-right', '0.50cm');
insert into shell003 (pindx, eindx, pname, value) values (000100020012, 000100020000, 'margin', '0.25cm');

insert into shell002 (eindx, sname, ename) values (000100030000, 'DEFAULT', '.item_form_name');
insert into shell003 (pindx, eindx, pname, value) values (000100030001, 000100030000, 'font-size', '12pt');
insert into shell003 (pindx, eindx, pname, value) values (000100030002, 000100030000, 'font-family', 'sans-serif');
insert into shell003 (pindx, eindx, pname, value) values (000100030003, 000100030000, 'text-align', 'right');
insert into shell003 (pindx, eindx, pname, value) values (000100030004, 000100030000, 'background-color', '#000000');
insert into shell003 (pindx, eindx, pname, value) values (000100030005, 000100030000, 'vertical-align', 'middle');
insert into shell003 (pindx, eindx, pname, value) values (000100030006, 000100030000, 'color', '#ffffff');
insert into shell003 (pindx, eindx, pname, value) values (000100030007, 000100030000, 'margin', '0.00cm');
insert into shell003 (pindx, eindx, pname, value) values (000100030008, 000100030000, 'padding', '0.10cm');

insert into shell002 (eindx, sname, ename) values (000100040000, 'DEFAULT', '.error_message');
insert into shell003 (pindx, eindx, pname, value) values (000100040001, 000100040000, 'font-size', '12pt');
insert into shell003 (pindx, eindx, pname, value) values (000100040002, 000100040000, 'font-family', 'sans-serif');
insert into shell003 (pindx, eindx, pname, value) values (000100040003, 000100040000, 'background-color', '#ff0000');
insert into shell003 (pindx, eindx, pname, value) values (000100040004, 000100040000, 'color', '#000000');
insert into shell003 (pindx, eindx, pname, value) values (000100040005, 000100040000, 'padding', '0.10cm');
insert into shell003 (pindx, eindx, pname, value) values (000100040006, 000100040000, 'margin', '0.25cm');

insert into shell002 (eindx, sname, ename) values (000100050000, 'DEFAULT', '.status');
insert into shell003 (pindx, eindx, pname, value) values (000100050001, 000100050000, 'font-size', '10pt');
insert into shell003 (pindx, eindx, pname, value) values (000100050002, 000100050000, 'font-family', 'sans-serif');
insert into shell003 (pindx, eindx, pname, value) values (000100050003, 000100050000, 'background-color', '#000000');
insert into shell003 (pindx, eindx, pname, value) values (000100050004, 000100050000, 'color', '#ffffff');
insert into shell003 (pindx, eindx, pname, value) values (000100050005, 000100050000, 'border-style', 'none');
insert into shell003 (pindx, eindx, pname, value) values (000100050006, 000100050000, 'padding', '0.10cm');
insert into shell003 (pindx, eindx, pname, value) values (000100050007, 000100050000, 'margin', '0.25cm');
                
insert into shell002 (eindx, sname, ename) values (000100060000, 'DEFAULT', '.title');
insert into shell003 (pindx, eindx, pname, value) values (000100060001, 000100060000, 'color', '#ffffff');
insert into shell003 (pindx, eindx, pname, value) values (000100060002, 000100060000, 'font-size', '26pt');
insert into shell003 (pindx, eindx, pname, value) values (000100060003, 000100060000, 'font-weight', 'bold');
insert into shell003 (pindx, eindx, pname, value) values (000100060004, 000100060000, 'margin', '0.25cm');
insert into shell003 (pindx, eindx, pname, value) values (000100060005, 000100060000, 'font-family', 'sans-serif');
                
insert into shell002 (eindx, sname, ename) values (000100070000, 'DEFAULT', '.form');
insert into shell003 (pindx, eindx, pname, value) values (000100070001, 000100070000, 'margin-left', '0.25cm');
insert into shell003 (pindx, eindx, pname, value) values (000100070002, 000100070000, 'padding', '0.00cm');
insert into shell003 (pindx, eindx, pname, value) values (000100070003, 000100070000, 'border-collapse', 'collapse');
insert into shell003 (pindx, eindx, pname, value) values (000100070004, 000100070000, 'border-width', '0.25cm');
insert into shell003 (pindx, eindx, pname, value) values (000100070005, 000100070000, 'border-style', 'solid');
insert into shell003 (pindx, eindx, pname, value) values (000100070006, 000100070000, 'border-color', '#000000');
insert into shell003 (pindx, eindx, pname, value) values (000100070007, 000100070000, 'margin-right', '0.25cm');
insert into shell003 (pindx, eindx, pname, value) values (000100070008, 000100070000, 'margin-top', '0.00cm');
insert into shell003 (pindx, eindx, pname, value) values (000100070009, 000100070000, 'margin-bottom', '0.00cm');
                
insert into shell002 (eindx, sname, ename) values (000100080000, 'DEFAULT', '.header');
insert into shell003 (pindx, eindx, pname, value) values (000100080001, 000100080000, 'background-color', '#000000');
insert into shell003 (pindx, eindx, pname, value) values (000100080002, 000100080000, 'padding', '0.10cm');
insert into shell003 (pindx, eindx, pname, value) values (000100080003, 000100080000, 'margin', '0.25cm');
insert into shell003 (pindx, eindx, pname, value) values (000100080004, 000100080000, 'border-style', 'none');

insert into shell002 (eindx, sname, ename) values (000100090000, 'DEFAULT', '.form_cell');
insert into shell003 (pindx, eindx, pname, value) values (000100090001, 000100090000, 'vertical-align', 'middle');
insert into shell003 (pindx, eindx, pname, value) values (000100090002, 000100090000, 'background-color', '#000000');
insert into shell003 (pindx, eindx, pname, value) values (000100090003, 000100090000, 'padding', '0.00cm');
insert into shell003 (pindx, eindx, pname, value) values (000100090004, 000100090000, 'margin', '0.00cm');
insert into shell003 (pindx, eindx, pname, value) values (000100090005, 000100090000, 'border-style', 'solid');
insert into shell003 (pindx, eindx, pname, value) values (000100090006, 000100090000, 'border-width', '0.10cm');
insert into shell003 (pindx, eindx, pname, value) values (000100090007, 000100090000, 'border-color', '#000000');

insert into shell002 (eindx, sname, ename) values (000100100000, 'DEFAULT', '.text_field');
insert into shell003 (pindx, eindx, pname, value) values (000100100001, 000100100000, 'background-color', '#ffffff'); 
insert into shell003 (pindx, eindx, pname, value) values (000100100002, 000100100000, 'padding', '0.10cm');
insert into shell003 (pindx, eindx, pname, value) values (000100100003, 000100100000, 'margin', '0.00cm');
insert into shell003 (pindx, eindx, pname, value) values (000100100004, 000100100000, 'border-style', 'none'); 
insert into shell003 (pindx, eindx, pname, value) values (000100100005, 000100100000, 'font-size', '12pt'); 
insert into shell003 (pindx, eindx, pname, value) values (000100100006, 000100100000, 'font-family', 'monospace');
insert into shell003 (pindx, eindx, pname, value) values (000100100007, 000100100000, 'color', '#000000');

insert into shell002 (eindx, sname, ename) values (000100110000, 'DEFAULT', '.warning_message');
insert into shell003 (pindx, eindx, pname, value) values (000100110001, 000100110000, 'font-size', '12pt');
insert into shell003 (pindx, eindx, pname, value) values (000100110002, 000100110000, 'font-family', 'sans-serif');
insert into shell003 (pindx, eindx, pname, value) values (000100110003, 000100110000, 'background-color', '#ffff00');
insert into shell003 (pindx, eindx, pname, value) values (000100110004, 000100110000, 'color', '#000000');
insert into shell003 (pindx, eindx, pname, value) values (000100110005, 000100110000, 'padding', '0.10cm');
insert into shell003 (pindx, eindx, pname, value) values (000100110006, 000100110000, 'margin', '0.25cm');

insert into shell002 (eindx, sname, ename) values (000100120000, 'DEFAULT', '.status_message');
insert into shell003 (pindx, eindx, pname, value) values (000100120001, 000100120000, 'font-size', '12pt');
insert into shell003 (pindx, eindx, pname, value) values (000100120002, 000100120000, 'font-family', 'sans-serif');
insert into shell003 (pindx, eindx, pname, value) values (000100120003, 000100120000, 'background-color', '#00ff00');
insert into shell003 (pindx, eindx, pname, value) values (000100120004, 000100120000, 'color', '#000000');
insert into shell003 (pindx, eindx, pname, value) values (000100120005, 000100120000, 'padding', '0.10cm');
insert into shell003 (pindx, eindx, pname, value) values (000100120006, 000100120000, 'margin', '0.25cm');

insert into shell002 (eindx, sname, ename) values (000100130000, 'DEFAULT', '.navbar_link');
insert into shell003 (pindx, eindx, pname, value) values (000100130001, 000100130000, 'font-size', '10pt');
insert into shell003 (pindx, eindx, pname, value) values (000100130002, 000100130000, 'font-family', 'sans-serif');
insert into shell003 (pindx, eindx, pname, value) values (000100130003, 000100130000, 'color', '#ffffff');

insert into shell002 (eindx, sname, ename) values (000100140000, 'DEFAULT', '.sh_button');
insert into shell003 (pindx, eindx, pname, value) values (000100140001, 000100140000, 'font-size', '12pt');
insert into shell003 (pindx, eindx, pname, value) values (000100140002, 000100140000, 'font-family', 'sans-serif');
insert into shell003 (pindx, eindx, pname, value) values (000100140003, 000100140000, 'text-align', 'center');
insert into shell003 (pindx, eindx, pname, value) values (000100140004, 000100140000, 'background-color', '#808080');
insert into shell003 (pindx, eindx, pname, value) values (000100140005, 000100140000, 'color', '#ffffff');
insert into shell003 (pindx, eindx, pname, value) values (000100140006, 000100140000, 'font-weight', 'bold');
insert into shell003 (pindx, eindx, pname, value) values (000100140007, 000100140000, 'border-style', 'none');
insert into shell003 (pindx, eindx, pname, value) values (000100140008, 000100140000, 'padding-top', '0.10cm');
insert into shell003 (pindx, eindx, pname, value) values (000100140009, 000100140000, 'padding-bottom', '0.10cm');
insert into shell003 (pindx, eindx, pname, value) values (000100140010, 000100140000, 'padding-left', '0.50cm');
insert into shell003 (pindx, eindx, pname, value) values (000100140011, 000100140000, 'padding-right', '0.50cm');
insert into shell003 (pindx, eindx, pname, value) values (000100140012, 000100140000, 'margin', '0.00cm');

insert into shell002 (eindx, sname, ename) values (000100150000, 'DEFAULT', '.tp_item');
insert into shell003 (pindx, eindx, pname, value) values (000100150001, 000100150000, 'background-color', '#000000');
insert into shell003 (pindx, eindx, pname, value) values (000100150002, 000100150000, 'margin-left', '0.25cm');
insert into shell003 (pindx, eindx, pname, value) values (000100150003, 000100150000, 'margin-right', '0.25cm');
insert into shell003 (pindx, eindx, pname, value) values (000100150004, 000100150000, 'margin-top', '0.00cm');
insert into shell003 (pindx, eindx, pname, value) values (000100150005, 000100150000, 'margin-bottom', '0.00cm');


insert into shell002 (eindx, sname, ename) values (000100160000, 'DEFAULT', '.tp_button_focused');
insert into shell003 (pindx, eindx, pname, value) values (000100160001, 000100160000, 'font-size', '12pt');
insert into shell003 (pindx, eindx, pname, value) values (000100160002, 000100160000, 'font-family', 'sans-serif');
insert into shell003 (pindx, eindx, pname, value) values (000100160003, 000100160000, 'text-align', 'center');
insert into shell003 (pindx, eindx, pname, value) values (000100160004, 000100160000, 'background-color', '#000000');
insert into shell003 (pindx, eindx, pname, value) values (000100160005, 000100160000, 'color', '#ffffff');
insert into shell003 (pindx, eindx, pname, value) values (000100160006, 000100160000, 'font-weight', 'bold');
insert into shell003 (pindx, eindx, pname, value) values (000100160007, 000100160000, 'border-style', 'none');
insert into shell003 (pindx, eindx, pname, value) values (000100160008, 000100160000, 'padding-top', '0.10cm');
insert into shell003 (pindx, eindx, pname, value) values (000100160009, 000100160000, 'padding-bottom', '0.10cm');
insert into shell003 (pindx, eindx, pname, value) values (000100160010, 000100160000, 'padding-left', '0.50cm');
insert into shell003 (pindx, eindx, pname, value) values (000100160011, 000100160000, 'padding-right', '0.50cm');
insert into shell003 (pindx, eindx, pname, value) values (000100160012, 000100160000, 'margin', '0.00cm');

insert into shell002 (eindx, sname, ename) values (000100170000, 'DEFAULT', '.tp_button_unfocused');
insert into shell003 (pindx, eindx, pname, value) values (000100170001, 000100170000, 'font-size', '12pt');
insert into shell003 (pindx, eindx, pname, value) values (000100170002, 000100170000, 'font-family', 'sans-serif');
insert into shell003 (pindx, eindx, pname, value) values (000100170003, 000100170000, 'text-align', 'center');
insert into shell003 (pindx, eindx, pname, value) values (000100170004, 000100170000, 'background-color', '#808080');
insert into shell003 (pindx, eindx, pname, value) values (000100170005, 000100170000, 'color', '#000000');
insert into shell003 (pindx, eindx, pname, value) values (000100170006, 000100170000, 'font-weight', 'bold');
insert into shell003 (pindx, eindx, pname, value) values (000100170007, 000100170000, 'border-style', 'none');
insert into shell003 (pindx, eindx, pname, value) values (000100170008, 000100170000, 'padding-top', '0.10cm');
insert into shell003 (pindx, eindx, pname, value) values (000100170009, 000100170000, 'padding-bottom', '0.10cm');
insert into shell003 (pindx, eindx, pname, value) values (000100170010, 000100170000, 'padding-left', '0.50cm');
insert into shell003 (pindx, eindx, pname, value) values (000100170011, 000100170000, 'padding-right', '0.50cm');
insert into shell003 (pindx, eindx, pname, value) values (000100170012, 000100170000, 'margin', '0.00cm');

insert into shell002 (eindx, sname, ename) values (000100180000, 'DEFAULT', '.tp_first_button_focused');
insert into shell003 (pindx, eindx, pname, value) values (000100180001, 000100180000, 'font-size', '12pt');
insert into shell003 (pindx, eindx, pname, value) values (000100180002, 000100180000, 'font-family', 'sans-serif');
insert into shell003 (pindx, eindx, pname, value) values (000100180003, 000100180000, 'text-align', 'center');
insert into shell003 (pindx, eindx, pname, value) values (000100180004, 000100180000, 'background-color', '#000000');
insert into shell003 (pindx, eindx, pname, value) values (000100180005, 000100180000, 'color', '#ffffff');
insert into shell003 (pindx, eindx, pname, value) values (000100180006, 000100180000, 'font-weight', 'bold');
insert into shell003 (pindx, eindx, pname, value) values (000100180007, 000100180000, 'border-style', 'none');
insert into shell003 (pindx, eindx, pname, value) values (000100180008, 000100180000, 'padding-top', '0.10cm');
insert into shell003 (pindx, eindx, pname, value) values (000100180009, 000100180000, 'padding-bottom', '0.10cm');
insert into shell003 (pindx, eindx, pname, value) values (000100180010, 000100180000, 'padding-left', '0.50cm');
insert into shell003 (pindx, eindx, pname, value) values (000100180011, 000100180000, 'padding-right', '0.50cm');
insert into shell003 (pindx, eindx, pname, value) values (000100180012, 000100180000, 'margin-left', '0.25cm');
insert into shell003 (pindx, eindx, pname, value) values (000100180013, 000100180000, 'margin-right', '0.00cm');
insert into shell003 (pindx, eindx, pname, value) values (000100180014, 000100180000, 'margin-top', '0.00cm');
insert into shell003 (pindx, eindx, pname, value) values (000100180015, 000100180000, 'margin-bottom', '0.00cm');

insert into shell002 (eindx, sname, ename) values (000100190000, 'DEFAULT', '.tp_first_button_unfocused');
insert into shell003 (pindx, eindx, pname, value) values (000100190001, 000100190000, 'font-size', '12pt');
insert into shell003 (pindx, eindx, pname, value) values (000100190002, 000100190000, 'font-family', 'sans-serif');
insert into shell003 (pindx, eindx, pname, value) values (000100190003, 000100190000, 'text-align', 'center');
insert into shell003 (pindx, eindx, pname, value) values (000100190004, 000100190000, 'background-color', '#808080');
insert into shell003 (pindx, eindx, pname, value) values (000100190005, 000100190000, 'color', '#000000');
insert into shell003 (pindx, eindx, pname, value) values (000100190006, 000100190000, 'font-weight', 'bold');
insert into shell003 (pindx, eindx, pname, value) values (000100190007, 000100190000, 'border-style', 'none');
insert into shell003 (pindx, eindx, pname, value) values (000100190008, 000100190000, 'padding-top', '0.10cm');
insert into shell003 (pindx, eindx, pname, value) values (000100190009, 000100190000, 'padding-bottom', '0.10cm');
insert into shell003 (pindx, eindx, pname, value) values (000100190010, 000100190000, 'padding-left', '0.50cm');
insert into shell003 (pindx, eindx, pname, value) values (000100190011, 000100190000, 'padding-right', '0.50cm');
insert into shell003 (pindx, eindx, pname, value) values (000100190012, 000100190000, 'margin-left', '0.25cm');
insert into shell003 (pindx, eindx, pname, value) values (000100190013, 000100190000, 'margin-right', '0.00cm');
insert into shell003 (pindx, eindx, pname, value) values (000100190014, 000100190000, 'margin-top', '0.00cm');
insert into shell003 (pindx, eindx, pname, value) values (000100190015, 000100190000, 'margin-bottom', '0.00cm');

insert into docs001(docid, tname, class) values('STYLE', 'SHELL001', '');
insert into docs003(ename, decim, lngth, etype, upcas) values('STYLE.NAME', 0, 12, 0, 1);
insert into docs003(ename, decim, lngth, etype, upcas) values('STYLE.INDEX', 0, 12, 3, 1);
insert into docs002(iname, docid, index, fname, ename, attrb) values('STYLE.NAME', 'STYLE', 0, 'SNAME', 'STYLE.NAME', '');
insert into docs002(iname, docid, index, fname, ename, attrb) values('STYLE.INDEX', 'STYLE', 1, 'SINDX', 'STYLE.INDEX', '');
insert into docs004(iname, docid) values('STYLE.NAME', 'STYLE');
insert into docs005(tname, docid) values('SHELL001', 'STYLE');

insert into docs001(docid, tname, class) values('STYLE_ELEMENT', 'SHELL002', '');
insert into docs003(ename, decim, lngth, etype, upcas) values('STYLE_ELEMENT.NAME', 0, 12, 0, 1); 
insert into docs002(iname, docid, index, fname, ename, attrb) values('STYLE_ELEMENT.INDEX', 'STYLE_ELEMENT', 0, 'EINDX', 'STYLE.INDEX', '');
insert into docs002(iname, docid, index, fname, ename, attrb) values('STYLE_ELEMENT.NAME', 'STYLE_ELEMENT', 1, 'ENAME', 'STYLE_ELEMENT.NAME', '');
insert into docs002(iname, docid, index, fname, ename, attrb) values('STYLE_ELEMENT.STYLE', 'STYLE_ELEMENT', 2, 'SNAME', 'STYLE.NAME', '');
insert into docs004(iname, docid) values('STYLE_ELEMENT.INDEX', 'STYLE_ELEMENT');
insert into docs005(tname, docid) values('SHELL002', 'STYLE_ELEMENT');

\p initial configuration saved.

commit work;

