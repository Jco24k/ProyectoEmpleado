package com.idat.springbootweb.app.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.idat.springbootweb.app.dao.IUsuarioDao;
import com.idat.springbootweb.app.entity.Usuario;


@Service
public class UsuarioServiceImpl implements IUsuarioService {

	@Autowired
	private IUsuarioDao usuarioDao;
	

	@Override
	@Transactional 
	public int crearUsuario(Usuario usuario) {	
		try {
			usuarioDao.save(usuario);
			return 1;
		} catch (Exception e) {
			return 0;
		}
	}


	@Override
	@Transactional
	public Usuario editarUsuario(Long id) {
		return usuarioDao.findById(id).orElse(null);
	}


	@Override
	@Transactional
	public Usuario buscar_x_dni(String dni) {
		return usuarioDao.findByDni(dni);
	}


	@Override
	public List<Usuario> listarUsuario() {
		// TODO Auto-generated method stub
		return (List<Usuario>) usuarioDao.findAll();
	}


	@Override
	public void eliminarEliminar(Long id) {
		usuarioDao.deleteById(id);	
	}


	@Override
	public List<Usuario> buscar_x_nombre(String nombre) {
		// TODO Auto-generated method stub
		return (List<Usuario>) usuarioDao.findByNombresStartingWith(nombre);
	}


}
