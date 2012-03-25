function setValue(name, value) {
	param = document.getElementById(name);
	param.value = value;
}

function defineAction(action, actionname) {
	setValue(action, actionname);
}

function send(actionname, response) {
    pagetrack = document.getElementById("pagetrack").value;
    var url = "index.html?pagetrack="+pagetrack+"&action="+actionname;
    var xmlhttp = XMLHttpRequest();
    
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

function setElementDisplay(id, state) {
    document.getElementById(id).style.display = state;
}

function revertElementDisplay(id) {
    var display = document.getElementById(id).style.display;
    
    setElementDisplay(id, (display == "none")? "block" : "none");
}
