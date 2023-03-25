import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Deposito from './deposito';
import DepositoDetail from './deposito-detail';
import DepositoUpdate from './deposito-update';
import DepositoDeleteDialog from './deposito-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={DepositoUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={DepositoUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={DepositoDetail} />
      <ErrorBoundaryRoute path={match.url} component={Deposito} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={DepositoDeleteDialog} />
  </>
);

export default Routes;
