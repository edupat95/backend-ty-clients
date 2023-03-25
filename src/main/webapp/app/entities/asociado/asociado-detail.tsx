import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './asociado.reducer';

export const AsociadoDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const asociadoEntity = useAppSelector(state => state.asociado.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="asociadoDetailsHeading">
          <Translate contentKey="fidelizacion2App.asociado.detail.title">Asociado</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{asociadoEntity.id}</dd>
          <dt>
            <span id="identificadorGeneral">
              <Translate contentKey="fidelizacion2App.asociado.identificadorGeneral">Identificador General</Translate>
            </span>
          </dt>
          <dd>{asociadoEntity.identificadorGeneral}</dd>
          <dt>
            <span id="estado">
              <Translate contentKey="fidelizacion2App.asociado.estado">Estado</Translate>
            </span>
          </dt>
          <dd>{asociadoEntity.estado ? 'true' : 'false'}</dd>
          <dt>
            <span id="createdDate">
              <Translate contentKey="fidelizacion2App.asociado.createdDate">Created Date</Translate>
            </span>
          </dt>
          <dd>
            {asociadoEntity.createdDate ? <TextFormat value={asociadoEntity.createdDate} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="updatedDate">
              <Translate contentKey="fidelizacion2App.asociado.updatedDate">Updated Date</Translate>
            </span>
          </dt>
          <dd>
            {asociadoEntity.updatedDate ? <TextFormat value={asociadoEntity.updatedDate} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <Translate contentKey="fidelizacion2App.asociado.documento">Documento</Translate>
          </dt>
          <dd>{asociadoEntity.documento ? asociadoEntity.documento.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/asociado" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/asociado/${asociadoEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default AsociadoDetail;
