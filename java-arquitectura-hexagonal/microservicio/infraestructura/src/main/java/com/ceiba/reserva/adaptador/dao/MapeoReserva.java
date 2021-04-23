package com.ceiba.reserva.adaptador.dao;

import com.ceiba.infraestructura.jdbc.MapperResult;
import com.ceiba.reserva.modelo.dto.DtoReserva;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class MapeoReserva implements RowMapper<DtoReserva>, MapperResult {

    @Override
    public DtoReserva mapRow(ResultSet rs, int rowNum) throws SQLException {

        Long idReserva = rs.getLong("idReserva");
        Long idReservante = rs.getLong("idReservante");
        String nombreReservante = rs.getString("nombreReservante");
        LocalDate fechaNacimiento = extraerLocalDate(rs, "fechaNacimiento");
        LocalDateTime fechaReserva = extraerLocalDateTime(rs,"fechaReserva");
        double valorReserva = rs.getDouble("valorReserva");
        Long estrato = rs.getLong("estrato");

        return new DtoReserva(idReserva, idReservante, nombreReservante, fechaNacimiento, fechaReserva, valorReserva, estrato);
    }
}
