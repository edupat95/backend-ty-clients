import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import AdminClub from './admin-club';
import AdminClubDetail from './admin-club-detail';
import AdminClubUpdate from './admin-club-update';
import AdminClubDeleteDialog from './admin-club-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={AdminClubUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={AdminClubUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={AdminClubDetail} />
      <ErrorBoundaryRoute path={match.url} component={AdminClub} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={AdminClubDeleteDialog} />
  </>
);

export default Routes;
