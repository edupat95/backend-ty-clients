import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './producto-caja.reducer';

export const ProductoCajaDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const productoCajaEntity = useAppSelector(state => state.productoCaja.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="productoCajaDetailsHeading">
          <Translate contentKey="fidelizacion2App.productoCaja.detail.title">ProductoCaja</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{productoCajaEntity.id}</dd>
          <dt>
            <span id="cantidad">
              <Translate contentKey="fidelizacion2App.productoCaja.cantidad">Cantidad</Translate>
            </span>
          </dt>
          <dd>{productoCajaEntity.cantidad}</dd>
          <dt>
            <span id="createdDate">
              <Translate contentKey="fidelizacion2App.productoCaja.createdDate">Created Date</Translate>
            </span>
          </dt>
          <dd>
            {productoCajaEntity.createdDate ? (
              <TextFormat value={productoCajaEntity.createdDate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="updatedDate">
              <Translate contentKey="fidelizacion2App.productoCaja.updatedDate">Updated Date</Translate>
            </span>
          </dt>
          <dd>
            {productoCajaEntity.updatedDate ? (
              <TextFormat value={productoCajaEntity.updatedDate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <Translate contentKey="fidelizacion2App.productoCaja.producto">Producto</Translate>
          </dt>
          <dd>{productoCajaEntity.producto ? productoCajaEntity.producto.id : ''}</dd>
          <dt>
            <Translate contentKey="fidelizacion2App.productoCaja.caja">Caja</Translate>
          </dt>
          <dd>{productoCajaEntity.caja ? productoCajaEntity.caja.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/producto-caja" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/producto-caja/${productoCajaEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ProductoCajaDetail;
