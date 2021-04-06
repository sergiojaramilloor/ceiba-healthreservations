select idReserva, idReservante, nombreReservante,fechaNacimiento, fechaReserva, valorReserva, estrato
from reserva
where idReserva = :idReserva