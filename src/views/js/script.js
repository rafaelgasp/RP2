function go(loc) {
	if (document.getElementById('telao').src.indexOf(loc) == -1) {
		document.getElementById('telao').src = loc;
	}
}