function setValue(name, value) {
	param = document.getElementById(name);
	param.value = value;
}

function defineAction(actionname) {
	setValue('action', actionname);
}

function search(actionname, pagetrack) {
    action = actionname;
    var url = "index.html?pagetrack="+pagetrack+"&action="+action;
    
    xmlhttp=XMLHttpRequest();
    xmlhttp.onreadystatechange = function() {
        searchCallback(actionname, xmlhttp);
    }
    xmlhttp.open("POST", url, true);
    xmlhttp.send();
}

function searchCallback(action, xmlhttp) {
    if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
        document.getElementById(action+".area").innerHTML=xmlhttp.responseText;
    }
}
