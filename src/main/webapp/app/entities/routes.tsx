import React from 'react';
import { Switch } from 'react-router-dom';
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import AdminClub from './admin-club';
import Cliente from './cliente';
import Registrador from './registrador';
import Trabajador from './trabajador';
import Cajero from './cajero';
import Caja from './caja';
import Producto from './producto';
import Venta from './venta';
import ProductoVenta from './producto-venta';
import ProductoCaja from './producto-caja';
import ProductoDeposito from './producto-deposito';
import Club from './club';
import Asociado from './asociado';
import AsociadoClub from './asociado-club';
import Evento from './evento';
import Deposito from './deposito';
import Documento from './documento';
import Acceso from './acceso';
import FormaPago from './forma-pago';
import TipoProducto from './tipo-producto';
/* jhipster-needle-add-route-import - JHipster will add routes here */

export default ({ match }) => {
  return (
    <div>
      <Switch>
        {/* prettier-ignore */}
        <ErrorBoundaryRoute path={`${match.url}admin-club`} component={AdminClub} />
        <ErrorBoundaryRoute path={`${match.url}cliente`} component={Cliente} />
        <ErrorBoundaryRoute path={`${match.url}registrador`} component={Registrador} />
        <ErrorBoundaryRoute path={`${match.url}trabajador`} component={Trabajador} />
        <ErrorBoundaryRoute path={`${match.url}cajero`} component={Cajero} />
        <ErrorBoundaryRoute path={`${match.url}caja`} component={Caja} />
        <ErrorBoundaryRoute path={`${match.url}producto`} component={Producto} />
        <ErrorBoundaryRoute path={`${match.url}venta`} component={Venta} />
        <ErrorBoundaryRoute path={`${match.url}producto-venta`} component={ProductoVenta} />
        <ErrorBoundaryRoute path={`${match.url}producto-caja`} component={ProductoCaja} />
        <ErrorBoundaryRoute path={`${match.url}producto-deposito`} component={ProductoDeposito} />
        <ErrorBoundaryRoute path={`${match.url}club`} component={Club} />
        <ErrorBoundaryRoute path={`${match.url}asociado`} component={Asociado} />
        <ErrorBoundaryRoute path={`${match.url}asociado-club`} component={AsociadoClub} />
        <ErrorBoundaryRoute path={`${match.url}evento`} component={Evento} />
        <ErrorBoundaryRoute path={`${match.url}deposito`} component={Deposito} />
        <ErrorBoundaryRoute path={`${match.url}documento`} component={Documento} />
        <ErrorBoundaryRoute path={`${match.url}acceso`} component={Acceso} />
        <ErrorBoundaryRoute path={`${match.url}forma-pago`} component={FormaPago} />
        <ErrorBoundaryRoute path={`${match.url}tipo-producto`} component={TipoProducto} />
        {/* jhipster-needle-add-route-path - JHipster will add routes here */}
      </Switch>
    </div>
  );
};
