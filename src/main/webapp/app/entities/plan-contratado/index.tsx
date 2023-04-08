import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import PlanContratado from './plan-contratado';
import PlanContratadoDetail from './plan-contratado-detail';
import PlanContratadoUpdate from './plan-contratado-update';
import PlanContratadoDeleteDialog from './plan-contratado-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={PlanContratadoUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={PlanContratadoUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={PlanContratadoDetail} />
      <ErrorBoundaryRoute path={match.url} component={PlanContratado} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={PlanContratadoDeleteDialog} />
  </>
);

export default Routes;
