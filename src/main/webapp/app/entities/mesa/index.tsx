import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Mesa from './mesa';
import MesaDetail from './mesa-detail';
import MesaUpdate from './mesa-update';
import MesaDeleteDialog from './mesa-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={MesaUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={MesaUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={MesaDetail} />
      <ErrorBoundaryRoute path={match.url} component={Mesa} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={MesaDeleteDialog} />
  </>
);

export default Routes;
