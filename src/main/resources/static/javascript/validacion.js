


var inputs = document.querySelectorAll(".box-input");

inputs.forEach(function (e) {
  e.addEventListener("keyup", function (event) {
	console.log(event.target.value +"   "+ event.target.name);
    validando(event,solo_letras);
  });
});

function validando(e,funcion) {
  if (funcion(e.target.value, e.target.id)) {
	console.log("Error");
	  mensaje_error_visible(e,"input-incorrecto","input-correcto","");
  } else {
	mensaje_error_visible(e,"input-correcto","input-incorrecto",mensaje[e.target.id]);
  }
}

function solo_letras(texto,opcion) {
	var texto_1 = texto.toUpperCase();
	let verificar = false;
	var validos = (opcion == "dni" || opcion == 'tel') ? "0123456789" :
				( opcion == "password" || opcion == "username" ? "ABCDEFGHIJKLMNÑOPQRSTUVWXYZ0123456789" 
			  : "ABCDEFGHIJKLMNÑOPQRSTUVWXYZ ");
	
	for (let i of texto_1) { verificar = validos.includes(i) ? true : false; if(!verificar){break;}}
	if(verificar){
	  switch(opcion){
		case 'nom':
	     	verificar = (texto_1.length <= 50) ? true: false;
		    break;
		case 'app': case 'apm':
		verificar = (texto_1.length <= 20) ? true: false;
		    break;
		case 'dni':
		    verificar = (texto_1.length == 8) ? true: false;
		    break;
		case 'tel':
		    verificar = (texto_1.length == 9) ? true: false;
		    break;
		case 'username':
			verificar = (texto_1.length>4) ? true : false;
			break;
		default:
			verificar = (texto_1.length>4) ? true : false;
			break;
	  }
	}
	return verificar;
  }
  
  
function mensaje_error_visible(e,remover_input,agregar_input,mensaje){
	document.getElementById(e.target.id).classList.remove(remover_input);
    document.getElementById(e.target.id).classList.add(agregar_input);
	document.getElementById("mensaje-incorrecto-"+e.target.id).textContent = mensaje;
}


var dni = document.getElementById("dni");
dni.addEventListener("input",function(e){
	this.value = solo_numeros(e.target.value);
});

function solo_numeros(texto) {
		let validos = "7894561230";
		let text = "";
		for (let i of texto) {
			text += (validos.includes(i)) ? i : "";
		}
		return text;
}

const mensaje = {
    nom : "El nombre tiene que ser como maximo 50 caracteres y solo puede contener letras.",
    dni : "El Dni tiene que ser de 8 digitos y solo puede contener numeros.",
	app : "El Apellido Paterno tiene que ser como maximo 20 caracteres y solo puede contener letras.",
	apm : "El Apellido Materno tiene que ser como maximo 20 caracteres y solo puede contener letras.",
	username : "El usuario tiene que ser mayor a 4 caracteres alfanumericos",
	password: "La contraseña tiene que ser mayor a 4 caracteres alfanumericos",
	tel: "El telefono tiene que tener 9 digitos",
	file : "La foto tiene que ser extension (png, jpg, jpeg)"
}


//VALIDAR FOTO
var foto = document.getElementById("file");
foto.addEventListener("change",function(){
	var png = /(.png)$/i; var jpg = /(.jpg)$/i; var jpeg = /(.jpeg)$/i
	if(!png.exec(this.value) && !jpg.exec(this.value)  && !jpeg.exec(this.value)){
		document.getElementById("mensaje-incorrecto-file").textContent = mensaje.file;
	}else{
		document.getElementById("mensaje-incorrecto-file").textContent = "";
	}

});