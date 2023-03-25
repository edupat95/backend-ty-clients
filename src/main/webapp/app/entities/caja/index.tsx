import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Caja from './caja';
import CajaDetail from './caja-detail';
import CajaUpdate from './caja-update';
import CajaDeleteDialog from './caja-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={CajaUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={CajaUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={CajaDetail} />
      <ErrorBoundaryRoute path={match.url} component={Caja} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={CajaDeleteDialog} />
  </>
);

export default Routes;
