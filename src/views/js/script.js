var programa;

function go(loc) {
	window.programa = document.getElementById("lista-programa").value;
	var opcao = document.getElementById('telao').src.indexOf(loc);
	
	if (programa != "Selecione..." && opcao == -1) {
		document.getElementById('telao').src = loc;
	}
}

function proximaEtapa() {
	window.programa = document.getElementById("lista-programa").value;
	
	if(programa != "Selecione...") {
		go("relatorios/escolha_acao.html");
	}
	else {
		go("relatorios/escolha_programa.html");
	}
}

function recebePrograma() {
	document.getElementById('graf').style.backgroundImage = "url('../imagens/graf-The Voice Brasil.png')";
}