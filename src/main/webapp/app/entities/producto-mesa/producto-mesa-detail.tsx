import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './producto-mesa.reducer';

export const ProductoMesaDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const productoMesaEntity = useAppSelector(state => state.productoMesa.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="productoMesaDetailsHeading">
          <Translate contentKey="fidelizacion2App.productoMesa.detail.title">ProductoMesa</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{productoMesaEntity.id}</dd>
          <dt>
            <span id="costoTotal">
              <Translate contentKey="fidelizacion2App.productoMesa.costoTotal">Costo Total</Translate>
            </span>
          </dt>
          <dd>{productoMesaEntity.costoTotal}</dd>
          <dt>
            <span id="costoTotalPuntos">
              <Translate contentKey="fidelizacion2App.productoMesa.costoTotalPuntos">Costo Total Puntos</Translate>
            </span>
          </dt>
          <dd>{productoMesaEntity.costoTotalPuntos}</dd>
          <dt>
            <span id="cantidad">
              <Translate contentKey="fidelizacion2App.productoMesa.cantidad">Cantidad</Translate>
            </span>
          </dt>
          <dd>{productoMesaEntity.cantidad}</dd>
          <dt>
            <span id="estado">
              <Translate contentKey="fidelizacion2App.productoMesa.estado">Estado</Translate>
            </span>
          </dt>
          <dd>{productoMesaEntity.estado ? 'true' : 'false'}</dd>
          <dt>
            <span id="createdDate">
              <Translate contentKey="fidelizacion2App.productoMesa.createdDate">Created Date</Translate>
            </span>
          </dt>
          <dd>
            {productoMesaEntity.createdDate ? (
              <TextFormat value={productoMesaEntity.createdDate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="updatedDate">
              <Translate contentKey="fidelizacion2App.productoMesa.updatedDate">Updated Date</Translate>
            </span>
          </dt>
          <dd>
            {productoMesaEntity.updatedDate ? (
              <TextFormat value={productoMesaEntity.updatedDate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <Translate contentKey="fidelizacion2App.productoMesa.mesa">Mesa</Translate>
          </dt>
          <dd>{productoMesaEntity.mesa ? productoMesaEntity.mesa.id : ''}</dd>
          <dt>
            <Translate contentKey="fidelizacion2App.productoMesa.producto">Producto</Translate>
          </dt>
          <dd>{productoMesaEntity.producto ? productoMesaEntity.producto.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/producto-mesa" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/producto-mesa/${productoMesaEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ProductoMesaDetail;
