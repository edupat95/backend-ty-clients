import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './forma-pago.reducer';

export const FormaPagoDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const formaPagoEntity = useAppSelector(state => state.formaPago.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="formaPagoDetailsHeading">
          <Translate contentKey="fidelizacion2App.formaPago.detail.title">FormaPago</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{formaPagoEntity.id}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="fidelizacion2App.formaPago.name">Name</Translate>
            </span>
          </dt>
          <dd>{formaPagoEntity.name}</dd>
          <dt>
            <span id="estado">
              <Translate contentKey="fidelizacion2App.formaPago.estado">Estado</Translate>
            </span>
          </dt>
          <dd>{formaPagoEntity.estado ? 'true' : 'false'}</dd>
          <dt>
            <span id="createdDate">
              <Translate contentKey="fidelizacion2App.formaPago.createdDate">Created Date</Translate>
            </span>
          </dt>
          <dd>
            {formaPagoEntity.createdDate ? <TextFormat value={formaPagoEntity.createdDate} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="updatedDate">
              <Translate contentKey="fidelizacion2App.formaPago.updatedDate">Updated Date</Translate>
            </span>
          </dt>
          <dd>
            {formaPagoEntity.updatedDate ? <TextFormat value={formaPagoEntity.updatedDate} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <Translate contentKey="fidelizacion2App.formaPago.club">Club</Translate>
          </dt>
          <dd>{formaPagoEntity.club ? formaPagoEntity.club.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/forma-pago" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/forma-pago/${formaPagoEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default FormaPagoDetail;
