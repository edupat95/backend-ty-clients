import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './producto.reducer';

export const ProductoDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const productoEntity = useAppSelector(state => state.producto.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="productoDetailsHeading">
          <Translate contentKey="fidelizacion2App.producto.detail.title">Producto</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{productoEntity.id}</dd>
          <dt>
            <span id="nombre">
              <Translate contentKey="fidelizacion2App.producto.nombre">Nombre</Translate>
            </span>
          </dt>
          <dd>{productoEntity.nombre}</dd>
          <dt>
            <span id="precio">
              <Translate contentKey="fidelizacion2App.producto.precio">Precio</Translate>
            </span>
          </dt>
          <dd>{productoEntity.precio}</dd>
          <dt>
            <span id="costoPuntos">
              <Translate contentKey="fidelizacion2App.producto.costoPuntos">Costo Puntos</Translate>
            </span>
          </dt>
          <dd>{productoEntity.costoPuntos}</dd>
          <dt>
            <span id="puntosRecompensa">
              <Translate contentKey="fidelizacion2App.producto.puntosRecompensa">Puntos Recompensa</Translate>
            </span>
          </dt>
          <dd>{productoEntity.puntosRecompensa}</dd>
          <dt>
            <span id="descripcion">
              <Translate contentKey="fidelizacion2App.producto.descripcion">Descripcion</Translate>
            </span>
          </dt>
          <dd>{productoEntity.descripcion}</dd>
          <dt>
            <span id="estado">
              <Translate contentKey="fidelizacion2App.producto.estado">Estado</Translate>
            </span>
          </dt>
          <dd>{productoEntity.estado ? 'true' : 'false'}</dd>
          <dt>
            <span id="createdDate">
              <Translate contentKey="fidelizacion2App.producto.createdDate">Created Date</Translate>
            </span>
          </dt>
          <dd>
            {productoEntity.createdDate ? <TextFormat value={productoEntity.createdDate} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="updatedDate">
              <Translate contentKey="fidelizacion2App.producto.updatedDate">Updated Date</Translate>
            </span>
          </dt>
          <dd>
            {productoEntity.updatedDate ? <TextFormat value={productoEntity.updatedDate} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <Translate contentKey="fidelizacion2App.producto.club">Club</Translate>
          </dt>
          <dd>{productoEntity.club ? productoEntity.club.id : ''}</dd>
          <dt>
            <Translate contentKey="fidelizacion2App.producto.tipoProducto">Tipo Producto</Translate>
          </dt>
          <dd>{productoEntity.tipoProducto ? productoEntity.tipoProducto.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/producto" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/producto/${productoEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ProductoDetail;
