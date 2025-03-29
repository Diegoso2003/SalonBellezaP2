-- diseño de la base de datos del segundo proyecto IPC2 salon de belleza 
CREATE DATABASE SalonDeBelleza;

USE SalonDeBelleza;

CREATE TABLE Usuario(
	idUsuario int AUTO_INCREMENT,
	nombre VARCHAR(250) NOT NULL,
	correo VARCHAR(250) NOT NULL UNIQUE,
	contraseña VARCHAR(60) NOT NULL,
	descripcion TEXT NOT NULL,
	gustos TEXT NOT NULL,
	hobbies TEXT NOT NULL,
	CONSTRAINT pk_usuario PRIMARY KEY(idUsuario)
);

CREATE TABLE Servicio(
	isServicio int AUTO_INCREMENT,
	nombreServicio VARCHAR(200) NOT NULL,
	precio DECIMAL(10,2) NOT NULL,
	duracion TIME NOT NULL,
	descripcion TEXT NOT NULL,
	CONSTRAINT pk_servicio PRIMARY KEY(idServicio)
);

CREATE TABLE EmpleadosServicio(
	empleado int NOT NULL,
	servicio int NOT NULL,
	CONSTRAINT pk_empleados_servicio(empleado, servicio),
	CONSTRAINT fk_empleado1 FOREIGN KEY(empleado) REFERENCES Usuario(idUsuario),
	CONSTRAINT fk_servicio1 FOREIGN KEY(servicio) REFERENCES Servicio(idServicio)
);

CREATE TABLE Anuncio(
	idAnuncio int AUTO_INCREMENT,
	tipo ENUM('TEXTO','IMAGEN','VIDEO'),
	texto TEXT NOT NULL,
	VIDEO varchar(250),
	CONSTRAINT pk_anuncio PRIMARY KEY(idAnuncio)
);
