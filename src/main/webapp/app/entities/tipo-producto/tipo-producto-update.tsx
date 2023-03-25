import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IClub } from 'app/shared/model/club.model';
import { getEntities as getClubs } from 'app/entities/club/club.reducer';
import { ITipoProducto } from 'app/shared/model/tipo-producto.model';
import { getEntity, updateEntity, createEntity, reset } from './tipo-producto.reducer';

export const TipoProductoUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const clubs = useAppSelector(state => state.club.entities);
  const tipoProductoEntity = useAppSelector(state => state.tipoProducto.entity);
  const loading = useAppSelector(state => state.tipoProducto.loading);
  const updating = useAppSelector(state => state.tipoProducto.updating);
  const updateSuccess = useAppSelector(state => state.tipoProducto.updateSuccess);
  const handleClose = () => {
    props.history.push('/tipo-producto');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getClubs({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...tipoProductoEntity,
      ...values,
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
      ? {}
      : {
          ...tipoProductoEntity,
          club: tipoProductoEntity?.club?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="fidelizacion2App.tipoProducto.home.createOrEditLabel" data-cy="TipoProductoCreateUpdateHeading">
            <Translate contentKey="fidelizacion2App.tipoProducto.home.createOrEditLabel">Create or edit a TipoProducto</Translate>
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
                  id="tipo-producto-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('fidelizacion2App.tipoProducto.estado')}
                id="tipo-producto-estado"
                name="estado"
                data-cy="estado"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('fidelizacion2App.tipoProducto.name')}
                id="tipo-producto-name"
                name="name"
                data-cy="name"
                type="text"
              />
              <ValidatedField
                id="tipo-producto-club"
                name="club"
                data-cy="club"
                label={translate('fidelizacion2App.tipoProducto.club')}
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/tipo-producto" replace color="info">
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

export default TipoProductoUpdate;
