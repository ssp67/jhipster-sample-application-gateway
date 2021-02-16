import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { ICustAccount } from 'app/shared/model/cust-account.model';
import { getEntities as getCustAccounts } from 'app/entities/cust-account/cust-account.reducer';
import { getEntity, updateEntity, createEntity, reset } from './transaction.reducer';
import { ITransaction } from 'app/shared/model/transaction.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface ITransactionUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const TransactionUpdate = (props: ITransactionUpdateProps) => {
  const [custAccountId, setCustAccountId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { transactionEntity, custAccounts, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/transaction');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getCustAccounts();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...transactionEntity,
        ...values,
      };

      if (isNew) {
        props.createEntity(entity);
      } else {
        props.updateEntity(entity);
      }
    }
  };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="myApp.transaction.home.createOrEditLabel">
            <Translate contentKey="myApp.transaction.home.createOrEditLabel">Create or edit a Transaction</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : transactionEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="transaction-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="transaction-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="dateLabel" for="transaction-date">
                  <Translate contentKey="myApp.transaction.date">Date</Translate>
                </Label>
                <AvField id="transaction-date" type="date" className="form-control" name="date" />
              </AvGroup>
              <AvGroup>
                <Label id="amountLabel" for="transaction-amount">
                  <Translate contentKey="myApp.transaction.amount">Amount</Translate>
                </Label>
                <AvField id="transaction-amount" type="text" name="amount" />
              </AvGroup>
              <AvGroup>
                <Label for="transaction-custAccount">
                  <Translate contentKey="myApp.transaction.custAccount">Cust Account</Translate>
                </Label>
                <AvInput id="transaction-custAccount" type="select" className="form-control" name="custAccount.id">
                  <option value="" key="0" />
                  {custAccounts
                    ? custAccounts.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/transaction" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </AvForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

const mapStateToProps = (storeState: IRootState) => ({
  custAccounts: storeState.custAccount.entities,
  transactionEntity: storeState.transaction.entity,
  loading: storeState.transaction.loading,
  updating: storeState.transaction.updating,
  updateSuccess: storeState.transaction.updateSuccess,
});

const mapDispatchToProps = {
  getCustAccounts,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(TransactionUpdate);
