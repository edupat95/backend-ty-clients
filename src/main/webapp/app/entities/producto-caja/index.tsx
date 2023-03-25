import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import ProductoCaja from './producto-caja';
import ProductoCajaDetail from './producto-caja-detail';
import ProductoCajaUpdate from './producto-caja-update';
import ProductoCajaDeleteDialog from './producto-caja-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ProductoCajaUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ProductoCajaUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ProductoCajaDetail} />
      <ErrorBoundaryRoute path={match.url} component={ProductoCaja} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={ProductoCajaDeleteDialog} />
  </>
);

export default Routes;
