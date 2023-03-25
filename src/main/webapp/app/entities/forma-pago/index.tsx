import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import FormaPago from './forma-pago';
import FormaPagoDetail from './forma-pago-detail';
import FormaPagoUpdate from './forma-pago-update';
import FormaPagoDeleteDialog from './forma-pago-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={FormaPagoUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={FormaPagoUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={FormaPagoDetail} />
      <ErrorBoundaryRoute path={match.url} component={FormaPago} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={FormaPagoDeleteDialog} />
  </>
);

export default Routes;
