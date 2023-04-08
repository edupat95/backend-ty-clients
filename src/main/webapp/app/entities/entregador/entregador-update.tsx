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
import { IEntregador } from 'app/shared/model/entregador.model';
import { getEntity, updateEntity, createEntity, reset } from './entregador.reducer';

export const EntregadorUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const trabajadors = useAppSelector(state => state.trabajador.entities);
  const entregadorEntity = useAppSelector(state => state.entregador.entity);
  const loading = useAppSelector(state => state.entregador.loading);
  const updating = useAppSelector(state => state.entregador.updating);
  const updateSuccess = useAppSelector(state => state.entregador.updateSuccess);
  const handleClose = () => {
    props.history.push('/entregador');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getTrabajadors({}));
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
      ...entregadorEntity,
      ...values,
      trabajador: trabajadors.find(it => it.id.toString() === values.trabajador.toString()),
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
          ...entregadorEntity,
          createdDate: convertDateTimeFromServer(entregadorEntity.createdDate),
          updatedDate: convertDateTimeFromServer(entregadorEntity.updatedDate),
          trabajador: entregadorEntity?.trabajador?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="fidelizacion2App.entregador.home.createOrEditLabel" data-cy="EntregadorCreateUpdateHeading">
            <Translate contentKey="fidelizacion2App.entregador.home.createOrEditLabel">Create or edit a Entregador</Translate>
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
                  id="entregador-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('fidelizacion2App.entregador.estado')}
                id="entregador-estado"
                name="estado"
                data-cy="estado"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('fidelizacion2App.entregador.createdDate')}
                id="entregador-createdDate"
                name="createdDate"
                data-cy="createdDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('fidelizacion2App.entregador.updatedDate')}
                id="entregador-updatedDate"
                name="updatedDate"
                data-cy="updatedDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                id="entregador-trabajador"
                name="trabajador"
                data-cy="trabajador"
                label={translate('fidelizacion2App.entregador.trabajador')}
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/entregador" replace color="info">
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

export default EntregadorUpdate;
