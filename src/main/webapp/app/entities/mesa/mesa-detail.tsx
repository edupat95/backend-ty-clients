import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './mesa.reducer';

export const MesaDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const mesaEntity = useAppSelector(state => state.mesa.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="mesaDetailsHeading">
          <Translate contentKey="fidelizacion2App.mesa.detail.title">Mesa</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{mesaEntity.id}</dd>
          <dt>
            <span id="numeroMesa">
              <Translate contentKey="fidelizacion2App.mesa.numeroMesa">Numero Mesa</Translate>
            </span>
          </dt>
          <dd>{mesaEntity.numeroMesa}</dd>
          <dt>
            <span id="estado">
              <Translate contentKey="fidelizacion2App.mesa.estado">Estado</Translate>
            </span>
          </dt>
          <dd>{mesaEntity.estado ? 'true' : 'false'}</dd>
          <dt>
            <span id="createdDate">
              <Translate contentKey="fidelizacion2App.mesa.createdDate">Created Date</Translate>
            </span>
          </dt>
          <dd>{mesaEntity.createdDate ? <TextFormat value={mesaEntity.createdDate} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="updatedDate">
              <Translate contentKey="fidelizacion2App.mesa.updatedDate">Updated Date</Translate>
            </span>
          </dt>
          <dd>{mesaEntity.updatedDate ? <TextFormat value={mesaEntity.updatedDate} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <Translate contentKey="fidelizacion2App.mesa.club">Club</Translate>
          </dt>
          <dd>{mesaEntity.club ? mesaEntity.club.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/mesa" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/mesa/${mesaEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default MesaDetail;
