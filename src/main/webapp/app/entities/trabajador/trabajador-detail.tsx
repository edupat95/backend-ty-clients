import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './trabajador.reducer';

export const TrabajadorDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const trabajadorEntity = useAppSelector(state => state.trabajador.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="trabajadorDetailsHeading">
          <Translate contentKey="fidelizacion2App.trabajador.detail.title">Trabajador</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{trabajadorEntity.id}</dd>
          <dt>
            <span id="sueldo">
              <Translate contentKey="fidelizacion2App.trabajador.sueldo">Sueldo</Translate>
            </span>
          </dt>
          <dd>{trabajadorEntity.sueldo}</dd>
          <dt>
            <span id="reputacion">
              <Translate contentKey="fidelizacion2App.trabajador.reputacion">Reputacion</Translate>
            </span>
          </dt>
          <dd>{trabajadorEntity.reputacion}</dd>
          <dt>
            <span id="descripcion">
              <Translate contentKey="fidelizacion2App.trabajador.descripcion">Descripcion</Translate>
            </span>
          </dt>
          <dd>{trabajadorEntity.descripcion}</dd>
          <dt>
            <span id="fechaIngreso">
              <Translate contentKey="fidelizacion2App.trabajador.fechaIngreso">Fecha Ingreso</Translate>
            </span>
          </dt>
          <dd>
            {trabajadorEntity.fechaIngreso ? (
              <TextFormat value={trabajadorEntity.fechaIngreso} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="estado">
              <Translate contentKey="fidelizacion2App.trabajador.estado">Estado</Translate>
            </span>
          </dt>
          <dd>{trabajadorEntity.estado ? 'true' : 'false'}</dd>
          <dt>
            <span id="createdDate">
              <Translate contentKey="fidelizacion2App.trabajador.createdDate">Created Date</Translate>
            </span>
          </dt>
          <dd>
            {trabajadorEntity.createdDate ? <TextFormat value={trabajadorEntity.createdDate} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="updatedDate">
              <Translate contentKey="fidelizacion2App.trabajador.updatedDate">Updated Date</Translate>
            </span>
          </dt>
          <dd>
            {trabajadorEntity.updatedDate ? <TextFormat value={trabajadorEntity.updatedDate} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <Translate contentKey="fidelizacion2App.trabajador.user">User</Translate>
          </dt>
          <dd>{trabajadorEntity.user ? trabajadorEntity.user.id : ''}</dd>
          <dt>
            <Translate contentKey="fidelizacion2App.trabajador.club">Club</Translate>
          </dt>
          <dd>{trabajadorEntity.club ? trabajadorEntity.club.id : ''}</dd>
          <dt>
            <Translate contentKey="fidelizacion2App.trabajador.adminClub">Admin Club</Translate>
          </dt>
          <dd>{trabajadorEntity.adminClub ? trabajadorEntity.adminClub.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/trabajador" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/trabajador/${trabajadorEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default TrabajadorDetail;
