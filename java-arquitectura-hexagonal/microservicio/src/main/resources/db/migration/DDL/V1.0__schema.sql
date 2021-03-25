create table usuario (
 id int(11) not null auto_increment,
 nombre varchar(100) not null,
 clave varchar(45) not null,
 fecha_creacion datetime null,
 primary key (id)
);

create table reserva (
 idReserva int(11) not null auto_increment,
 idReservante int(11) not null ,
 nombreReservante varchar(100) not null,
 fechaNacimiento date null,
 fechaReserva datetime null,
 valorReserva double(7,0),
 primary key (idReserva)
);