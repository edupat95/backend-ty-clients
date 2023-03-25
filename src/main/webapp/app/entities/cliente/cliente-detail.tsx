import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './cliente.reducer';

export const ClienteDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const clienteEntity = useAppSelector(state => state.cliente.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="clienteDetailsHeading">
          <Translate contentKey="fidelizacion2App.cliente.detail.title">Cliente</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{clienteEntity.id}</dd>
          <dt>
            <span id="mail">
              <Translate contentKey="fidelizacion2App.cliente.mail">Mail</Translate>
            </span>
          </dt>
          <dd>{clienteEntity.mail}</dd>
          <dt>
            <span id="phone">
              <Translate contentKey="fidelizacion2App.cliente.phone">Phone</Translate>
            </span>
          </dt>
          <dd>{clienteEntity.phone}</dd>
          <dt>
            <span id="createdDate">
              <Translate contentKey="fidelizacion2App.cliente.createdDate">Created Date</Translate>
            </span>
          </dt>
          <dd>
            {clienteEntity.createdDate ? <TextFormat value={clienteEntity.createdDate} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="updatedDate">
              <Translate contentKey="fidelizacion2App.cliente.updatedDate">Updated Date</Translate>
            </span>
          </dt>
          <dd>
            {clienteEntity.updatedDate ? <TextFormat value={clienteEntity.updatedDate} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <Translate contentKey="fidelizacion2App.cliente.user">User</Translate>
          </dt>
          <dd>{clienteEntity.user ? clienteEntity.user.id : ''}</dd>
          <dt>
            <Translate contentKey="fidelizacion2App.cliente.documento">Documento</Translate>
          </dt>
          <dd>{clienteEntity.documento ? clienteEntity.documento.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/cliente" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/cliente/${clienteEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ClienteDetail;
