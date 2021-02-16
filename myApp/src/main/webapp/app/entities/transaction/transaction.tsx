import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, ICrudGetAllAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './transaction.reducer';
import { ITransaction } from 'app/shared/model/transaction.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ITransactionProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const Transaction = (props: ITransactionProps) => {
  useEffect(() => {
    props.getEntities();
  }, []);

  const { transactionList, match, loading } = props;
  return (
    <div>
      <h2 id="transaction-heading">
        <Translate contentKey="myApp.transaction.home.title">Transactions</Translate>
        <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
          <FontAwesomeIcon icon="plus" />
          &nbsp;
          <Translate contentKey="myApp.transaction.home.createLabel">Create new Transaction</Translate>
        </Link>
      </h2>
      <div className="table-responsive">
        {transactionList && transactionList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="global.field.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="myApp.transaction.date">Date</Translate>
                </th>
                <th>
                  <Translate contentKey="myApp.transaction.amount">Amount</Translate>
                </th>
                <th>
                  <Translate contentKey="myApp.transaction.custAccount">Cust Account</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {transactionList.map((transaction, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${transaction.id}`} color="link" size="sm">
                      {transaction.id}
                    </Button>
                  </td>
                  <td>{transaction.date ? <TextFormat type="date" value={transaction.date} format={APP_LOCAL_DATE_FORMAT} /> : null}</td>
                  <td>{transaction.amount}</td>
                  <td>
                    {transaction.custAccount ? (
                      <Link to={`cust-account/${transaction.custAccount.id}`}>{transaction.custAccount.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${transaction.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${transaction.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${transaction.id}/delete`} color="danger" size="sm">
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
              <Translate contentKey="myApp.transaction.home.notFound">No Transactions found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

const mapStateToProps = ({ transaction }: IRootState) => ({
  transactionList: transaction.entities,
  loading: transaction.loading,
});

const mapDispatchToProps = {
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(Transaction);
