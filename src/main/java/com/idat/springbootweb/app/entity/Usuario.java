package com.idat.springbootweb.app.entity;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Data;

@Entity
@Data
@Table(name="usuario")
public class Usuario implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	
	@Size( max = 50, message="El nombre solo puede contener un maximo de 50 caracteres ")
	@Pattern(regexp = "[a-zA-Z\s]+", message="El nombre solo puede contener letras")
	private String nombres;
	
	
	@Size(min = 8, max = 8, message="El dni debe contener 8 digitos")
	@Pattern(regexp = "[0-9]+", message="El dni solo puede contener numeros")
	private String dni;
	
	
	@Size( max = 20, message="El apellido solo puede contener un maximo de 20 caracteres ")
	@Pattern(regexp = "[a-zA-Z\s]+", message="El apellido solo puede contener letras")
	@Column(name = "ap_paterno")
	private String app;
	
	
	@Column(name = "ap_materno")
	@Size( max = 20, message="El apellido solo puede contener un maximo de 20 caracteres ")
	@Pattern(regexp = "[a-zA-Z\s]+", message="El apellido solo puede contener letras")
	private String apm;
	
	
	@Column(name = "fecha_nac")
	private Date fecha;
	
	
	@Size(min = 9, max = 9, message="El telefono debe contener 9 digitos")
	@Pattern(regexp = "[0-9]+", message="El telefono solo puede contener numeros")
	@Column(name = "telefono")
	private String tel;
	
	private String foto;
	
	@Email(message = "Correo debe contener el signo @")
	private String correo;
	
	
	@Size(min = 4, max = 50, message="El usuario debe contener minimo 4 y un maximo de 50 caracteres o digitos")
	@Pattern(regexp = "[a-zA-Z0-9]+", message="El usuario solo puede contener caracteres alfanumericos")
	private String username;
	
	
	@Size(min = 4, max = 50, message="La contraseña debe contener minimo 4  y un maximo de 50 caracteres o digitos")
	@Pattern(regexp = "[a-zA-Z0-9]+", message="La contraseña solo puede contener caracteres alfanumericos")
	private String password;
	
	@OneToOne
	@JoinColumn(name="id_rol")
	private Rol rol;
	
	private String estado;
	
	
	private String genero;
}
