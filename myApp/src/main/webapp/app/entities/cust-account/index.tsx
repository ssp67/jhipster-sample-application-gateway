import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import CustAccount from './cust-account';
import CustAccountDetail from './cust-account-detail';
import CustAccountUpdate from './cust-account-update';
import CustAccountDeleteDialog from './cust-account-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={CustAccountUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={CustAccountUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={CustAccountDetail} />
      <ErrorBoundaryRoute path={match.url} component={CustAccount} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={CustAccountDeleteDialog} />
  </>
);

export default Routes;
