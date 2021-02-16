import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, ICrudGetAllAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './cust-account.reducer';
import { ICustAccount } from 'app/shared/model/cust-account.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ICustAccountProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const CustAccount = (props: ICustAccountProps) => {
  useEffect(() => {
    props.getEntities();
  }, []);

  const { custAccountList, match, loading } = props;
  return (
    <div>
      <h2 id="cust-account-heading">
        <Translate contentKey="myApp.custAccount.home.title">Cust Accounts</Translate>
        <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
          <FontAwesomeIcon icon="plus" />
          &nbsp;
          <Translate contentKey="myApp.custAccount.home.createLabel">Create new Cust Account</Translate>
        </Link>
      </h2>
      <div className="table-responsive">
        {custAccountList && custAccountList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="global.field.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="myApp.custAccount.departmentName">Department Name</Translate>
                </th>
                <th>
                  <Translate contentKey="myApp.custAccount.accountNumber">Account Number</Translate>
                </th>
                <th>
                  <Translate contentKey="myApp.custAccount.accountType">Account Type</Translate>
                </th>
                <th>
                  <Translate contentKey="myApp.custAccount.customer">Customer</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {custAccountList.map((custAccount, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${custAccount.id}`} color="link" size="sm">
                      {custAccount.id}
                    </Button>
                  </td>
                  <td>{custAccount.departmentName}</td>
                  <td>{custAccount.accountNumber}</td>
                  <td>{custAccount.accountType}</td>
                  <td>{custAccount.customer ? <Link to={`customer/${custAccount.customer.id}`}>{custAccount.customer.id}</Link> : ''}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${custAccount.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${custAccount.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${custAccount.id}/delete`} color="danger" size="sm">
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="myApp.custAccount.home.notFound">No Cust Accounts found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

const mapStateToProps = ({ custAccount }: IRootState) => ({
  custAccountList: custAccount.entities,
  loading: custAccount.loading,
});

const mapDispatchToProps = {
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(CustAccount);
