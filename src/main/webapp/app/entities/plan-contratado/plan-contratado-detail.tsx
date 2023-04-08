import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './plan-contratado.reducer';

export const PlanContratadoDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const planContratadoEntity = useAppSelector(state => state.planContratado.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="planContratadoDetailsHeading">
          <Translate contentKey="fidelizacion2App.planContratado.detail.title">PlanContratado</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{planContratadoEntity.id}</dd>
          <dt>
            <span id="tiempoContratado">
              <Translate contentKey="fidelizacion2App.planContratado.tiempoContratado">Tiempo Contratado</Translate>
            </span>
          </dt>
          <dd>{planContratadoEntity.tiempoContratado}</dd>
          <dt>
            <span id="fechaVencimiento">
              <Translate contentKey="fidelizacion2App.planContratado.fechaVencimiento">Fecha Vencimiento</Translate>
            </span>
          </dt>
          <dd>
            {planContratadoEntity.fechaVencimiento ? (
              <TextFormat value={planContratadoEntity.fechaVencimiento} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="estado">
              <Translate contentKey="fidelizacion2App.planContratado.estado">Estado</Translate>
            </span>
          </dt>
          <dd>{planContratadoEntity.estado ? 'true' : 'false'}</dd>
          <dt>
            <span id="createdDate">
              <Translate contentKey="fidelizacion2App.planContratado.createdDate">Created Date</Translate>
            </span>
          </dt>
          <dd>
            {planContratadoEntity.createdDate ? (
              <TextFormat value={planContratadoEntity.createdDate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="updatedDate">
              <Translate contentKey="fidelizacion2App.planContratado.updatedDate">Updated Date</Translate>
            </span>
          </dt>
          <dd>
            {planContratadoEntity.updatedDate ? (
              <TextFormat value={planContratadoEntity.updatedDate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <Translate contentKey="fidelizacion2App.planContratado.plan">Plan</Translate>
          </dt>
          <dd>{planContratadoEntity.plan ? planContratadoEntity.plan.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/plan-contratado" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/plan-contratado/${planContratadoEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default PlanContratadoDetail;
