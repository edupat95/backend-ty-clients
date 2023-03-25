import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Acceso from './acceso';
import AccesoDetail from './acceso-detail';
import AccesoUpdate from './acceso-update';
import AccesoDeleteDialog from './acceso-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={AccesoUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={AccesoUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={AccesoDetail} />
      <ErrorBoundaryRoute path={match.url} component={Acceso} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={AccesoDeleteDialog} />
  </>
);

export default Routes;
