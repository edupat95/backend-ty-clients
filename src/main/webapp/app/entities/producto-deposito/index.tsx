import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import ProductoDeposito from './producto-deposito';
import ProductoDepositoDetail from './producto-deposito-detail';
import ProductoDepositoUpdate from './producto-deposito-update';
import ProductoDepositoDeleteDialog from './producto-deposito-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ProductoDepositoUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ProductoDepositoUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ProductoDepositoDetail} />
      <ErrorBoundaryRoute path={match.url} component={ProductoDeposito} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={ProductoDepositoDeleteDialog} />
  </>
);

export default Routes;
