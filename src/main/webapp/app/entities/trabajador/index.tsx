import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Trabajador from './trabajador';
import TrabajadorDetail from './trabajador-detail';
import TrabajadorUpdate from './trabajador-update';
import TrabajadorDeleteDialog from './trabajador-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={TrabajadorUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={TrabajadorUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={TrabajadorDetail} />
      <ErrorBoundaryRoute path={match.url} component={Trabajador} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={TrabajadorDeleteDialog} />
  </>
);

export default Routes;
