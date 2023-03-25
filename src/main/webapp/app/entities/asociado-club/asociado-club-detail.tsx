import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './asociado-club.reducer';

export const AsociadoClubDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const asociadoClubEntity = useAppSelector(state => state.asociadoClub.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="asociadoClubDetailsHeading">
          <Translate contentKey="fidelizacion2App.asociadoClub.detail.title">AsociadoClub</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{asociadoClubEntity.id}</dd>
          <dt>
            <span id="identificador">
              <Translate contentKey="fidelizacion2App.asociadoClub.identificador">Identificador</Translate>
            </span>
          </dt>
          <dd>{asociadoClubEntity.identificador}</dd>
          <dt>
            <span id="fechaAsociacion">
              <Translate contentKey="fidelizacion2App.asociadoClub.fechaAsociacion">Fecha Asociacion</Translate>
            </span>
          </dt>
          <dd>
            {asociadoClubEntity.fechaAsociacion ? (
              <TextFormat value={asociadoClubEntity.fechaAsociacion} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="puntosClub">
              <Translate contentKey="fidelizacion2App.asociadoClub.puntosClub">Puntos Club</Translate>
            </span>
          </dt>
          <dd>{asociadoClubEntity.puntosClub}</dd>
          <dt>
            <span id="estado">
              <Translate contentKey="fidelizacion2App.asociadoClub.estado">Estado</Translate>
            </span>
          </dt>
          <dd>{asociadoClubEntity.estado ? 'true' : 'false'}</dd>
          <dt>
            <span id="createdDate">
              <Translate contentKey="fidelizacion2App.asociadoClub.createdDate">Created Date</Translate>
            </span>
          </dt>
          <dd>
            {asociadoClubEntity.createdDate ? (
              <TextFormat value={asociadoClubEntity.createdDate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="updatedDate">
              <Translate contentKey="fidelizacion2App.asociadoClub.updatedDate">Updated Date</Translate>
            </span>
          </dt>
          <dd>
            {asociadoClubEntity.updatedDate ? (
              <TextFormat value={asociadoClubEntity.updatedDate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <Translate contentKey="fidelizacion2App.asociadoClub.asociado">Asociado</Translate>
          </dt>
          <dd>{asociadoClubEntity.asociado ? asociadoClubEntity.asociado.id : ''}</dd>
          <dt>
            <Translate contentKey="fidelizacion2App.asociadoClub.club">Club</Translate>
          </dt>
          <dd>{asociadoClubEntity.club ? asociadoClubEntity.club.id : ''}</dd>
          <dt>
            <Translate contentKey="fidelizacion2App.asociadoClub.registrador">Registrador</Translate>
          </dt>
          <dd>{asociadoClubEntity.registrador ? asociadoClubEntity.registrador.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/asociado-club" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/asociado-club/${asociadoClubEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default AsociadoClubDetail;
