-- diseño de la base de datos del segundo proyecto IPC2 salon de belleza 
CREATE DATABASE SalonDeBelleza;

USE SalonDeBelleza;

CREATE TABLE Usuario(
	dpi BIGINT,
	nombre VARCHAR(250) NOT NULL,
	correo VARCHAR(250) NOT NULL UNIQUE,
	rol ENUM('CLIENTE','EMPLEADO','MARKETING','SERVICIOS','ADMINISTRADOR') NOT NULL,
	contraseña VARCHAR(60) NOT NULL,
	telefono VARCHAR(20) NOT NULL,
	direccion VARCHAR(250) NOT NULL DEFAULT 'no especificada',
	descripcion TEXT,
	gustos TEXT,
	hobbies TEXT,
	activo BOOL NOT NULL DEFAULT TRUE,
	listaNegra BOOL NOT NULL DEFAULT FALSE,
	CONSTRAINT pk_usuario PRIMARY KEY(dpi)
);

INSERT INTO Usuario(dpi, nombre, correo, rol, contraseña, telefono) VALUES(3355700050901, 'diego rafael', 'diegocayax@gmail.com', 'ADMINISTRADOR', 
	'$2a$10$V8VR4nTQXhmvs5SSiZUrwOXy5td1K30K.873W/FdnZe4vwuEX5422', '+502 38162870');

CREATE TABLE FotoUsuario(
	dpi BIGINT,
	foto BLOB NOT NULL,
	extension VARCHAR(10) NOT NULL,
	CONSTRAINT pk_foto_usuario PRIMARY KEY(dpi),
	CONSTRAINT fk_usuario FOREIGN KEY(dpi) REFERENCES Usuario(dpi)
);

CREATE TABLE Servicio(
	idServicio INT AUTO_INCREMENT,
	nombreServicio VARCHAR(200) NOT NULL UNIQUE,
	precio DECIMAL(10,2) UNSIGNED NOT NULL,
	duracion TIME NOT NULL,
	descripcion TEXT NOT NULL,
	activo BOOL NOT NULL DEFAULT TRUE,
	CONSTRAINT pk_servicio PRIMARY KEY(idServicio)
);

CREATE TABLE ArchivosServicio(
	idArchivos INT,
	catalogo MEDIUMBLOB NOT NULL,
	fotografia MEDIUMBLOB NOT NULL,
	extension VARCHAR(10) NOT NULL,
	CONSTRAINT pk_catalogo PRIMARY KEY(idArchivos),
	CONSTRAINT fk_servicio1 FOREIGN KEY(idArchivos) REFERENCES Servicio(idServicio)
);

CREATE TABLE EmpleadosServicio(
	empleado BIGINT NOT NULL,
	servicio INT NOT NULL,
	CONSTRAINT pk_empleados_servicio PRIMARY KEY(empleado, servicio),
	CONSTRAINT fk_empleado1 FOREIGN KEY(empleado) REFERENCES Usuario(dpi),
	CONSTRAINT fk_servicio2 FOREIGN KEY(servicio) REFERENCES Servicio(idServicio)
);

CREATE TABLE Anuncio(
	idAnuncio INT AUTO_INCREMENT,
	tipo ENUM('TEXTO','IMAGEN','VIDEO') NOT NULL,
	texto VARCHAR(150) NOT NULL,
	urlVideo VARCHAR(250),
	estado BOOL NOT NULL DEFAULT TRUE,
	CONSTRAINT pk_anuncio PRIMARY KEY(idAnuncio)
);

CREATE TABLE ImagenAnuncio(
	idAnuncio INT,
	imagen BLOB NOT NULL,
	extension VARCHAR(10) NOT NULL,
	CONSTRAINT pk_imagen_anuncio PRIMARY KEY(idAnuncio),
	CONSTRAINT fk_anuncio2 FOREIGN KEY(idAnuncio) REFERENCES Anuncio(idAnuncio)
);

CREATE TABLE HistorialAnuncio(
	idHistorial INT AUTO_INCREMENT,
	url VARCHAR(250) NOT NULL,
	fechaPublicacion DATE NOT NULL,
	contador INT NOT NULL DEFAULT 1,
	idAnuncio INT NOT NULL,
	CONSTRAINT un_historial UNIQUE(url, fechaPublicacion, idAnuncio),
	CONSTRAINT pk_historial PRIMARY KEY(idHistorial),
	CONSTRAINT fk_anuncio1 FOREIGN KEY(idAnuncio) REFERENCES Anuncio(idAnuncio)
);

CREATE TABLE Vigencia(
	idVigencia INT AUTO_INCREMENT,
	idAnuncio INT NOT NULL,
	dias INT UNSIGNED NOT NULL,
	fechaPublicacion DATE NOT NULL,
	precio DECIMAL(10, 2) UNSIGNED NOT NULL,
	CONSTRAINT pk_vigencia PRIMARY KEY(idVigencia),
	CONSTRAINT fk_anuncio3 FOREIGN KEY(idAnuncio) REFERENCES Anuncio(idAnuncio)
);

CREATE TABLE Cita(
	idCita INT AUTO_INCREMENT,
	idServicio INT NOT NULL,
	cliente BIGINT NOT NULL,
	empleado BIGINT NOT NULL,
	fecha DATE NOT NULL,
	costoTotal DECIMAL(10, 2) UNSIGNED NOT NULL DEFAULT 0.00,
	hora TIME NOT NULL,
	estado ENUM('PENDIENTE','PROGRAMADA','RECHAZADA','AUSENTE','ATENDIDA') NOT NULL,
	CONSTRAINT fk_cliente1 FOREIGN KEY(cliente) REFERENCES Usuario(dpi),
	CONSTRAINT fk_servicio3 FOREIGN KEY(idServicio) REFERENCES Servicio(idServicio),
	CONSTRAINT fk_empleado2 FOREIGN KEY(empleado) REFERENCES Usuario(dpi),
	CONSTRAINT pk_cita PRIMARY KEY(idCita)
);

CREATE TABLE PreciosAnuncio(
	texto DECIMAL(10, 2) UNSIGNED NOT NULL,
	imagen DECIMAL(10, 2) UNSIGNED NOT NULL,
	video DECIMAL(10, 2) UNSIGNED NOT NULL
);

INSERT INTO PreciosAnuncio(texto, imagen, video) VALUES(2.50, 5.00, 7.50);

CREATE TABLE HorarioSalon(
	horaInicio TIME NOT NULL,
	horaFin TIME NOT NULL
);

INSERT INTO HorarioSalon(horaInicio, horaFin) VALUES('08:00:00', '18:00:00');

