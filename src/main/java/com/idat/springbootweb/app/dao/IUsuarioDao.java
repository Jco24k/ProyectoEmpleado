package com.idat.springbootweb.app.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.idat.springbootweb.app.entity.Usuario;

public interface IUsuarioDao extends JpaRepository<Usuario,Long> {
	public Usuario findByDni(String dni);
	public List<Usuario> findByNombresStartingWith(String nombre);
}
