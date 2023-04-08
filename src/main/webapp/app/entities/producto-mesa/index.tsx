import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import ProductoMesa from './producto-mesa';
import ProductoMesaDetail from './producto-mesa-detail';
import ProductoMesaUpdate from './producto-mesa-update';
import ProductoMesaDeleteDialog from './producto-mesa-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ProductoMesaUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ProductoMesaUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ProductoMesaDetail} />
      <ErrorBoundaryRoute path={match.url} component={ProductoMesa} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={ProductoMesaDeleteDialog} />
  </>
);

export default Routes;
