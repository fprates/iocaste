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
insert into shell003 (pindx, eindx, pname, value) values (000100010004, 000100010000, 'margin', '10px');

insert into shell002 (eindx, sname, ename) values (000100020000, 'DEFAULT', '.button');
insert into shell003 (pindx, eindx, pname, value) values (000100020001, 000100020000, 'font-size', '12pt');
insert into shell003 (pindx, eindx, pname, value) values (000100020002, 000100020000, 'font-family', 'sans-serif');
insert into shell003 (pindx, eindx, pname, value) values (000100020003, 000100020000, 'text-align', 'center');
insert into shell003 (pindx, eindx, pname, value) values (000100020004, 000100020000, 'background-color', '#808080');
insert into shell003 (pindx, eindx, pname, value) values (000100020005, 000100020000, 'color', '#ffffff');
insert into shell003 (pindx, eindx, pname, value) values (000100020006, 000100020000, 'font-weight', 'normal');
insert into shell003 (pindx, eindx, pname, value) values (000100020007, 000100020000, 'border-style', 'none');
insert into shell003 (pindx, eindx, pname, value) values (000100020008, 000100020000, 'padding-top', '3px');
insert into shell003 (pindx, eindx, pname, value) values (000100020009, 000100020000, 'padding-bottom', '3px');
insert into shell003 (pindx, eindx, pname, value) values (000100020010, 000100020000, 'padding-left', '20px');
insert into shell003 (pindx, eindx, pname, value) values (000100020011, 000100020000, 'padding-right', '20px');
insert into shell003 (pindx, eindx, pname, value) values (000100020012, 000100020000, 'margin-bottom', '3px');
insert into shell003 (pindx, eindx, pname, value) values (000100020013, 000100020000, 'border-radius', '4px');

insert into shell002 (eindx, sname, ename) values (000100030000, 'DEFAULT', '.item_form_name');
insert into shell003 (pindx, eindx, pname, value) values (000100030001, 000100030000, 'font-size', '12pt');
insert into shell003 (pindx, eindx, pname, value) values (000100030002, 000100030000, 'font-family', 'sans-serif');
insert into shell003 (pindx, eindx, pname, value) values (000100030003, 000100030000, 'text-align', 'right');
insert into shell003 (pindx, eindx, pname, value) values (000100030004, 000100030000, 'background-color', '#000000');
insert into shell003 (pindx, eindx, pname, value) values (000100030005, 000100030000, 'vertical-align', 'middle');
insert into shell003 (pindx, eindx, pname, value) values (000100030006, 000100030000, 'color', '#ffffff');
insert into shell003 (pindx, eindx, pname, value) values (000100030007, 000100030000, 'margin', '0px');
insert into shell003 (pindx, eindx, pname, value) values (000100030008, 000100030000, 'padding', '3px');

insert into shell002 (eindx, sname, ename) values (000100040000, 'DEFAULT', '.error_message');
insert into shell003 (pindx, eindx, pname, value) values (000100040001, 000100040000, 'font-size', '12pt');
insert into shell003 (pindx, eindx, pname, value) values (000100040002, 000100040000, 'font-family', 'sans-serif');
insert into shell003 (pindx, eindx, pname, value) values (000100040003, 000100040000, 'background-color', '#ff0000');
insert into shell003 (pindx, eindx, pname, value) values (000100040004, 000100040000, 'color', '#000000');
insert into shell003 (pindx, eindx, pname, value) values (000100040005, 000100040000, 'padding', '3px');
insert into shell003 (pindx, eindx, pname, value) values (000100040006, 000100040000, 'margin', '0px');

insert into shell002 (eindx, sname, ename) values (000100050000, 'DEFAULT', '.status');
insert into shell003 (pindx, eindx, pname, value) values (000100050001, 000100050000, 'font-size', '10pt');
insert into shell003 (pindx, eindx, pname, value) values (000100050002, 000100050000, 'font-family', 'sans-serif');
insert into shell003 (pindx, eindx, pname, value) values (000100050003, 000100050000, 'background-color', '#000000');
insert into shell003 (pindx, eindx, pname, value) values (000100050004, 000100050000, 'color', '#ffffff');
insert into shell003 (pindx, eindx, pname, value) values (000100050005, 000100050000, 'border-style', 'none');
insert into shell003 (pindx, eindx, pname, value) values (000100050006, 000100050000, 'padding', '3px');
insert into shell003 (pindx, eindx, pname, value) values (000100050007, 000100050000, 'margin', '0px');
                
insert into shell002 (eindx, sname, ename) values (000100060000, 'DEFAULT', '.title');
insert into shell003 (pindx, eindx, pname, value) values (000100060001, 000100060000, 'color', '#ffffff');
insert into shell003 (pindx, eindx, pname, value) values (000100060002, 000100060000, 'font-size', '26pt');
insert into shell003 (pindx, eindx, pname, value) values (000100060003, 000100060000, 'font-weight', 'bold');
insert into shell003 (pindx, eindx, pname, value) values (000100060004, 000100060000, 'margin', '0px');
insert into shell003 (pindx, eindx, pname, value) values (000100060005, 000100060000, 'font-family', 'sans-serif');
                
insert into shell002 (eindx, sname, ename) values (000100070000, 'DEFAULT', '.form');
insert into shell003 (pindx, eindx, pname, value) values (000100070001, 000100070000, 'padding', '0px');
insert into shell003 (pindx, eindx, pname, value) values (000100070002, 000100070000, 'border-collapse', 'collapse');
insert into shell003 (pindx, eindx, pname, value) values (000100070003, 000100070000, 'border-width', '5px');
insert into shell003 (pindx, eindx, pname, value) values (000100070004, 000100070000, 'border-style', 'solid');
insert into shell003 (pindx, eindx, pname, value) values (000100070005, 000100070000, 'border-color', '#000000');
insert into shell003 (pindx, eindx, pname, value) values (000100070006, 000100070000, 'margin-bottom', '5px');
                
insert into shell002 (eindx, sname, ename) values (000100080000, 'DEFAULT', '.header');
insert into shell003 (pindx, eindx, pname, value) values (000100080001, 000100080000, 'background-color', '#000000');
insert into shell003 (pindx, eindx, pname, value) values (000100080002, 000100080000, 'padding', '10px');
insert into shell003 (pindx, eindx, pname, value) values (000100080003, 000100080000, 'margin-bottom', '5px');
insert into shell003 (pindx, eindx, pname, value) values (000100080004, 000100080000, 'border-style', 'none');

insert into shell002 (eindx, sname, ename) values (000100090000, 'DEFAULT', '.form_cell');
insert into shell003 (pindx, eindx, pname, value) values (000100090001, 000100090000, 'vertical-align', 'middle');
insert into shell003 (pindx, eindx, pname, value) values (000100090002, 000100090000, 'background-color', '#000000');
insert into shell003 (pindx, eindx, pname, value) values (000100090003, 000100090000, 'padding', '0px');
insert into shell003 (pindx, eindx, pname, value) values (000100090004, 000100090000, 'margin', '0px');
insert into shell003 (pindx, eindx, pname, value) values (000100090005, 000100090000, 'border-style', 'solid');
insert into shell003 (pindx, eindx, pname, value) values (000100090006, 000100090000, 'border-width', '5px');
insert into shell003 (pindx, eindx, pname, value) values (000100090007, 000100090000, 'border-color', '#000000');
insert into shell003 (pindx, eindx, pname, value) values (000100090008, 000100090000, 'color', '#ffffff');

insert into shell002 (eindx, sname, ename) values (000100100000, 'DEFAULT', '.text_field');
insert into shell003 (pindx, eindx, pname, value) values (000100100001, 000100100000, 'background-color', '#ffffff'); 
insert into shell003 (pindx, eindx, pname, value) values (000100100002, 000100100000, 'padding', '3px');
insert into shell003 (pindx, eindx, pname, value) values (000100100003, 000100100000, 'margin', '0px');
insert into shell003 (pindx, eindx, pname, value) values (000100100004, 000100100000, 'border-style', 'none'); 
insert into shell003 (pindx, eindx, pname, value) values (000100100005, 000100100000, 'font-size', '12pt'); 
insert into shell003 (pindx, eindx, pname, value) values (000100100006, 000100100000, 'font-family', 'monospace');
insert into shell003 (pindx, eindx, pname, value) values (000100100007, 000100100000, 'color', '#000000');

insert into shell002 (eindx, sname, ename) values (000100110000, 'DEFAULT', '.warning_message');
insert into shell003 (pindx, eindx, pname, value) values (000100110001, 000100110000, 'font-size', '12pt');
insert into shell003 (pindx, eindx, pname, value) values (000100110002, 000100110000, 'font-family', 'sans-serif');
insert into shell003 (pindx, eindx, pname, value) values (000100110003, 000100110000, 'background-color', '#ffff00');
insert into shell003 (pindx, eindx, pname, value) values (000100110004, 000100110000, 'color', '#000000');
insert into shell003 (pindx, eindx, pname, value) values (000100110005, 000100110000, 'padding', '3px');
insert into shell003 (pindx, eindx, pname, value) values (000100110006, 000100110000, 'margin', '0px');

insert into shell002 (eindx, sname, ename) values (000100120000, 'DEFAULT', '.status_message');
insert into shell003 (pindx, eindx, pname, value) values (000100120001, 000100120000, 'font-size', '12pt');
insert into shell003 (pindx, eindx, pname, value) values (000100120002, 000100120000, 'font-family', 'sans-serif');
insert into shell003 (pindx, eindx, pname, value) values (000100120003, 000100120000, 'background-color', '#00ff00');
insert into shell003 (pindx, eindx, pname, value) values (000100120004, 000100120000, 'color', '#000000');
insert into shell003 (pindx, eindx, pname, value) values (000100120005, 000100120000, 'padding', '3px');
insert into shell003 (pindx, eindx, pname, value) values (000100120006, 000100120000, 'margin', '0px');

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
insert into shell003 (pindx, eindx, pname, value) values (000100140008, 000100140000, 'height', '22px');
insert into shell003 (pindx, eindx, pname, value) values (000100140009, 000100140000, 'width', '22px');
insert into shell003 (pindx, eindx, pname, value) values (000100140010, 000100140000, 'margin', '0px');
insert into shell003 (pindx, eindx, pname, value) values (000100140011, 000100140000, 'border-radius', '11px');
insert into shell003 (pindx, eindx, pname, value) values (000100140012, 000100140000, 'display', 'inline');

insert into shell002 (eindx, sname, ename) values (000100150000, 'DEFAULT', '.tp_item');
insert into shell003 (pindx, eindx, pname, value) values (000100150001, 000100150000, 'background-color', '#000000');
insert into shell003 (pindx, eindx, pname, value) values (000100150002, 000100150000, 'margin-bottom', '3px');
insert into shell003 (pindx, eindx, pname, value) values (000100150003, 000100150000, 'padding-top', '3px');
insert into shell003 (pindx, eindx, pname, value) values (000100150004, 000100150000, 'padding-bottom', '0px');
insert into shell003 (pindx, eindx, pname, value) values (000100150005, 000100150000, 'padding-left', '3px');
insert into shell003 (pindx, eindx, pname, value) values (000100150006, 000100150000, 'padding-right', '3px');
insert into shell003 (pindx, eindx, pname, value) values (000100150007, 000100150000, 'overflow', 'auto');

insert into shell002 (eindx, sname, ename) values (000100160000, 'DEFAULT', '.tp_button_focused');
insert into shell003 (pindx, eindx, pname, value) values (000100160001, 000100160000, 'font-size', '12pt');
insert into shell003 (pindx, eindx, pname, value) values (000100160002, 000100160000, 'font-family', 'sans-serif');
insert into shell003 (pindx, eindx, pname, value) values (000100160003, 000100160000, 'text-align', 'center');
insert into shell003 (pindx, eindx, pname, value) values (000100160004, 000100160000, 'background-color', '#000000');
insert into shell003 (pindx, eindx, pname, value) values (000100160005, 000100160000, 'color', '#ffffff');
insert into shell003 (pindx, eindx, pname, value) values (000100160006, 000100160000, 'font-weight', 'normal');
insert into shell003 (pindx, eindx, pname, value) values (000100160007, 000100160000, 'border-style', 'none');
insert into shell003 (pindx, eindx, pname, value) values (000100160008, 000100160000, 'padding-top', '3px');
insert into shell003 (pindx, eindx, pname, value) values (000100160009, 000100160000, 'padding-bottom', '3px');
insert into shell003 (pindx, eindx, pname, value) values (000100160010, 000100160000, 'padding-left', '20px');
insert into shell003 (pindx, eindx, pname, value) values (000100160011, 000100160000, 'padding-right', '20px');
insert into shell003 (pindx, eindx, pname, value) values (000100160012, 000100160000, 'margin', '0px');

insert into shell002 (eindx, sname, ename) values (000100170000, 'DEFAULT', '.tp_button_unfocused');
insert into shell003 (pindx, eindx, pname, value) values (000100170001, 000100170000, 'font-size', '12pt');
insert into shell003 (pindx, eindx, pname, value) values (000100170002, 000100170000, 'font-family', 'sans-serif');
insert into shell003 (pindx, eindx, pname, value) values (000100170003, 000100170000, 'text-align', 'center');
insert into shell003 (pindx, eindx, pname, value) values (000100170004, 000100170000, 'background-color', '#808080');
insert into shell003 (pindx, eindx, pname, value) values (000100170005, 000100170000, 'color', '#000000');
insert into shell003 (pindx, eindx, pname, value) values (000100170006, 000100170000, 'font-weight', 'normal');
insert into shell003 (pindx, eindx, pname, value) values (000100170007, 000100170000, 'border-style', 'none');
insert into shell003 (pindx, eindx, pname, value) values (000100170008, 000100170000, 'padding-top', '3px');
insert into shell003 (pindx, eindx, pname, value) values (000100170009, 000100170000, 'padding-bottom', '3px');
insert into shell003 (pindx, eindx, pname, value) values (000100170010, 000100170000, 'padding-left', '20px');
insert into shell003 (pindx, eindx, pname, value) values (000100170011, 000100170000, 'padding-right', '20px');
insert into shell003 (pindx, eindx, pname, value) values (000100170012, 000100170000, 'margin', '0px');

insert into shell002 (eindx, sname, ename) values (000100180000, 'DEFAULT', '.table_header');
insert into shell003 (pindx, eindx, pname, value) values (000100180001, 000100180000, 'font-size', '12pt');
insert into shell003 (pindx, eindx, pname, value) values (000100180002, 000100180000, 'font-family', 'sans-serif');
insert into shell003 (pindx, eindx, pname, value) values (000100180003, 000100180000, 'text-align', 'center');
insert into shell003 (pindx, eindx, pname, value) values (000100180004, 000100180000, 'background-color', '#808080');
insert into shell003 (pindx, eindx, pname, value) values (000100180005, 000100180000, 'color', '#ffffff');
insert into shell003 (pindx, eindx, pname, value) values (000100180006, 000100180000, 'font-weight', 'normal');
insert into shell003 (pindx, eindx, pname, value) values (000100180007, 000100180000, 'padding', '3px');
insert into shell003 (pindx, eindx, pname, value) values (000100180008, 000100180000, 'margin', '0px');

insert into shell002 (eindx, sname, ename) values (000100190000, 'DEFAULT', '.table_cell');
insert into shell003 (pindx, eindx, pname, value) values (000100190001, 000100190000, 'background-color', '#000000');
insert into shell003 (pindx, eindx, pname, value) values (000100190002, 000100190000, 'color', '#ffffff');
insert into shell003 (pindx, eindx, pname, value) values (000100190003, 000100190000, 'padding', '3px');
insert into shell003 (pindx, eindx, pname, value) values (000100190004, 000100190000, 'margin', '0px');

insert into shell002 (eindx, sname, ename) values (000100200000, 'DEFAULT', '.table_area');
insert into shell003 (pindx, eindx, pname, value) values (000100200001, 000100200000, 'background-color', '#000000');
insert into shell003 (pindx, eindx, pname, value) values (000100200002, 000100200000, 'border-style', 'solid');
insert into shell003 (pindx, eindx, pname, value) values (000100200003, 000100200000, 'border-collapse', 'collapse');
insert into shell003 (pindx, eindx, pname, value) values (000100200004, 000100200000, 'margin-bottom', '5px');
insert into shell003 (pindx, eindx, pname, value) values (000100200005, 000100200000, 'padding', '0px');
insert into shell003 (pindx, eindx, pname, value) values (000100200006, 000100200000, 'border-width', '1px');
insert into shell003 (pindx, eindx, pname, value) values (000100200007, 000100200000, 'border-color', '#808080');

insert into shell002 (eindx, sname, ename) values (000100210000, 'DEFAULT', '.link:link');
insert into shell003 (pindx, eindx, pname, value) values (000100210001, 000100210000, 'color', '#ffffff');
insert into shell003 (pindx, eindx, pname, value) values (000100210002, 000100210000, 'font-family', 'sans-serif');
insert into shell003 (pindx, eindx, pname, value) values (000100210003, 000100210000, 'font-weight', 'normal');
insert into shell003 (pindx, eindx, pname, value) values (000100210004, 000100210000, 'font-size', '12pt');
insert into shell003 (pindx, eindx, pname, value) values (000100210005, 000100210000, 'text-decoration', 'underline');

insert into shell002 (eindx, sname, ename) values (000100220000, 'DEFAULT', '.link:visited');
insert into shell003 (pindx, eindx, pname, value) values (000100220001, 000100220000, 'color', '#ffffff');
insert into shell003 (pindx, eindx, pname, value) values (000100220002, 000100220000, 'font-family', 'sans-serif');
insert into shell003 (pindx, eindx, pname, value) values (000100220003, 000100220000, 'font-weight', 'normal');
insert into shell003 (pindx, eindx, pname, value) values (000100220004, 000100220000, 'font-size', '12pt');
insert into shell003 (pindx, eindx, pname, value) values (000100220005, 000100220000, 'text-decoration', 'underline');

insert into shell002 (eindx, sname, ename) values (000100230000, 'DEFAULT', '.link:hover');
insert into shell003 (pindx, eindx, pname, value) values (000100230001, 000100230000, 'color', '#808080');
insert into shell003 (pindx, eindx, pname, value) values (000100230002, 000100230000, 'font-family', 'sans-serif');
insert into shell003 (pindx, eindx, pname, value) values (000100230003, 000100230000, 'font-weight', 'normal');
insert into shell003 (pindx, eindx, pname, value) values (000100230004, 000100230000, 'font-size', '12pt');
insert into shell003 (pindx, eindx, pname, value) values (000100230005, 000100230000, 'text-decoration', 'none');

insert into shell002 (eindx, sname, ename) values (000100240000, 'DEFAULT', '.link:active');
insert into shell003 (pindx, eindx, pname, value) values (000100240001, 000100240000, 'color', '#ffffff');
insert into shell003 (pindx, eindx, pname, value) values (000100240002, 000100240000, 'font-family', 'sans-serif');
insert into shell003 (pindx, eindx, pname, value) values (000100240003, 000100240000, 'font-weight', 'normal');
insert into shell003 (pindx, eindx, pname, value) values (000100240004, 000100240000, 'font-size', '12pt');
insert into shell003 (pindx, eindx, pname, value) values (000100240005, 000100240000, 'text-decoration', 'underline');

insert into shell002 (eindx, sname, ename) values (000100250000, 'DEFAULT', '.frame');
insert into shell003 (pindx, eindx, pname, value) values (000100250001, 000100250000, 'color', '#ffffff');
insert into shell003 (pindx, eindx, pname, value) values (000100250002, 000100250000, 'background-color', '#000000');
insert into shell003 (pindx, eindx, pname, value) values (000100250003, 000100250000, 'font-family', 'sans-serif');
insert into shell003 (pindx, eindx, pname, value) values (000100250004, 000100250000, 'font-size', '11pt');
insert into shell003 (pindx, eindx, pname, value) values (000100250005, 000100250000, 'font-weight', 'normal');
insert into shell003 (pindx, eindx, pname, value) values (000100250006, 000100250000, 'margin-bottom', '5px');

insert into shell002 (eindx, sname, ename) values (000100260000, 'DEFAULT', '.expand_bar');
insert into shell003 (pindx, eindx, pname, value) values (000100260001, 000100260000, 'color', '#ffffff');
insert into shell003 (pindx, eindx, pname, value) values (000100260002, 000100260000, 'background-color', '#000000');
insert into shell003 (pindx, eindx, pname, value) values (000100260003, 000100260000, 'border-width', '1px');
insert into shell003 (pindx, eindx, pname, value) values (000100260004, 000100260000, 'border-style', 'solid');
insert into shell003 (pindx, eindx, pname, value) values (000100260005, 000100260000, 'border-collapse', 'collapse');
insert into shell003 (pindx, eindx, pname, value) values (000100260006, 000100260000, 'border-color', '#808080');
insert into shell003 (pindx, eindx, pname, value) values (000100260007, 000100260000, 'padding-top', '3px');
insert into shell003 (pindx, eindx, pname, value) values (000100260008, 000100260000, 'padding-bottom', '0px');
insert into shell003 (pindx, eindx, pname, value) values (000100260009, 000100260000, 'padding-left', '3px');
insert into shell003 (pindx, eindx, pname, value) values (000100260010, 000100260000, 'padding-right', '3px');
insert into shell003 (pindx, eindx, pname, value) values (000100260011, 000100260000, 'margin-bottom', '3px');
insert into shell003 (pindx, eindx, pname, value) values (000100260012, 000100260000, 'overflow', 'auto');

insert into shell002 (eindx, sname, ename) values (000100270000, 'DEFAULT', '.eb_edge');
insert into shell003 (pindx, eindx, pname, value) values (000100270001, 000100270000, 'color', '#ffffff');
insert into shell003 (pindx, eindx, pname, value) values (000100270002, 000100270000, 'background-color', '#808080');
insert into shell003 (pindx, eindx, pname, value) values (000100270003, 000100270000, 'border-width', '0px');
insert into shell003 (pindx, eindx, pname, value) values (000100270004, 000100270000, 'border-style', 'none');
insert into shell003 (pindx, eindx, pname, value) values (000100270005, 000100270000, 'border-collapse', 'collapse');
insert into shell003 (pindx, eindx, pname, value) values (000100270006, 000100270000, 'border-color', '#000000');
insert into shell003 (pindx, eindx, pname, value) values (000100270007, 000100270000, 'padding', '3px');
insert into shell003 (pindx, eindx, pname, value) values (000100270008, 000100270000, 'margin-bottom', '0px');
insert into shell003 (pindx, eindx, pname, value) values (000100270009, 000100270000, 'width', '100%');
insert into shell003 (pindx, eindx, pname, value) values (000100270010, 000100270000, 'font-family', 'sans-serif');
insert into shell003 (pindx, eindx, pname, value) values (000100270011, 000100270000, 'font-size', '12pt');
insert into shell003 (pindx, eindx, pname, value) values (000100270012, 000100270000, 'font-weight', 'normal');
insert into shell003 (pindx, eindx, pname, value) values (000100270013, 000100270000, 'text-align', 'left');

insert into shell002 (eindx, sname, ename) values (000100280000, 'DEFAULT', '.text_field_disabled');
insert into shell003 (pindx, eindx, pname, value) values (000102800001, 000100280000, 'background-color', '#ffffff'); 
insert into shell003 (pindx, eindx, pname, value) values (000102800002, 000100280000, 'padding', '3px');
insert into shell003 (pindx, eindx, pname, value) values (000102800003, 000100280000, 'margin', '0px');
insert into shell003 (pindx, eindx, pname, value) values (000102800004, 000100280000, 'border-style', 'none');
insert into shell003 (pindx, eindx, pname, value) values (000102800005, 000100280000, 'font-size', '12pt'); 
insert into shell003 (pindx, eindx, pname, value) values (000102800006, 000100280000, 'font-family', 'monospace');
insert into shell003 (pindx, eindx, pname, value) values (000102800007, 000100280000, 'color', '#808080');

insert into shell002 (eindx, sname, ename) values (000100290000, 'DEFAULT', '.list_box');
insert into shell003 (pindx, eindx, pname, value) values (000102900001, 000100290000, 'background-color', '#ffffff'); 
insert into shell003 (pindx, eindx, pname, value) values (000102900002, 000100290000, 'color', '#000000');
insert into shell003 (pindx, eindx, pname, value) values (000102900003, 000100290000, 'border-style', 'none');
insert into shell003 (pindx, eindx, pname, value) values (000102990004, 000100290000, 'font-family', 'sans-serif');
insert into shell003 (pindx, eindx, pname, value) values (000100290005, 000100290000, 'font-weight', 'normal');
insert into shell003 (pindx, eindx, pname, value) values (000100290006, 000100290000, 'font-size', '12pt');
insert into shell003 (pindx, eindx, pname, value) values (000100290007, 000100290000, 'padding', '3px');

insert into shell002 (eindx, sname, ename) values (000100300000, 'DEFAULT', '.list_box_disabled');
insert into shell003 (pindx, eindx, pname, value) values (000100300001, 000100300000, 'background-color', '#ffffff');
insert into shell003 (pindx, eindx, pname, value) values (000100300002, 000100300000, 'color', '#808080');
insert into shell003 (pindx, eindx, pname, value) values (000100300003, 000100300000, 'border-style', 'none');
insert into shell003 (pindx, eindx, pname, value) values (000100300004, 000100300000, 'font-family', 'sans-serif');
insert into shell003 (pindx, eindx, pname, value) values (000100300005, 000100300000, 'font-weight', 'normal');
insert into shell003 (pindx, eindx, pname, value) values (000100300006, 000100300000, 'font-size', '12pt');
insert into shell003 (pindx, eindx, pname, value) values (000100300007, 000100300000, 'padding', '3px');

insert into shell002 (eindx, sname, ename) values (000100310000, 'DEFAULT', '.output_list');
insert into shell003 (pindx, eindx, pname, value) values (000100310001, 000100310000, 'background-color', 'transparent');
insert into shell003 (pindx, eindx, pname, value) values (000100310002, 000100310000, 'color', '#ffffff');
insert into shell003 (pindx, eindx, pname, value) values (000100310003, 000100310000, 'font-family', 'monospace');
insert into shell003 (pindx, eindx, pname, value) values (000100310004, 000100310000, 'font-weight', 'normal');
insert into shell003 (pindx, eindx, pname, value) values (000100310005, 000100310000, 'font-size', '10pt');

insert into shell002 (eindx, sname, ename) values (000100320000, 'DEFAULT', '.text');
insert into shell003 (pindx, eindx, pname, value) values (000100320001, 000100320000, 'font-size', '12pt');
insert into shell003 (pindx, eindx, pname, value) values (000100320002, 000100320000, 'font-family', 'sans-serif');
insert into shell003 (pindx, eindx, pname, value) values (000100320003, 000100320000, 'text-align', 'left');
insert into shell003 (pindx, eindx, pname, value) values (000100320004, 000100320000, 'background-color', 'transparent');
insert into shell003 (pindx, eindx, pname, value) values (000100320005, 000100320000, 'vertical-align', 'middle');
insert into shell003 (pindx, eindx, pname, value) values (000100320006, 000100320000, 'color', '#ffffff');
insert into shell003 (pindx, eindx, pname, value) values (000100320007, 000100320000, 'margin', '0px');
insert into shell003 (pindx, eindx, pname, value) values (000100320008, 000100320000, 'padding', '3px');

insert into shell002 (eindx, sname, ename) values (000100330000, 'DEFAULT', '.tftext');
insert into shell003 (pindx, eindx, pname, value) values (000100330001, 000100330000, 'font-size', '12pt');
insert into shell003 (pindx, eindx, pname, value) values (000100330002, 000100330000, 'font-family', 'sans-serif');
insert into shell003 (pindx, eindx, pname, value) values (000100330003, 000100330000, 'text-align', 'left');
insert into shell003 (pindx, eindx, pname, value) values (000100330004, 000100330000, 'background-color', 'transparent');
insert into shell003 (pindx, eindx, pname, value) values (000100330005, 000100330000, 'vertical-align', 'middle');
insert into shell003 (pindx, eindx, pname, value) values (000100330006, 000100330000, 'color', '#ffffff');
insert into shell003 (pindx, eindx, pname, value) values (000100330007, 000100330000, 'margin', '0px');
insert into shell003 (pindx, eindx, pname, value) values (000100330008, 000100330000, 'padding', '3px');
insert into shell003 (pindx, eindx, pname, value) values (000100330009, 000100330000, 'display', 'inline');

insert into shell002 (eindx, sname, ename) values (000100340000, 'DEFAULT', '.imglink:link');
insert into shell003 (pindx, eindx, pname, value) values (000100340001, 000100340000, 'color', 'transparent');
insert into shell002 (eindx, sname, ename) values (000100350000, 'DEFAULT', '.imglink:visited');
insert into shell003 (pindx, eindx, pname, value) values (000100350001, 000100350000, 'color', 'transparent');
insert into shell002 (eindx, sname, ename) values (000100360000, 'DEFAULT', '.imglink:hover');
insert into shell003 (pindx, eindx, pname, value) values (000100360001, 000100360000, 'color', 'transparent');
insert into shell002 (eindx, sname, ename) values (000100370000, 'DEFAULT', '.imglink:active');
insert into shell003 (pindx, eindx, pname, value) values (000100370001, 000100370000, 'color', 'transparent');

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

