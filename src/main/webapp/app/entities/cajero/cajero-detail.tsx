import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './cajero.reducer';

export const CajeroDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const cajeroEntity = useAppSelector(state => state.cajero.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="cajeroDetailsHeading">
          <Translate contentKey="fidelizacion2App.cajero.detail.title">Cajero</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{cajeroEntity.id}</dd>
          <dt>
            <span id="plataDeCambio">
              <Translate contentKey="fidelizacion2App.cajero.plataDeCambio">Plata De Cambio</Translate>
            </span>
          </dt>
          <dd>{cajeroEntity.plataDeCambio}</dd>
          <dt>
            <span id="tipo">
              <Translate contentKey="fidelizacion2App.cajero.tipo">Tipo</Translate>
            </span>
          </dt>
          <dd>{cajeroEntity.tipo}</dd>
          <dt>
            <span id="estado">
              <Translate contentKey="fidelizacion2App.cajero.estado">Estado</Translate>
            </span>
          </dt>
          <dd>{cajeroEntity.estado ? 'true' : 'false'}</dd>
          <dt>
            <span id="creadDate">
              <Translate contentKey="fidelizacion2App.cajero.creadDate">Cread Date</Translate>
            </span>
          </dt>
          <dd>{cajeroEntity.creadDate ? <TextFormat value={cajeroEntity.creadDate} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="updatedDate">
              <Translate contentKey="fidelizacion2App.cajero.updatedDate">Updated Date</Translate>
            </span>
          </dt>
          <dd>{cajeroEntity.updatedDate ? <TextFormat value={cajeroEntity.updatedDate} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <Translate contentKey="fidelizacion2App.cajero.trabajador">Trabajador</Translate>
          </dt>
          <dd>{cajeroEntity.trabajador ? cajeroEntity.trabajador.id : ''}</dd>
          <dt>
            <Translate contentKey="fidelizacion2App.cajero.caja">Caja</Translate>
          </dt>
          <dd>{cajeroEntity.caja ? cajeroEntity.caja.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/cajero" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/cajero/${cajeroEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default CajeroDetail;
