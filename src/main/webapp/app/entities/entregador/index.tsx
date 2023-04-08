import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Entregador from './entregador';
import EntregadorDetail from './entregador-detail';
import EntregadorUpdate from './entregador-update';
import EntregadorDeleteDialog from './entregador-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={EntregadorUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={EntregadorUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={EntregadorDetail} />
      <ErrorBoundaryRoute path={match.url} component={Entregador} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={EntregadorDeleteDialog} />
  </>
);

export default Routes;
