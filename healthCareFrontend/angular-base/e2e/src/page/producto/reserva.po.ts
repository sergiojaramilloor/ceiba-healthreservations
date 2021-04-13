import { by, element } from 'protractor';

export class ReservaPage {
    private linkCrearReserva = element(by.id('linkCrearReserva'));
    private linkListarReserva = element(by.id('linkListarReserva'));
    private listaReserva = element.all(by.css('ul.reservas li'));

    async clickBotonCrearReserva() {
        await this.linkCrearReserva.click();
    }

    async clickBotonListarReserva() {
        await this.linkListarReserva.click();
    }

    async contarReservas() {
        return this.listaReserva.count();
    }
}
