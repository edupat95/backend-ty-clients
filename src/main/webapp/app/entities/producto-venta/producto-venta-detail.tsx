import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './producto-venta.reducer';

export const ProductoVentaDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const productoVentaEntity = useAppSelector(state => state.productoVenta.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="productoVentaDetailsHeading">
          <Translate contentKey="fidelizacion2App.productoVenta.detail.title">ProductoVenta</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{productoVentaEntity.id}</dd>
          <dt>
            <span id="costoTotal">
              <Translate contentKey="fidelizacion2App.productoVenta.costoTotal">Costo Total</Translate>
            </span>
          </dt>
          <dd>{productoVentaEntity.costoTotal}</dd>
          <dt>
            <span id="costoTotalPuntos">
              <Translate contentKey="fidelizacion2App.productoVenta.costoTotalPuntos">Costo Total Puntos</Translate>
            </span>
          </dt>
          <dd>{productoVentaEntity.costoTotalPuntos}</dd>
          <dt>
            <span id="cantidad">
              <Translate contentKey="fidelizacion2App.productoVenta.cantidad">Cantidad</Translate>
            </span>
          </dt>
          <dd>{productoVentaEntity.cantidad}</dd>
          <dt>
            <span id="createdDate">
              <Translate contentKey="fidelizacion2App.productoVenta.createdDate">Created Date</Translate>
            </span>
          </dt>
          <dd>
            {productoVentaEntity.createdDate ? (
              <TextFormat value={productoVentaEntity.createdDate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="updatedDate">
              <Translate contentKey="fidelizacion2App.productoVenta.updatedDate">Updated Date</Translate>
            </span>
          </dt>
          <dd>
            {productoVentaEntity.updatedDate ? (
              <TextFormat value={productoVentaEntity.updatedDate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <Translate contentKey="fidelizacion2App.productoVenta.venta">Venta</Translate>
          </dt>
          <dd>{productoVentaEntity.venta ? productoVentaEntity.venta.id : ''}</dd>
          <dt>
            <Translate contentKey="fidelizacion2App.productoVenta.producto">Producto</Translate>
          </dt>
          <dd>{productoVentaEntity.producto ? productoVentaEntity.producto.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/producto-venta" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/producto-venta/${productoVentaEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ProductoVentaDetail;
