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
import { IDocumento } from 'app/shared/model/documento.model';
import { getEntity, updateEntity, createEntity, reset } from './documento.reducer';

export const DocumentoUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const users = useAppSelector(state => state.userManagement.users);
  const documentoEntity = useAppSelector(state => state.documento.entity);
  const loading = useAppSelector(state => state.documento.loading);
  const updating = useAppSelector(state => state.documento.updating);
  const updateSuccess = useAppSelector(state => state.documento.updateSuccess);
  const handleClose = () => {
    props.history.push('/documento');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getUsers({}));
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
      ...documentoEntity,
      ...values,
      user: users.find(it => it.id.toString() === values.user.toString()),
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
          ...documentoEntity,
          createdDate: convertDateTimeFromServer(documentoEntity.createdDate),
          updatedDate: convertDateTimeFromServer(documentoEntity.updatedDate),
          user: documentoEntity?.user?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="fidelizacion2App.documento.home.createOrEditLabel" data-cy="DocumentoCreateUpdateHeading">
            <Translate contentKey="fidelizacion2App.documento.home.createOrEditLabel">Create or edit a Documento</Translate>
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
                  id="documento-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('fidelizacion2App.documento.numeroTramite')}
                id="documento-numeroTramite"
                name="numeroTramite"
                data-cy="numeroTramite"
                type="text"
              />
              <ValidatedField
                label={translate('fidelizacion2App.documento.apellidos')}
                id="documento-apellidos"
                name="apellidos"
                data-cy="apellidos"
                type="text"
              />
              <ValidatedField
                label={translate('fidelizacion2App.documento.nombres')}
                id="documento-nombres"
                name="nombres"
                data-cy="nombres"
                type="text"
              />
              <ValidatedField
                label={translate('fidelizacion2App.documento.sexo')}
                id="documento-sexo"
                name="sexo"
                data-cy="sexo"
                type="text"
              />
              <ValidatedField
                label={translate('fidelizacion2App.documento.numeroDni')}
                id="documento-numeroDni"
                name="numeroDni"
                data-cy="numeroDni"
                type="text"
              />
              <ValidatedField
                label={translate('fidelizacion2App.documento.ejemplar')}
                id="documento-ejemplar"
                name="ejemplar"
                data-cy="ejemplar"
                type="text"
              />
              <ValidatedField
                label={translate('fidelizacion2App.documento.nacimiento')}
                id="documento-nacimiento"
                name="nacimiento"
                data-cy="nacimiento"
                type="date"
              />
              <ValidatedField
                label={translate('fidelizacion2App.documento.fechaEmision')}
                id="documento-fechaEmision"
                name="fechaEmision"
                data-cy="fechaEmision"
                type="date"
              />
              <ValidatedField
                label={translate('fidelizacion2App.documento.inicioFinCuil')}
                id="documento-inicioFinCuil"
                name="inicioFinCuil"
                data-cy="inicioFinCuil"
                type="text"
              />
              <ValidatedField
                label={translate('fidelizacion2App.documento.createdDate')}
                id="documento-createdDate"
                name="createdDate"
                data-cy="createdDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('fidelizacion2App.documento.updatedDate')}
                id="documento-updatedDate"
                name="updatedDate"
                data-cy="updatedDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                id="documento-user"
                name="user"
                data-cy="user"
                label={translate('fidelizacion2App.documento.user')}
                type="select"
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/documento" replace color="info">
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

export default DocumentoUpdate;
