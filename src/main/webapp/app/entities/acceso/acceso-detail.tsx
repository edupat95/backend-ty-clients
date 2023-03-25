import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './acceso.reducer';

export const AccesoDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const accesoEntity = useAppSelector(state => state.acceso.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="accesoDetailsHeading">
          <Translate contentKey="fidelizacion2App.acceso.detail.title">Acceso</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{accesoEntity.id}</dd>
          <dt>
            <span id="costoPuntos">
              <Translate contentKey="fidelizacion2App.acceso.costoPuntos">Costo Puntos</Translate>
            </span>
          </dt>
          <dd>{accesoEntity.costoPuntos}</dd>
          <dt>
            <span id="fechaCanje">
              <Translate contentKey="fidelizacion2App.acceso.fechaCanje">Fecha Canje</Translate>
            </span>
          </dt>
          <dd>{accesoEntity.fechaCanje ? <TextFormat value={accesoEntity.fechaCanje} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="estado">
              <Translate contentKey="fidelizacion2App.acceso.estado">Estado</Translate>
            </span>
          </dt>
          <dd>{accesoEntity.estado ? 'true' : 'false'}</dd>
          <dt>
            <span id="createdDate">
              <Translate contentKey="fidelizacion2App.acceso.createdDate">Created Date</Translate>
            </span>
          </dt>
          <dd>{accesoEntity.createdDate ? <TextFormat value={accesoEntity.createdDate} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="updatedDate">
              <Translate contentKey="fidelizacion2App.acceso.updatedDate">Updated Date</Translate>
            </span>
          </dt>
          <dd>{accesoEntity.updatedDate ? <TextFormat value={accesoEntity.updatedDate} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <Translate contentKey="fidelizacion2App.acceso.asociado">Asociado</Translate>
          </dt>
          <dd>{accesoEntity.asociado ? accesoEntity.asociado.id : ''}</dd>
          <dt>
            <Translate contentKey="fidelizacion2App.acceso.evento">Evento</Translate>
          </dt>
          <dd>{accesoEntity.evento ? accesoEntity.evento.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/acceso" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/acceso/${accesoEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default AccesoDetail;
