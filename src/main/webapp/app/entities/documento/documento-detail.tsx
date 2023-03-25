import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './documento.reducer';

export const DocumentoDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const documentoEntity = useAppSelector(state => state.documento.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="documentoDetailsHeading">
          <Translate contentKey="fidelizacion2App.documento.detail.title">Documento</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{documentoEntity.id}</dd>
          <dt>
            <span id="numeroTramite">
              <Translate contentKey="fidelizacion2App.documento.numeroTramite">Numero Tramite</Translate>
            </span>
          </dt>
          <dd>{documentoEntity.numeroTramite}</dd>
          <dt>
            <span id="apellidos">
              <Translate contentKey="fidelizacion2App.documento.apellidos">Apellidos</Translate>
            </span>
          </dt>
          <dd>{documentoEntity.apellidos}</dd>
          <dt>
            <span id="nombres">
              <Translate contentKey="fidelizacion2App.documento.nombres">Nombres</Translate>
            </span>
          </dt>
          <dd>{documentoEntity.nombres}</dd>
          <dt>
            <span id="sexo">
              <Translate contentKey="fidelizacion2App.documento.sexo">Sexo</Translate>
            </span>
          </dt>
          <dd>{documentoEntity.sexo}</dd>
          <dt>
            <span id="numeroDni">
              <Translate contentKey="fidelizacion2App.documento.numeroDni">Numero Dni</Translate>
            </span>
          </dt>
          <dd>{documentoEntity.numeroDni}</dd>
          <dt>
            <span id="ejemplar">
              <Translate contentKey="fidelizacion2App.documento.ejemplar">Ejemplar</Translate>
            </span>
          </dt>
          <dd>{documentoEntity.ejemplar}</dd>
          <dt>
            <span id="nacimiento">
              <Translate contentKey="fidelizacion2App.documento.nacimiento">Nacimiento</Translate>
            </span>
          </dt>
          <dd>
            {documentoEntity.nacimiento ? (
              <TextFormat value={documentoEntity.nacimiento} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="fechaEmision">
              <Translate contentKey="fidelizacion2App.documento.fechaEmision">Fecha Emision</Translate>
            </span>
          </dt>
          <dd>
            {documentoEntity.fechaEmision ? (
              <TextFormat value={documentoEntity.fechaEmision} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="inicioFinCuil">
              <Translate contentKey="fidelizacion2App.documento.inicioFinCuil">Inicio Fin Cuil</Translate>
            </span>
          </dt>
          <dd>{documentoEntity.inicioFinCuil}</dd>
          <dt>
            <span id="createdDate">
              <Translate contentKey="fidelizacion2App.documento.createdDate">Created Date</Translate>
            </span>
          </dt>
          <dd>
            {documentoEntity.createdDate ? <TextFormat value={documentoEntity.createdDate} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="updatedDate">
              <Translate contentKey="fidelizacion2App.documento.updatedDate">Updated Date</Translate>
            </span>
          </dt>
          <dd>
            {documentoEntity.updatedDate ? <TextFormat value={documentoEntity.updatedDate} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <Translate contentKey="fidelizacion2App.documento.user">User</Translate>
          </dt>
          <dd>{documentoEntity.user ? documentoEntity.user.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/documento" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/documento/${documentoEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default DocumentoDetail;
