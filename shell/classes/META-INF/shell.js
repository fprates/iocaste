function setValue(name, value) {
	param = document.getElementById(name);
	param.value = value;
}

function defineAction(actionname) {
	setValue('action', actionname);
}