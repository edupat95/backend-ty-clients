import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './entregador.reducer';

export const EntregadorDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const entregadorEntity = useAppSelector(state => state.entregador.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="entregadorDetailsHeading">
          <Translate contentKey="fidelizacion2App.entregador.detail.title">Entregador</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{entregadorEntity.id}</dd>
          <dt>
            <span id="estado">
              <Translate contentKey="fidelizacion2App.entregador.estado">Estado</Translate>
            </span>
          </dt>
          <dd>{entregadorEntity.estado ? 'true' : 'false'}</dd>
          <dt>
            <span id="createdDate">
              <Translate contentKey="fidelizacion2App.entregador.createdDate">Created Date</Translate>
            </span>
          </dt>
          <dd>
            {entregadorEntity.createdDate ? <TextFormat value={entregadorEntity.createdDate} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="updatedDate">
              <Translate contentKey="fidelizacion2App.entregador.updatedDate">Updated Date</Translate>
            </span>
          </dt>
          <dd>
            {entregadorEntity.updatedDate ? <TextFormat value={entregadorEntity.updatedDate} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <Translate contentKey="fidelizacion2App.entregador.trabajador">Trabajador</Translate>
          </dt>
          <dd>{entregadorEntity.trabajador ? entregadorEntity.trabajador.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/entregador" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/entregador/${entregadorEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default EntregadorDetail;
