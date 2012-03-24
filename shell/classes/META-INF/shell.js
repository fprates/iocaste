function setValue(name, value) {
	param = document.getElementById(name);
	param.value = value;
}

function defineAction(action, actionname) {
	setValue(action, actionname);
}

function send(actionname) {
    pagetrack = document.getElementById('pagetrack').value;
    var url = 'index.html?pagetrack='+pagetrack+'&action='+actionname;
    
    xmlhttp=XMLHttpRequest();
    xmlhttp.onreadystatechange = function() {
        sendCallback(actionname, xmlhttp);
    }
    xmlhttp.open("POST", url, true);
    xmlhttp.send();
}

function sendCallback(action, xmlhttp) {
/*    if (xmlhttp.readyState == 4 && xmlhttp.status == 200)
 *        document.getElementById(action+".area").innerHTML=xmlhttp.responseText;
 */
}

function setElementDisplay(id, state) {
    document.getElementById(id).style.display = state;
    sendAction(
}

function revertElementDisplay(id) {
    var display = document.getElementById(id).style.display;
    
    setElementDisplay(id, (display == 'none')? 'block' : 'none');
}
