import { NavbarPage } from '../page/navbar/navbar.po';
import { AppPage } from '../app.po';
import { ProductoPage } from '../page/producto/producto.po';
import { ReservaPage } from '../page/producto/reserva.po';

describe('workspace-project Producto', () => {
    let page: AppPage;
    let navBar: NavbarPage;
    let producto: ProductoPage;
    let reserva: ReservaPage;

    beforeEach(() => {
        page = new AppPage();
        navBar = new NavbarPage();
        producto = new ProductoPage();
        reserva = new ReservaPage();
    });

    it('Deberia crear producto', () => {
        const ID_PRODUCTO = '001';
        const DESCRIPCION_PRODUCTO = 'Producto de pruebas';

        page.navigateTo('/');
        navBar.clickBotonProductos();
        producto.clickBotonCrearProductos();
        producto.ingresarId(ID_PRODUCTO);
        producto.ingresarDescripcion(DESCRIPCION_PRODUCTO);

        // Adicionamos las validaciones despues de la creación
        // expect(<>).toEqual(<>);
    });

    it('Deberia listar productos', () => {
        page.navigateTo('/');
        navBar.clickBotonProductos();
        producto.clickBotonListarProductos();

        expect(0).toBe(producto.contarProductos());
    });

    it('Debería listar reservas', () => {
        page.navigateTo('/reserva/reservas');
        navBar.clickBotonReservas();
        reserva.clickBotonListarReserva();

        expect(20).toBe(reserva.contarReservas());
    });

    it('Debería crear reserva', () => {
        page.navigateTo('/reserva/reservas');
        navBar.clickBotonReservas();
        reserva.clickBotonCrearReserva();
    });
});
