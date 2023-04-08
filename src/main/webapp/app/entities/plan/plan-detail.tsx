import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, byteSize, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './plan.reducer';

export const PlanDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const planEntity = useAppSelector(state => state.plan.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="planDetailsHeading">
          <Translate contentKey="fidelizacion2App.plan.detail.title">Plan</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{planEntity.id}</dd>
          <dt>
            <span id="numeroPlan">
              <Translate contentKey="fidelizacion2App.plan.numeroPlan">Numero Plan</Translate>
            </span>
          </dt>
          <dd>{planEntity.numeroPlan}</dd>
          <dt>
            <span id="descripcion">
              <Translate contentKey="fidelizacion2App.plan.descripcion">Descripcion</Translate>
            </span>
          </dt>
          <dd>{planEntity.descripcion}</dd>
          <dt>
            <span id="estado">
              <Translate contentKey="fidelizacion2App.plan.estado">Estado</Translate>
            </span>
          </dt>
          <dd>{planEntity.estado ? 'true' : 'false'}</dd>
          <dt>
            <span id="createdDate">
              <Translate contentKey="fidelizacion2App.plan.createdDate">Created Date</Translate>
            </span>
          </dt>
          <dd>{planEntity.createdDate ? <TextFormat value={planEntity.createdDate} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="updatedDate">
              <Translate contentKey="fidelizacion2App.plan.updatedDate">Updated Date</Translate>
            </span>
          </dt>
          <dd>{planEntity.updatedDate ? <TextFormat value={planEntity.updatedDate} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
        </dl>
        <Button tag={Link} to="/plan" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/plan/${planEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default PlanDetail;
