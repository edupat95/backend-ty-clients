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
import { IPlanContratado } from 'app/shared/model/plan-contratado.model';
import { getEntities as getPlanContratados } from 'app/entities/plan-contratado/plan-contratado.reducer';
import { IClub } from 'app/shared/model/club.model';
import { getEntity, updateEntity, createEntity, reset } from './club.reducer';

export const ClubUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const adminClubs = useAppSelector(state => state.adminClub.entities);
  const planContratados = useAppSelector(state => state.planContratado.entities);
  const clubEntity = useAppSelector(state => state.club.entity);
  const loading = useAppSelector(state => state.club.loading);
  const updating = useAppSelector(state => state.club.updating);
  const updateSuccess = useAppSelector(state => state.club.updateSuccess);
  const handleClose = () => {
    props.history.push('/club');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getAdminClubs({}));
    dispatch(getPlanContratados({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    values.createdDate = convertDateTimeToServer(values.createdDate);
    values.updatedDate = convertDateTimeToServer(values.updatedDate);

    const entity = {
      ...clubEntity,
      ...values,
      adminClub: adminClubs.find(it => it.id.toString() === values.adminClub.toString()),
      planContratado: planContratados.find(it => it.id.toString() === values.planContratado.toString()),
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
          createdDate: displayDefaultDateTime(),
          updatedDate: displayDefaultDateTime(),
        }
      : {
          ...clubEntity,
          createdDate: convertDateTimeFromServer(clubEntity.createdDate),
          updatedDate: convertDateTimeFromServer(clubEntity.updatedDate),
          adminClub: clubEntity?.adminClub?.id,
          planContratado: clubEntity?.planContratado?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="fidelizacion2App.club.home.createOrEditLabel" data-cy="ClubCreateUpdateHeading">
            <Translate contentKey="fidelizacion2App.club.home.createOrEditLabel">Create or edit a Club</Translate>
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
                  id="club-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('fidelizacion2App.club.nombre')}
                id="club-nombre"
                name="nombre"
                data-cy="nombre"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('fidelizacion2App.club.direccion')}
                id="club-direccion"
                name="direccion"
                data-cy="direccion"
                type="text"
              />
              <ValidatedField
                label={translate('fidelizacion2App.club.estado')}
                id="club-estado"
                name="estado"
                data-cy="estado"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('fidelizacion2App.club.createdDate')}
                id="club-createdDate"
                name="createdDate"
                data-cy="createdDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('fidelizacion2App.club.updatedDate')}
                id="club-updatedDate"
                name="updatedDate"
                data-cy="updatedDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                id="club-adminClub"
                name="adminClub"
                data-cy="adminClub"
                label={translate('fidelizacion2App.club.adminClub')}
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
                id="club-planContratado"
                name="planContratado"
                data-cy="planContratado"
                label={translate('fidelizacion2App.club.planContratado')}
                type="select"
                required
              >
                <option value="" key="0" />
                {planContratados
                  ? planContratados.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <FormText>
                <Translate contentKey="entity.validation.required">This field is required.</Translate>
              </FormText>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/club" replace color="info">
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

export default ClubUpdate;
