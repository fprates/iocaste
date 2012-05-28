function setValue(name, value) {
	var param = document.getElementById(name);
	param.value = value;
}

function defineAction(action, actionname) {
	setValue(action, actionname);
}

function revertElementDisplay(id) {
    var display = document.getElementById(id).style.display;
    
    setElementDisplay(id, (display == "none")? "block" : "none");
}

function send(actionname, args, response) {
    var pagetrack = document.getElementById("pagetrack").value;
    var url = "index.html?pagetrack="+pagetrack+"&action="+actionname;
    var xmlhttp = new XMLHttpRequest();
    
    if (args != null)
        url += args;
    
    xmlhttp.onreadystatechange = function() {
        sendCallback(actionname, response, xmlhttp);
    }
    
    xmlhttp.open("POST", url, true);
    xmlhttp.send();
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

function submit(formname, actionname, value) {
    var form = document.getElementById(formname);
    var action = document.getElementBydId(actionname);
    
    action.value = value;
    form.submit();
}