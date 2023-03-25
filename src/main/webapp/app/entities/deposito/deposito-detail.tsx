import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './deposito.reducer';

export const DepositoDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const depositoEntity = useAppSelector(state => state.deposito.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="depositoDetailsHeading">
          <Translate contentKey="fidelizacion2App.deposito.detail.title">Deposito</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{depositoEntity.id}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="fidelizacion2App.deposito.name">Name</Translate>
            </span>
          </dt>
          <dd>{depositoEntity.name}</dd>
          <dt>
            <span id="estado">
              <Translate contentKey="fidelizacion2App.deposito.estado">Estado</Translate>
            </span>
          </dt>
          <dd>{depositoEntity.estado ? 'true' : 'false'}</dd>
          <dt>
            <span id="createdDate">
              <Translate contentKey="fidelizacion2App.deposito.createdDate">Created Date</Translate>
            </span>
          </dt>
          <dd>
            {depositoEntity.createdDate ? <TextFormat value={depositoEntity.createdDate} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="updatedDate">
              <Translate contentKey="fidelizacion2App.deposito.updatedDate">Updated Date</Translate>
            </span>
          </dt>
          <dd>
            {depositoEntity.updatedDate ? <TextFormat value={depositoEntity.updatedDate} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <Translate contentKey="fidelizacion2App.deposito.club">Club</Translate>
          </dt>
          <dd>{depositoEntity.club ? depositoEntity.club.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/deposito" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/deposito/${depositoEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default DepositoDetail;
