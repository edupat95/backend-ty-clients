import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './admin-club.reducer';

export const AdminClubDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const adminClubEntity = useAppSelector(state => state.adminClub.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="adminClubDetailsHeading">
          <Translate contentKey="fidelizacion2App.adminClub.detail.title">AdminClub</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{adminClubEntity.id}</dd>
          <dt>
            <span id="estado">
              <Translate contentKey="fidelizacion2App.adminClub.estado">Estado</Translate>
            </span>
          </dt>
          <dd>{adminClubEntity.estado ? 'true' : 'false'}</dd>
          <dt>
            <span id="createdDate">
              <Translate contentKey="fidelizacion2App.adminClub.createdDate">Created Date</Translate>
            </span>
          </dt>
          <dd>
            {adminClubEntity.createdDate ? <TextFormat value={adminClubEntity.createdDate} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="updatedDate">
              <Translate contentKey="fidelizacion2App.adminClub.updatedDate">Updated Date</Translate>
            </span>
          </dt>
          <dd>
            {adminClubEntity.updatedDate ? <TextFormat value={adminClubEntity.updatedDate} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <Translate contentKey="fidelizacion2App.adminClub.user">User</Translate>
          </dt>
          <dd>{adminClubEntity.user ? adminClubEntity.user.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/admin-club" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/admin-club/${adminClubEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default AdminClubDetail;
