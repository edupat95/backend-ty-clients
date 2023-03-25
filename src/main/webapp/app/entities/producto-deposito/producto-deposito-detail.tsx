import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './producto-deposito.reducer';

export const ProductoDepositoDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const productoDepositoEntity = useAppSelector(state => state.productoDeposito.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="productoDepositoDetailsHeading">
          <Translate contentKey="fidelizacion2App.productoDeposito.detail.title">ProductoDeposito</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{productoDepositoEntity.id}</dd>
          <dt>
            <span id="cantidad">
              <Translate contentKey="fidelizacion2App.productoDeposito.cantidad">Cantidad</Translate>
            </span>
          </dt>
          <dd>{productoDepositoEntity.cantidad}</dd>
          <dt>
            <span id="createdDate">
              <Translate contentKey="fidelizacion2App.productoDeposito.createdDate">Created Date</Translate>
            </span>
          </dt>
          <dd>
            {productoDepositoEntity.createdDate ? (
              <TextFormat value={productoDepositoEntity.createdDate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="updatedDate">
              <Translate contentKey="fidelizacion2App.productoDeposito.updatedDate">Updated Date</Translate>
            </span>
          </dt>
          <dd>
            {productoDepositoEntity.updatedDate ? (
              <TextFormat value={productoDepositoEntity.updatedDate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <Translate contentKey="fidelizacion2App.productoDeposito.deposito">Deposito</Translate>
          </dt>
          <dd>{productoDepositoEntity.deposito ? productoDepositoEntity.deposito.id : ''}</dd>
          <dt>
            <Translate contentKey="fidelizacion2App.productoDeposito.producto">Producto</Translate>
          </dt>
          <dd>{productoDepositoEntity.producto ? productoDepositoEntity.producto.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/producto-deposito" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/producto-deposito/${productoDepositoEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ProductoDepositoDetail;
