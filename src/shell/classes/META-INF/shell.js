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
    
    action.value = value;
    form.submit();
}

function revertElementDisplay(id) {
    var display = document.getElementById(id).style.display;
    
    setElementDisplay(id, (display == "none")? "block" : "none");
}

function send(actionname, args, response) {
    var pagetrack = document.getElementById("pagetrack").value;
    var url = "index.html";
    var param = "pagetrack="+pagetrack+"&action="+actionname;
    var xmlhttp = new XMLHttpRequest();
    
    if (args != null)
        param += args;
    
    xmlhttp.onreadystatechange = function() {
        sendCallback(actionname, response, xmlhttp);
    }
    
    xmlhttp.open("POST", url, true);
    xmlhttp.setRequestHeader("Content-type",
    		"application/x-www-form-urlencoded");
    xmlhttp.setRequestHeader("Content-length", param.length);
    xmlhttp.setRequestHeader("Connection", "close")
    xmlhttp.send(param);
}

function sendCallback(action, area, xmlhttp) {
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

function setFieldSh(name, value) {
	setValue(name, value);
	document.getElementById(name).focus();
	closeSh();
}

function setValue(name, value) {
	var param = document.getElementById(name);
	param.value = value;
}
