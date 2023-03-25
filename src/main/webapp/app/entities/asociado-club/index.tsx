import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import AsociadoClub from './asociado-club';
import AsociadoClubDetail from './asociado-club-detail';
import AsociadoClubUpdate from './asociado-club-update';
import AsociadoClubDeleteDialog from './asociado-club-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={AsociadoClubUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={AsociadoClubUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={AsociadoClubDetail} />
      <ErrorBoundaryRoute path={match.url} component={AsociadoClub} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={AsociadoClubDeleteDialog} />
  </>
);

export default Routes;
