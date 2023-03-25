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
import { IEvento } from 'app/shared/model/evento.model';
import { getEntities as getEventos } from 'app/entities/evento/evento.reducer';
import { IAcceso } from 'app/shared/model/acceso.model';
import { getEntity, updateEntity, createEntity, reset } from './acceso.reducer';

export const AccesoUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const asociados = useAppSelector(state => state.asociado.entities);
  const eventos = useAppSelector(state => state.evento.entities);
  const accesoEntity = useAppSelector(state => state.acceso.entity);
  const loading = useAppSelector(state => state.acceso.loading);
  const updating = useAppSelector(state => state.acceso.updating);
  const updateSuccess = useAppSelector(state => state.acceso.updateSuccess);
  const handleClose = () => {
    props.history.push('/acceso');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getAsociados({}));
    dispatch(getEventos({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    values.fechaCanje = convertDateTimeToServer(values.fechaCanje);
    values.createdDate = convertDateTimeToServer(values.createdDate);
    values.updatedDate = convertDateTimeToServer(values.updatedDate);

    const entity = {
      ...accesoEntity,
      ...values,
      asociado: asociados.find(it => it.id.toString() === values.asociado.toString()),
      evento: eventos.find(it => it.id.toString() === values.evento.toString()),
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
          fechaCanje: displayDefaultDateTime(),
          createdDate: displayDefaultDateTime(),
          updatedDate: displayDefaultDateTime(),
        }
      : {
          ...accesoEntity,
          fechaCanje: convertDateTimeFromServer(accesoEntity.fechaCanje),
          createdDate: convertDateTimeFromServer(accesoEntity.createdDate),
          updatedDate: convertDateTimeFromServer(accesoEntity.updatedDate),
          asociado: accesoEntity?.asociado?.id,
          evento: accesoEntity?.evento?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="fidelizacion2App.acceso.home.createOrEditLabel" data-cy="AccesoCreateUpdateHeading">
            <Translate contentKey="fidelizacion2App.acceso.home.createOrEditLabel">Create or edit a Acceso</Translate>
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
                  id="acceso-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('fidelizacion2App.acceso.costoPuntos')}
                id="acceso-costoPuntos"
                name="costoPuntos"
                data-cy="costoPuntos"
                type="text"
              />
              <ValidatedField
                label={translate('fidelizacion2App.acceso.fechaCanje')}
                id="acceso-fechaCanje"
                name="fechaCanje"
                data-cy="fechaCanje"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('fidelizacion2App.acceso.estado')}
                id="acceso-estado"
                name="estado"
                data-cy="estado"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('fidelizacion2App.acceso.createdDate')}
                id="acceso-createdDate"
                name="createdDate"
                data-cy="createdDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('fidelizacion2App.acceso.updatedDate')}
                id="acceso-updatedDate"
                name="updatedDate"
                data-cy="updatedDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                id="acceso-asociado"
                name="asociado"
                data-cy="asociado"
                label={translate('fidelizacion2App.acceso.asociado')}
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
                id="acceso-evento"
                name="evento"
                data-cy="evento"
                label={translate('fidelizacion2App.acceso.evento')}
                type="select"
                required
              >
                <option value="" key="0" />
                {eventos
                  ? eventos.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <FormText>
                <Translate contentKey="entity.validation.required">This field is required.</Translate>
              </FormText>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/acceso" replace color="info">
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

export default AccesoUpdate;
