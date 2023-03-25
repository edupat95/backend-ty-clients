import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import ProductoVenta from './producto-venta';
import ProductoVentaDetail from './producto-venta-detail';
import ProductoVentaUpdate from './producto-venta-update';
import ProductoVentaDeleteDialog from './producto-venta-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ProductoVentaUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ProductoVentaUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ProductoVentaDetail} />
      <ErrorBoundaryRoute path={match.url} component={ProductoVenta} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={ProductoVentaDeleteDialog} />
  </>
);

export default Routes;
