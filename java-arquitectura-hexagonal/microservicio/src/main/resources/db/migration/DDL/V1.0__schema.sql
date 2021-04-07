CREATE TABLE usuario (
 id INT(11) NOT NULL AUTO_INCREMENT,
 nombre VARCHAR(100) NOT NULL,
 clave VARCHAR(45) NOT NULL,
 fecha_creacion DATETIME NULL,
 PRIMARY KEY (id)
);

CREATE TABLE reserva (
 idReserva INT(11) NOT NULL AUTO_INCREMENT,
 idReservante INT(11) NOT NULL,
 nombreReservante VARCHAR(100) NOT NULL,
 fechaNacimiento DATE NULL,
 fechaReserva DATETIME NULL,
 valorReserva DOUBLE,
 estrato INT NULL,
 PRIMARY KEY (idReserva)
);