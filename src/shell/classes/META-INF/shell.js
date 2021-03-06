var global_form = "";
var global_action = "";
var check_double_submit = false;

function closeCal() {
    var cal = document.getElementById('calstdcnt');
    cal.style.display='none';
}

function closeSh() {
	var sh = document.getElementById('shstdcnt');
	sh.style.display='none';
}

function defineAction(action, actionname) {
	setValue(action, actionname);
}

function formSubmit(formname, actionname, value) {
    var form = document.getElementById(formname);
    var action = document.getElementById(actionname);
    
    setClassStyle('screen_lock', 'screen_locked');
    action.value = value;
    form.submit();
}

function formSubmitNoLock(formname, actionname, value) {
    var form = document.getElementById(formname);
    var action = document.getElementById(actionname);
    
    action.value = value;
    form.submit();
}

function getClassStyle(id) {
    return document.getElementById(id).className;
}

function revertElementDisplay(id) {
    var display = document.getElementById(id).style.display;
    
    setElementDisplay(id, (display == "none")? "block" : "none");
}

function _send(actionname, args, response) {
    var pagetrack = document.getElementById("pagetrack").value;
    var url = "index.html";
    var param = "pagetrack="+pagetrack+"&action="+actionname;
    var xmlhttp = new XMLHttpRequest();
    
    if (args != null)
        param += args;
    
    xmlhttp.onreadystatechange = function() {
        _sendCallback(actionname, response, xmlhttp);
    }
    
    xmlhttp.open("POST", url, true);
    xmlhttp.setRequestHeader("Content-type",
    		"application/x-www-form-urlencoded");
    xmlhttp.setRequestHeader("Content-length", param.length);
    xmlhttp.setRequestHeader("Connection", "close")
    xmlhttp.send(param);
}

function _sendCallback(action, area, xmlhttp) {
    if (area == null)
        return;
        
    if (xmlhttp.readyState == 4 && xmlhttp.status == 200)
        document.getElementById(area).innerHTML=xmlhttp.responseText;
}

function setClassStyle(id, classname) {
    document.getElementById(id).className = classname;
}

function setElementDisplay(id, state) {
    document.getElementById(id).style.display = state;
}

function setElementDisplayOfClass(style, display) {
   var elements = document.querySelectorAll(style);
   for (var i = 0; i < elements.length; i++) {
      elements[i].style.display = display;
   }
}

function setFieldCal(name, value) {
    var input = document.getElementById(name);
    if (!input.readOnly)
        setValue(name, value);
    
    input.focus();
    closeCal();
}

function setFieldSh(name, value) {
    var input = document.getElementById(name);
    if (!input.readOnly)
        setValue(name, value);
    
    input.focus();
    closeSh();
}

function setValue(name, value) {
	var param = document.getElementById(name);
	param.value = value;
}

function setGlobalContext(form, action) {
    global_form = form;
    global_action = action;
}

window.onpopstate = function(event) {
   if (check_double_submit)
      return;
   check_double_submit = true;
   formSubmit(global_form, global_action, "back");
   history.replaceState(null, null, "");
};

window.onclick = function(event) {
  if ((event.target.className == 'nc_login_menu') ||
      (event.target.className == 'ctxmenu') ||
      (event.target.className == 'button_ctxmenu_open'))
     return;
  setElementDisplayOfClass('.button_ctxmenu_close','none');
  setElementDisplayOfClass('.button_ctxmenu_open','inline');
  setElementDisplayOfClass('.ctxmenu', 'none');
  setElementDisplay('nc_login_options', 'none');
}

history.pushState(null, null, "");
