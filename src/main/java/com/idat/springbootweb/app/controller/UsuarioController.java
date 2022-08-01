package com.idat.springbootweb.app.controller;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.idat.springbootweb.app.entity.Usuario;
import com.idat.springbootweb.app.service.IRolService;
import com.idat.springbootweb.app.service.IUsuarioService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class UsuarioController {
	@Autowired
	private IRolService rolDao;
	@Autowired
	private IUsuarioService usuarioDao;
	
	@GetMapping("/listar/usuario")
	//VERIFICAR SIEMPRE LAS IMPORTACIONES QUE PODEMOS IMPORTAR UNO QUE NO ES Y VA A MANDAR NULL
	public String listar_empleado(Model model) {
		model.addAttribute("usuarios", usuarioDao.listarUsuario());
		model.addAttribute("buscar", "");
		return "usuario/listar";
	}
	@GetMapping("/listar/usuario/buscar")
	public String buscador(Model model,
			@RequestParam String nombre) {
		model.addAttribute("usuarios",usuarioDao.buscar_x_nombre(nombre));
		model.addAttribute("buscar", nombre);
		return "usuario/listar";
	}

	
	@GetMapping("/formulario/usuario")
	public String registrar_usuario(Model model) {
		model.addAttribute("titulo", "Registrar Usuario");
		model.addAttribute("usuario",new Usuario());
		model.addAttribute("roles", rolDao.listarRol());
		return "usuario/formulario";
	}
	
	
	@PostMapping("/formulario/usuario")
	public String guardar_usuario(Model model,@Valid Usuario usuario,Errors errores,
			@RequestParam String rol,@RequestParam String genero,
			 @RequestParam("file") MultipartFile foto,BindingResult result) throws IOException {
		
		model.addAttribute("titulo", "Registrar Usuario");
		model.addAttribute("roles", rolDao.listarRol());
		usuario.setEstado("ACTIVO");
		usuario.setGenero(genero);
		if(usuario.getId()==null) {
			if(usuarioDao.buscar_x_dni(usuario.getDni()) != null) {
				model.addAttribute("dni_existente","DNI EXISTENTE, DIGITE OTRO DNI");
				model.addAttribute("usuario", usuario);
				return "usuario/formulario";
			}
		}

			if(errores.hasErrors()) {
				model.addAttribute("titulo", "Registrar Usuario");
				return "usuario/formulario";
			}
			if(result.hasErrors()) {
				model.addAttribute("usuario",usuario);
				model.addAttribute("titulo", "Error Usuario");
				return "usuario/formulario";
			}
			if(!foto.isEmpty()) {

				if(foto.getContentType().toString().equalsIgnoreCase("image/png")|| 
						foto.getContentType().toString().equalsIgnoreCase("image/jpg") ||
						foto.getContentType().toString().equalsIgnoreCase("image/jpeg")) {
					Path directorioRecursos = Paths.get("src//main//resources//static/uploads");	
					String rootPath = directorioRecursos.toFile().getAbsolutePath();
					Path p = Paths.get(rootPath);
					Files.createDirectories(p.getParent());
					log.info(foto.getContentType());
					try {
						byte[] bytes = foto.getBytes();
						Path rutaCompleta=Paths.get(rootPath+"//"+foto.getOriginalFilename());
						Files.createDirectories(rutaCompleta.getParent());
						Files.write(rutaCompleta,bytes);
						usuario.setFoto(foto.getOriginalFilename());
					} catch (Exception e) {
						e.printStackTrace();
					}
				}else {
					model.addAttribute("tipo_foto","La foto tiene que ser extension (png, jpg, jpeg)");
					return "usuario/formulario";
				}
			}
			var rol_encontrado = rolDao.editarRol(Long.valueOf(rol));
			usuario.setRol(rol_encontrado);
			if(usuarioDao.crearUsuario(usuario) == 1) {
				model.addAttribute("mensaje", "(Usuario Registro Correctamente)");
			}else {
				model.addAttribute("mensaje", "(Error al registrar)");
			}
			
			model.addAttribute("usuario",new Usuario());
			return "usuario/formulario";
		
		
	}
	
	@GetMapping(value="/ver/usuario/{id}")
	public String ver(@PathVariable(value="id") Long id, Model model) {
		Usuario user = usuarioDao.editarUsuario(id);
		if(user==null) {
			return "redirect:/listar/usuario";
		}
		model.addAttribute("usuario", user);
		model.addAttribute("titulo", "Detalle Usuario:"+user.getNombres());
		return "usuario/ver";
	}
	
	@GetMapping(value="/formulario/usuario/{id}")
	public String editar_empleado(@PathVariable(value="id") Long id, Model model) {
		Usuario user = null;
		if(id>0) {
			user=usuarioDao.editarUsuario(id);
		}else {
			return "redirect:/listar/usuario";
		}
		model.addAttribute("titulo", "Actualizar Usuario");
		model.addAttribute("usuario",user);
		model.addAttribute("roles", rolDao.listarRol());
		return "usuario/formulario";		
	}
	
	@GetMapping(value="/eliminar/usuario/{id}")
	public String eliminar(@PathVariable(value="id") Long id) {
		if(id>0) {
			usuarioDao.eliminarEliminar(id);
		}
		return "redirect:/listar/usuario";
	}
}
