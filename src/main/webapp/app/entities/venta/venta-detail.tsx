import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './venta.reducer';

export const VentaDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const ventaEntity = useAppSelector(state => state.venta.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="ventaDetailsHeading">
          <Translate contentKey="fidelizacion2App.venta.detail.title">Venta</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{ventaEntity.id}</dd>
          <dt>
            <span id="costoTotal">
              <Translate contentKey="fidelizacion2App.venta.costoTotal">Costo Total</Translate>
            </span>
          </dt>
          <dd>{ventaEntity.costoTotal}</dd>
          <dt>
            <span id="costoTotalPuntos">
              <Translate contentKey="fidelizacion2App.venta.costoTotalPuntos">Costo Total Puntos</Translate>
            </span>
          </dt>
          <dd>{ventaEntity.costoTotalPuntos}</dd>
          <dt>
            <span id="identificadorTicket">
              <Translate contentKey="fidelizacion2App.venta.identificadorTicket">Identificador Ticket</Translate>
            </span>
          </dt>
          <dd>{ventaEntity.identificadorTicket}</dd>
          <dt>
            <span id="entregado">
              <Translate contentKey="fidelizacion2App.venta.entregado">Entregado</Translate>
            </span>
          </dt>
          <dd>{ventaEntity.entregado ? 'true' : 'false'}</dd>
          <dt>
            <span id="createdDate">
              <Translate contentKey="fidelizacion2App.venta.createdDate">Created Date</Translate>
            </span>
          </dt>
          <dd>{ventaEntity.createdDate ? <TextFormat value={ventaEntity.createdDate} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="updatedDate">
              <Translate contentKey="fidelizacion2App.venta.updatedDate">Updated Date</Translate>
            </span>
          </dt>
          <dd>{ventaEntity.updatedDate ? <TextFormat value={ventaEntity.updatedDate} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <Translate contentKey="fidelizacion2App.venta.cajero">Cajero</Translate>
          </dt>
          <dd>{ventaEntity.cajero ? ventaEntity.cajero.id : ''}</dd>
          <dt>
            <Translate contentKey="fidelizacion2App.venta.asociado">Asociado</Translate>
          </dt>
          <dd>{ventaEntity.asociado ? ventaEntity.asociado.id : ''}</dd>
          <dt>
            <Translate contentKey="fidelizacion2App.venta.formaPago">Forma Pago</Translate>
          </dt>
          <dd>{ventaEntity.formaPago ? ventaEntity.formaPago.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/venta" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/venta/${ventaEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default VentaDetail;
