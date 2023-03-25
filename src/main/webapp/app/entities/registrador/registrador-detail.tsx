import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './registrador.reducer';

export const RegistradorDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const registradorEntity = useAppSelector(state => state.registrador.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="registradorDetailsHeading">
          <Translate contentKey="fidelizacion2App.registrador.detail.title">Registrador</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{registradorEntity.id}</dd>
          <dt>
            <span id="estado">
              <Translate contentKey="fidelizacion2App.registrador.estado">Estado</Translate>
            </span>
          </dt>
          <dd>{registradorEntity.estado ? 'true' : 'false'}</dd>
          <dt>
            <span id="createdDate">
              <Translate contentKey="fidelizacion2App.registrador.createdDate">Created Date</Translate>
            </span>
          </dt>
          <dd>
            {registradorEntity.createdDate ? (
              <TextFormat value={registradorEntity.createdDate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="updatedDate">
              <Translate contentKey="fidelizacion2App.registrador.updatedDate">Updated Date</Translate>
            </span>
          </dt>
          <dd>
            {registradorEntity.updatedDate ? (
              <TextFormat value={registradorEntity.updatedDate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <Translate contentKey="fidelizacion2App.registrador.trabajador">Trabajador</Translate>
          </dt>
          <dd>{registradorEntity.trabajador ? registradorEntity.trabajador.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/registrador" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/registrador/${registradorEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default RegistradorDetail;
