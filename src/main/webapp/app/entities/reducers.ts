import adminClub from 'app/entities/admin-club/admin-club.reducer';
import cliente from 'app/entities/cliente/cliente.reducer';
import registrador from 'app/entities/registrador/registrador.reducer';
import trabajador from 'app/entities/trabajador/trabajador.reducer';
import cajero from 'app/entities/cajero/cajero.reducer';
import caja from 'app/entities/caja/caja.reducer';
import producto from 'app/entities/producto/producto.reducer';
import venta from 'app/entities/venta/venta.reducer';
import productoVenta from 'app/entities/producto-venta/producto-venta.reducer';
import productoCaja from 'app/entities/producto-caja/producto-caja.reducer';
import productoDeposito from 'app/entities/producto-deposito/producto-deposito.reducer';
import club from 'app/entities/club/club.reducer';
import asociado from 'app/entities/asociado/asociado.reducer';
import asociadoClub from 'app/entities/asociado-club/asociado-club.reducer';
import evento from 'app/entities/evento/evento.reducer';
import deposito from 'app/entities/deposito/deposito.reducer';
import documento from 'app/entities/documento/documento.reducer';
import acceso from 'app/entities/acceso/acceso.reducer';
import formaPago from 'app/entities/forma-pago/forma-pago.reducer';
import tipoProducto from 'app/entities/tipo-producto/tipo-producto.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

const entitiesReducers = {
  adminClub,
  cliente,
  registrador,
  trabajador,
  cajero,
  caja,
  producto,
  venta,
  productoVenta,
  productoCaja,
  productoDeposito,
  club,
  asociado,
  asociadoClub,
  evento,
  deposito,
  documento,
  acceso,
  formaPago,
  tipoProducto,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
};

export default entitiesReducers;
