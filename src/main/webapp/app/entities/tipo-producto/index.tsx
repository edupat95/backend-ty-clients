import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import TipoProducto from './tipo-producto';
import TipoProductoDetail from './tipo-producto-detail';
import TipoProductoUpdate from './tipo-producto-update';
import TipoProductoDeleteDialog from './tipo-producto-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={TipoProductoUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={TipoProductoUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={TipoProductoDetail} />
      <ErrorBoundaryRoute path={match.url} component={TipoProducto} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={TipoProductoDeleteDialog} />
  </>
);

export default Routes;
