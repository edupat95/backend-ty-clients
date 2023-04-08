import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IUser } from 'app/shared/model/user.model';
import { getUsers } from 'app/modules/administration/user-management/user-management.reducer';
import { IClub } from 'app/shared/model/club.model';
import { getEntities as getClubs } from 'app/entities/club/club.reducer';
import { IAdminClub } from 'app/shared/model/admin-club.model';
import { getEntities as getAdminClubs } from 'app/entities/admin-club/admin-club.reducer';
import { ITrabajador } from 'app/shared/model/trabajador.model';
import { getEntity, updateEntity, createEntity, reset } from './trabajador.reducer';

export const TrabajadorUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const users = useAppSelector(state => state.userManagement.users);
  const clubs = useAppSelector(state => state.club.entities);
  const adminClubs = useAppSelector(state => state.adminClub.entities);
  const trabajadorEntity = useAppSelector(state => state.trabajador.entity);
  const loading = useAppSelector(state => state.trabajador.loading);
  const updating = useAppSelector(state => state.trabajador.updating);
  const updateSuccess = useAppSelector(state => state.trabajador.updateSuccess);
  const handleClose = () => {
    props.history.push('/trabajador');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getUsers({}));
    dispatch(getClubs({}));
    dispatch(getAdminClubs({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    values.fechaIngreso = convertDateTimeToServer(values.fechaIngreso);
    values.createdDate = convertDateTimeToServer(values.createdDate);
    values.updatedDate = convertDateTimeToServer(values.updatedDate);

    const entity = {
      ...trabajadorEntity,
      ...values,
      user: users.find(it => it.id.toString() === values.user.toString()),
      club: clubs.find(it => it.id.toString() === values.club.toString()),
      adminClub: adminClubs.find(it => it.id.toString() === values.adminClub.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {
          fechaIngreso: displayDefaultDateTime(),
          createdDate: displayDefaultDateTime(),
          updatedDate: displayDefaultDateTime(),
        }
      : {
          ...trabajadorEntity,
          fechaIngreso: convertDateTimeFromServer(trabajadorEntity.fechaIngreso),
          createdDate: convertDateTimeFromServer(trabajadorEntity.createdDate),
          updatedDate: convertDateTimeFromServer(trabajadorEntity.updatedDate),
          user: trabajadorEntity?.user?.id,
          club: trabajadorEntity?.club?.id,
          adminClub: trabajadorEntity?.adminClub?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="fidelizacion2App.trabajador.home.createOrEditLabel" data-cy="TrabajadorCreateUpdateHeading">
            <Translate contentKey="fidelizacion2App.trabajador.home.createOrEditLabel">Create or edit a Trabajador</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="trabajador-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('fidelizacion2App.trabajador.sueldo')}
                id="trabajador-sueldo"
                name="sueldo"
                data-cy="sueldo"
                type="text"
              />
              <ValidatedField
                label={translate('fidelizacion2App.trabajador.descripcion')}
                id="trabajador-descripcion"
                name="descripcion"
                data-cy="descripcion"
                type="text"
              />
              <ValidatedField
                label={translate('fidelizacion2App.trabajador.fechaIngreso')}
                id="trabajador-fechaIngreso"
                name="fechaIngreso"
                data-cy="fechaIngreso"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('fidelizacion2App.trabajador.estado')}
                id="trabajador-estado"
                name="estado"
                data-cy="estado"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('fidelizacion2App.trabajador.createdDate')}
                id="trabajador-createdDate"
                name="createdDate"
                data-cy="createdDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('fidelizacion2App.trabajador.updatedDate')}
                id="trabajador-updatedDate"
                name="updatedDate"
                data-cy="updatedDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                id="trabajador-user"
                name="user"
                data-cy="user"
                label={translate('fidelizacion2App.trabajador.user')}
                type="select"
                required
              >
                <option value="" key="0" />
                {users
                  ? users.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <FormText>
                <Translate contentKey="entity.validation.required">This field is required.</Translate>
              </FormText>
              <ValidatedField
                id="trabajador-club"
                name="club"
                data-cy="club"
                label={translate('fidelizacion2App.trabajador.club')}
                type="select"
                required
              >
                <option value="" key="0" />
                {clubs
                  ? clubs.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <FormText>
                <Translate contentKey="entity.validation.required">This field is required.</Translate>
              </FormText>
              <ValidatedField
                id="trabajador-adminClub"
                name="adminClub"
                data-cy="adminClub"
                label={translate('fidelizacion2App.trabajador.adminClub')}
                type="select"
                required
              >
                <option value="" key="0" />
                {adminClubs
                  ? adminClubs.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <FormText>
                <Translate contentKey="entity.validation.required">This field is required.</Translate>
              </FormText>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/trabajador" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default TrabajadorUpdate;
