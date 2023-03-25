import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './club.reducer';

export const ClubDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const clubEntity = useAppSelector(state => state.club.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="clubDetailsHeading">
          <Translate contentKey="fidelizacion2App.club.detail.title">Club</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{clubEntity.id}</dd>
          <dt>
            <span id="nombre">
              <Translate contentKey="fidelizacion2App.club.nombre">Nombre</Translate>
            </span>
          </dt>
          <dd>{clubEntity.nombre}</dd>
          <dt>
            <span id="direccion">
              <Translate contentKey="fidelizacion2App.club.direccion">Direccion</Translate>
            </span>
          </dt>
          <dd>{clubEntity.direccion}</dd>
          <dt>
            <span id="estado">
              <Translate contentKey="fidelizacion2App.club.estado">Estado</Translate>
            </span>
          </dt>
          <dd>{clubEntity.estado ? 'true' : 'false'}</dd>
          <dt>
            <span id="createdDate">
              <Translate contentKey="fidelizacion2App.club.createdDate">Created Date</Translate>
            </span>
          </dt>
          <dd>{clubEntity.createdDate ? <TextFormat value={clubEntity.createdDate} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="updatedDate">
              <Translate contentKey="fidelizacion2App.club.updatedDate">Updated Date</Translate>
            </span>
          </dt>
          <dd>{clubEntity.updatedDate ? <TextFormat value={clubEntity.updatedDate} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <Translate contentKey="fidelizacion2App.club.adminClub">Admin Club</Translate>
          </dt>
          <dd>{clubEntity.adminClub ? clubEntity.adminClub.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/club" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/club/${clubEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ClubDetail;
