import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Cajero from './cajero';
import CajeroDetail from './cajero-detail';
import CajeroUpdate from './cajero-update';
import CajeroDeleteDialog from './cajero-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={CajeroUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={CajeroUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={CajeroDetail} />
      <ErrorBoundaryRoute path={match.url} component={Cajero} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={CajeroDeleteDialog} />
  </>
);

export default Routes;
