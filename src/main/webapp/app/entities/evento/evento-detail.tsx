import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './evento.reducer';

export const EventoDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const eventoEntity = useAppSelector(state => state.evento.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="eventoDetailsHeading">
          <Translate contentKey="fidelizacion2App.evento.detail.title">Evento</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{eventoEntity.id}</dd>
          <dt>
            <span id="fechaEvento">
              <Translate contentKey="fidelizacion2App.evento.fechaEvento">Fecha Evento</Translate>
            </span>
          </dt>
          <dd>{eventoEntity.fechaEvento ? <TextFormat value={eventoEntity.fechaEvento} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="fechaCreacion">
              <Translate contentKey="fidelizacion2App.evento.fechaCreacion">Fecha Creacion</Translate>
            </span>
          </dt>
          <dd>
            {eventoEntity.fechaCreacion ? <TextFormat value={eventoEntity.fechaCreacion} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="estado">
              <Translate contentKey="fidelizacion2App.evento.estado">Estado</Translate>
            </span>
          </dt>
          <dd>{eventoEntity.estado ? 'true' : 'false'}</dd>
          <dt>
            <span id="direccion">
              <Translate contentKey="fidelizacion2App.evento.direccion">Direccion</Translate>
            </span>
          </dt>
          <dd>{eventoEntity.direccion}</dd>
          <dt>
            <span id="createdDate">
              <Translate contentKey="fidelizacion2App.evento.createdDate">Created Date</Translate>
            </span>
          </dt>
          <dd>{eventoEntity.createdDate ? <TextFormat value={eventoEntity.createdDate} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="updatedDate">
              <Translate contentKey="fidelizacion2App.evento.updatedDate">Updated Date</Translate>
            </span>
          </dt>
          <dd>{eventoEntity.updatedDate ? <TextFormat value={eventoEntity.updatedDate} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <Translate contentKey="fidelizacion2App.evento.adminClub">Admin Club</Translate>
          </dt>
          <dd>{eventoEntity.adminClub ? eventoEntity.adminClub.id : ''}</dd>
          <dt>
            <Translate contentKey="fidelizacion2App.evento.club">Club</Translate>
          </dt>
          <dd>{eventoEntity.club ? eventoEntity.club.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/evento" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/evento/${eventoEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default EventoDetail;
