update reserva
set nombreReservante = :nombreReservante,
	fechaReserva = :fechaReserva,
	valorReserva = :valorReserva,
	estrato = :estrato
where idReserva = :idReserva