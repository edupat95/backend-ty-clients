import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IDocumento } from 'app/shared/model/documento.model';
import { getEntities as getDocumentos } from 'app/entities/documento/documento.reducer';
import { IAsociado } from 'app/shared/model/asociado.model';
import { getEntity, updateEntity, createEntity, reset } from './asociado.reducer';

export const AsociadoUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const documentos = useAppSelector(state => state.documento.entities);
  const asociadoEntity = useAppSelector(state => state.asociado.entity);
  const loading = useAppSelector(state => state.asociado.loading);
  const updating = useAppSelector(state => state.asociado.updating);
  const updateSuccess = useAppSelector(state => state.asociado.updateSuccess);
  const handleClose = () => {
    props.history.push('/asociado');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getDocumentos({}));
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
      ...asociadoEntity,
      ...values,
      documento: documentos.find(it => it.id.toString() === values.documento.toString()),
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
          ...asociadoEntity,
          createdDate: convertDateTimeFromServer(asociadoEntity.createdDate),
          updatedDate: convertDateTimeFromServer(asociadoEntity.updatedDate),
          documento: asociadoEntity?.documento?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="fidelizacion2App.asociado.home.createOrEditLabel" data-cy="AsociadoCreateUpdateHeading">
            <Translate contentKey="fidelizacion2App.asociado.home.createOrEditLabel">Create or edit a Asociado</Translate>
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
                  id="asociado-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('fidelizacion2App.asociado.identificadorGeneral')}
                id="asociado-identificadorGeneral"
                name="identificadorGeneral"
                data-cy="identificadorGeneral"
                type="text"
              />
              <ValidatedField
                label={translate('fidelizacion2App.asociado.estado')}
                id="asociado-estado"
                name="estado"
                data-cy="estado"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('fidelizacion2App.asociado.createdDate')}
                id="asociado-createdDate"
                name="createdDate"
                data-cy="createdDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('fidelizacion2App.asociado.updatedDate')}
                id="asociado-updatedDate"
                name="updatedDate"
                data-cy="updatedDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                id="asociado-documento"
                name="documento"
                data-cy="documento"
                label={translate('fidelizacion2App.asociado.documento')}
                type="select"
                required
              >
                <option value="" key="0" />
                {documentos
                  ? documentos.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <FormText>
                <Translate contentKey="entity.validation.required">This field is required.</Translate>
              </FormText>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/asociado" replace color="info">
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

export default AsociadoUpdate;
