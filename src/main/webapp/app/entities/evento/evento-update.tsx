import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IAdminClub } from 'app/shared/model/admin-club.model';
import { getEntities as getAdminClubs } from 'app/entities/admin-club/admin-club.reducer';
import { IClub } from 'app/shared/model/club.model';
import { getEntities as getClubs } from 'app/entities/club/club.reducer';
import { IEvento } from 'app/shared/model/evento.model';
import { getEntity, updateEntity, createEntity, reset } from './evento.reducer';

export const EventoUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const adminClubs = useAppSelector(state => state.adminClub.entities);
  const clubs = useAppSelector(state => state.club.entities);
  const eventoEntity = useAppSelector(state => state.evento.entity);
  const loading = useAppSelector(state => state.evento.loading);
  const updating = useAppSelector(state => state.evento.updating);
  const updateSuccess = useAppSelector(state => state.evento.updateSuccess);
  const handleClose = () => {
    props.history.push('/evento');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getAdminClubs({}));
    dispatch(getClubs({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    values.fechaEvento = convertDateTimeToServer(values.fechaEvento);
    values.fechaCreacion = convertDateTimeToServer(values.fechaCreacion);
    values.createdDate = convertDateTimeToServer(values.createdDate);
    values.updatedDate = convertDateTimeToServer(values.updatedDate);

    const entity = {
      ...eventoEntity,
      ...values,
      adminClub: adminClubs.find(it => it.id.toString() === values.adminClub.toString()),
      club: clubs.find(it => it.id.toString() === values.club.toString()),
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
          fechaEvento: displayDefaultDateTime(),
          fechaCreacion: displayDefaultDateTime(),
          createdDate: displayDefaultDateTime(),
          updatedDate: displayDefaultDateTime(),
        }
      : {
          ...eventoEntity,
          fechaEvento: convertDateTimeFromServer(eventoEntity.fechaEvento),
          fechaCreacion: convertDateTimeFromServer(eventoEntity.fechaCreacion),
          createdDate: convertDateTimeFromServer(eventoEntity.createdDate),
          updatedDate: convertDateTimeFromServer(eventoEntity.updatedDate),
          adminClub: eventoEntity?.adminClub?.id,
          club: eventoEntity?.club?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="fidelizacion2App.evento.home.createOrEditLabel" data-cy="EventoCreateUpdateHeading">
            <Translate contentKey="fidelizacion2App.evento.home.createOrEditLabel">Create or edit a Evento</Translate>
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
                  id="evento-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('fidelizacion2App.evento.fechaEvento')}
                id="evento-fechaEvento"
                name="fechaEvento"
                data-cy="fechaEvento"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('fidelizacion2App.evento.fechaCreacion')}
                id="evento-fechaCreacion"
                name="fechaCreacion"
                data-cy="fechaCreacion"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('fidelizacion2App.evento.estado')}
                id="evento-estado"
                name="estado"
                data-cy="estado"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('fidelizacion2App.evento.direccion')}
                id="evento-direccion"
                name="direccion"
                data-cy="direccion"
                type="text"
              />
              <ValidatedField
                label={translate('fidelizacion2App.evento.createdDate')}
                id="evento-createdDate"
                name="createdDate"
                data-cy="createdDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('fidelizacion2App.evento.updatedDate')}
                id="evento-updatedDate"
                name="updatedDate"
                data-cy="updatedDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                id="evento-adminClub"
                name="adminClub"
                data-cy="adminClub"
                label={translate('fidelizacion2App.evento.adminClub')}
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
              <ValidatedField
                id="evento-club"
                name="club"
                data-cy="club"
                label={translate('fidelizacion2App.evento.club')}
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/evento" replace color="info">
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

export default EventoUpdate;
