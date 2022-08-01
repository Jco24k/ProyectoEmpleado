var btn = document.createElement("a");
btn.style.display = "none";
btn.setAttribute("class", "btn-enviar");
var texto = document.getElementById("nombre");
texto.focus();
texto.selectionStart = texto.selectionEnd = texto.value.length;
