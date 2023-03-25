import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './tipo-producto.reducer';

export const TipoProductoDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const tipoProductoEntity = useAppSelector(state => state.tipoProducto.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="tipoProductoDetailsHeading">
          <Translate contentKey="fidelizacion2App.tipoProducto.detail.title">TipoProducto</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{tipoProductoEntity.id}</dd>
          <dt>
            <span id="estado">
              <Translate contentKey="fidelizacion2App.tipoProducto.estado">Estado</Translate>
            </span>
          </dt>
          <dd>{tipoProductoEntity.estado ? 'true' : 'false'}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="fidelizacion2App.tipoProducto.name">Name</Translate>
            </span>
          </dt>
          <dd>{tipoProductoEntity.name}</dd>
          <dt>
            <Translate contentKey="fidelizacion2App.tipoProducto.club">Club</Translate>
          </dt>
          <dd>{tipoProductoEntity.club ? tipoProductoEntity.club.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/tipo-producto" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/tipo-producto/${tipoProductoEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default TipoProductoDetail;
