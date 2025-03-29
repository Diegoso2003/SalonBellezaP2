-- diseño de la base de datos del segundo proyecto IPC2 salon de belleza 
CREATE DATABASE SalonDeBelleza;

USE SalonDeBelleza;

CREATE TABLE Usuario(
	idUsuario int AUTO_INCREMENT,
	nombre VARCHAR(250) NOT NULL,
	correo VARCHAR(250) NOT NULL UNIQUE,
	rol ENUM('CLIENTE','EMPLEADO','MARKETING','SERVICIOS','ADMINISTRADOR'),
	contraseña VARCHAR(60) NOT NULL,
	descripcion TEXT NOT NULL,
	gustos TEXT NOT NULL,
	hobbies TEXT NOT NULL,
	CONSTRAINT pk_usuario PRIMARY KEY(idUsuario)
);

CREATE TABLE Servicio(
	idServicio INT AUTO_INCREMENT,
	nombreServicio VARCHAR(200) NOT NULL,
	precio DECIMAL(10,2) NOT NULL,
	duracion TIME NOT NULL,
	descripcion TEXT NOT NULL,
	CONSTRAINT pk_servicio PRIMARY KEY(idServicio)
);

CREATE TABLE EmpleadosServicio(
	empleado INT NOT NULL,
	servicio INT NOT NULL,
	CONSTRAINT pk_empleados_servicio PRIMARY KEY(empleado, servicio),
	CONSTRAINT fk_empleado1 FOREIGN KEY(empleado) REFERENCES Usuario(idUsuario),
	CONSTRAINT fk_servicio1 FOREIGN KEY(servicio) REFERENCES Servicio(idServicio)
);

CREATE TABLE Anuncio(
	idAnuncio INT AUTO_INCREMENT,
	tipo ENUM('TEXTO','IMAGEN','VIDEO') NOT NULL,
	texto TEXT NOT NULL,
	urlVideo VARCHAR(250),
	estado BOOL NOT NULL DEFAULT TRUE,
	CONSTRAINT pk_anuncio PRIMARY KEY(idAnuncio)
);

CREATE TABLE Vigencia(
	idVigencia INT AUTO_INCREMENT,
	idAnuncio INT NOT NULL,
	dias INT UNSIGNED NOT NULL,
	fechaPublicacion DATE NOT NULL,
	precio DECIMAL(10, 2) UNSIGNED NOT NULL,
	CONSTRAINT pk_vigencia PRIMARY KEY(idVigencia),
	CONSTRAINT fk_anuncio1 FOREIGN KEY(idAnuncio) REFERENCES Anuncio(idAnuncio)
);

CREATE TABLE Cita(
	idCita INT AUTO_INCREMENT,
	cliente INT NOT NULL,
	empleado INT NOT NULL,
	fecha DATE NOT NULL,
	costoTotal DECIMAL(10, 2) UNSIGNED NOT NULL DEFAULT 0.00,
	hora TIME NOT NULL,
	estado ENUM('PENDIENTE','PROGRAMADA','RECHAZADA','AUSENTE','ATENDIDA') NOT NULL,
	CONSTRAINT fk_cliente1 FOREIGN KEY(cliente) REFERENCES Usuario(idUsuario),
	CONSTRAINT fk_empleado2 FOREIGN KEY(empleado) REFERENCES Usuario(idUsuario),
	CONSTRAINT pk_cita PRIMARY KEY(idCita)
);
