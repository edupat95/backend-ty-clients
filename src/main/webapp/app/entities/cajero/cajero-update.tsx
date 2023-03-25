import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ITrabajador } from 'app/shared/model/trabajador.model';
import { getEntities as getTrabajadors } from 'app/entities/trabajador/trabajador.reducer';
import { ICaja } from 'app/shared/model/caja.model';
import { getEntities as getCajas } from 'app/entities/caja/caja.reducer';
import { ICajero } from 'app/shared/model/cajero.model';
import { getEntity, updateEntity, createEntity, reset } from './cajero.reducer';

export const CajeroUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const trabajadors = useAppSelector(state => state.trabajador.entities);
  const cajas = useAppSelector(state => state.caja.entities);
  const cajeroEntity = useAppSelector(state => state.cajero.entity);
  const loading = useAppSelector(state => state.cajero.loading);
  const updating = useAppSelector(state => state.cajero.updating);
  const updateSuccess = useAppSelector(state => state.cajero.updateSuccess);
  const handleClose = () => {
    props.history.push('/cajero');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getTrabajadors({}));
    dispatch(getCajas({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    values.creadDate = convertDateTimeToServer(values.creadDate);
    values.updatedDate = convertDateTimeToServer(values.updatedDate);

    const entity = {
      ...cajeroEntity,
      ...values,
      trabajador: trabajadors.find(it => it.id.toString() === values.trabajador.toString()),
      caja: cajas.find(it => it.id.toString() === values.caja.toString()),
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
          creadDate: displayDefaultDateTime(),
          updatedDate: displayDefaultDateTime(),
        }
      : {
          ...cajeroEntity,
          creadDate: convertDateTimeFromServer(cajeroEntity.creadDate),
          updatedDate: convertDateTimeFromServer(cajeroEntity.updatedDate),
          trabajador: cajeroEntity?.trabajador?.id,
          caja: cajeroEntity?.caja?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="fidelizacion2App.cajero.home.createOrEditLabel" data-cy="CajeroCreateUpdateHeading">
            <Translate contentKey="fidelizacion2App.cajero.home.createOrEditLabel">Create or edit a Cajero</Translate>
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
                  id="cajero-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('fidelizacion2App.cajero.plataDeCambio')}
                id="cajero-plataDeCambio"
                name="plataDeCambio"
                data-cy="plataDeCambio"
                type="text"
              />
              <ValidatedField
                label={translate('fidelizacion2App.cajero.estado')}
                id="cajero-estado"
                name="estado"
                data-cy="estado"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('fidelizacion2App.cajero.creadDate')}
                id="cajero-creadDate"
                name="creadDate"
                data-cy="creadDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('fidelizacion2App.cajero.updatedDate')}
                id="cajero-updatedDate"
                name="updatedDate"
                data-cy="updatedDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                id="cajero-trabajador"
                name="trabajador"
                data-cy="trabajador"
                label={translate('fidelizacion2App.cajero.trabajador')}
                type="select"
                required
              >
                <option value="" key="0" />
                {trabajadors
                  ? trabajadors.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <FormText>
                <Translate contentKey="entity.validation.required">This field is required.</Translate>
              </FormText>
              <ValidatedField id="cajero-caja" name="caja" data-cy="caja" label={translate('fidelizacion2App.cajero.caja')} type="select">
                <option value="" key="0" />
                {cajas
                  ? cajas.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/cajero" replace color="info">
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

export default CajeroUpdate;
