import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IAsociado } from 'app/shared/model/asociado.model';
import { getEntities as getAsociados } from 'app/entities/asociado/asociado.reducer';
import { IClub } from 'app/shared/model/club.model';
import { getEntities as getClubs } from 'app/entities/club/club.reducer';
import { IRegistrador } from 'app/shared/model/registrador.model';
import { getEntities as getRegistradors } from 'app/entities/registrador/registrador.reducer';
import { IAsociadoClub } from 'app/shared/model/asociado-club.model';
import { getEntity, updateEntity, createEntity, reset } from './asociado-club.reducer';

export const AsociadoClubUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const asociados = useAppSelector(state => state.asociado.entities);
  const clubs = useAppSelector(state => state.club.entities);
  const registradors = useAppSelector(state => state.registrador.entities);
  const asociadoClubEntity = useAppSelector(state => state.asociadoClub.entity);
  const loading = useAppSelector(state => state.asociadoClub.loading);
  const updating = useAppSelector(state => state.asociadoClub.updating);
  const updateSuccess = useAppSelector(state => state.asociadoClub.updateSuccess);
  const handleClose = () => {
    props.history.push('/asociado-club');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getAsociados({}));
    dispatch(getClubs({}));
    dispatch(getRegistradors({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    values.fechaAsociacion = convertDateTimeToServer(values.fechaAsociacion);
    values.createdDate = convertDateTimeToServer(values.createdDate);
    values.updatedDate = convertDateTimeToServer(values.updatedDate);

    const entity = {
      ...asociadoClubEntity,
      ...values,
      asociado: asociados.find(it => it.id.toString() === values.asociado.toString()),
      club: clubs.find(it => it.id.toString() === values.club.toString()),
      registrador: registradors.find(it => it.id.toString() === values.registrador.toString()),
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
          fechaAsociacion: displayDefaultDateTime(),
          createdDate: displayDefaultDateTime(),
          updatedDate: displayDefaultDateTime(),
        }
      : {
          ...asociadoClubEntity,
          fechaAsociacion: convertDateTimeFromServer(asociadoClubEntity.fechaAsociacion),
          createdDate: convertDateTimeFromServer(asociadoClubEntity.createdDate),
          updatedDate: convertDateTimeFromServer(asociadoClubEntity.updatedDate),
          asociado: asociadoClubEntity?.asociado?.id,
          club: asociadoClubEntity?.club?.id,
          registrador: asociadoClubEntity?.registrador?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="fidelizacion2App.asociadoClub.home.createOrEditLabel" data-cy="AsociadoClubCreateUpdateHeading">
            <Translate contentKey="fidelizacion2App.asociadoClub.home.createOrEditLabel">Create or edit a AsociadoClub</Translate>
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
                  id="asociado-club-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('fidelizacion2App.asociadoClub.identificador')}
                id="asociado-club-identificador"
                name="identificador"
                data-cy="identificador"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('fidelizacion2App.asociadoClub.fechaAsociacion')}
                id="asociado-club-fechaAsociacion"
                name="fechaAsociacion"
                data-cy="fechaAsociacion"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('fidelizacion2App.asociadoClub.puntosClub')}
                id="asociado-club-puntosClub"
                name="puntosClub"
                data-cy="puntosClub"
                type="text"
              />
              <ValidatedField
                label={translate('fidelizacion2App.asociadoClub.estado')}
                id="asociado-club-estado"
                name="estado"
                data-cy="estado"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('fidelizacion2App.asociadoClub.createdDate')}
                id="asociado-club-createdDate"
                name="createdDate"
                data-cy="createdDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('fidelizacion2App.asociadoClub.updatedDate')}
                id="asociado-club-updatedDate"
                name="updatedDate"
                data-cy="updatedDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                id="asociado-club-asociado"
                name="asociado"
                data-cy="asociado"
                label={translate('fidelizacion2App.asociadoClub.asociado')}
                type="select"
                required
              >
                <option value="" key="0" />
                {asociados
                  ? asociados.map(otherEntity => (
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
                id="asociado-club-club"
                name="club"
                data-cy="club"
                label={translate('fidelizacion2App.asociadoClub.club')}
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
                id="asociado-club-registrador"
                name="registrador"
                data-cy="registrador"
                label={translate('fidelizacion2App.asociadoClub.registrador')}
                type="select"
              >
                <option value="" key="0" />
                {registradors
                  ? registradors.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/asociado-club" replace color="info">
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

export default AsociadoClubUpdate;
