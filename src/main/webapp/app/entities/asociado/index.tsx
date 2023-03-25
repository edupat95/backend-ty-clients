import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Asociado from './asociado';
import AsociadoDetail from './asociado-detail';
import AsociadoUpdate from './asociado-update';
import AsociadoDeleteDialog from './asociado-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={AsociadoUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={AsociadoUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={AsociadoDetail} />
      <ErrorBoundaryRoute path={match.url} component={Asociado} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={AsociadoDeleteDialog} />
  </>
);

export default Routes;
