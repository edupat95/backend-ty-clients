import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Registrador from './registrador';
import RegistradorDetail from './registrador-detail';
import RegistradorUpdate from './registrador-update';
import RegistradorDeleteDialog from './registrador-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={RegistradorUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={RegistradorUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={RegistradorDetail} />
      <ErrorBoundaryRoute path={match.url} component={Registrador} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={RegistradorDeleteDialog} />
  </>
);

export default Routes;
