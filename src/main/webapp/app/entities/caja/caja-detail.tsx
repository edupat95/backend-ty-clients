import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './caja.reducer';

export const CajaDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const cajaEntity = useAppSelector(state => state.caja.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="cajaDetailsHeading">
          <Translate contentKey="fidelizacion2App.caja.detail.title">Caja</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{cajaEntity.id}</dd>
          <dt>
            <span id="nombre">
              <Translate contentKey="fidelizacion2App.caja.nombre">Nombre</Translate>
            </span>
          </dt>
          <dd>{cajaEntity.nombre}</dd>
          <dt>
            <span id="estado">
              <Translate contentKey="fidelizacion2App.caja.estado">Estado</Translate>
            </span>
          </dt>
          <dd>{cajaEntity.estado ? 'true' : 'false'}</dd>
          <dt>
            <span id="createdDate">
              <Translate contentKey="fidelizacion2App.caja.createdDate">Created Date</Translate>
            </span>
          </dt>
          <dd>{cajaEntity.createdDate ? <TextFormat value={cajaEntity.createdDate} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="updatedDate">
              <Translate contentKey="fidelizacion2App.caja.updatedDate">Updated Date</Translate>
            </span>
          </dt>
          <dd>{cajaEntity.updatedDate ? <TextFormat value={cajaEntity.updatedDate} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <Translate contentKey="fidelizacion2App.caja.club">Club</Translate>
          </dt>
          <dd>{cajaEntity.club ? cajaEntity.club.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/caja" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/caja/${cajaEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default CajaDetail;
