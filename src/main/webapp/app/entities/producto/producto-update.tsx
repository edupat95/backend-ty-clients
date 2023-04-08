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
import { getEntities as getTipoProductos } from 'app/entities/tipo-producto/tipo-producto.reducer';
import { IProducto } from 'app/shared/model/producto.model';
import { getEntity, updateEntity, createEntity, reset } from './producto.reducer';

export const ProductoUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const clubs = useAppSelector(state => state.club.entities);
  const tipoProductos = useAppSelector(state => state.tipoProducto.entities);
  const productoEntity = useAppSelector(state => state.producto.entity);
  const loading = useAppSelector(state => state.producto.loading);
  const updating = useAppSelector(state => state.producto.updating);
  const updateSuccess = useAppSelector(state => state.producto.updateSuccess);
  const handleClose = () => {
    props.history.push('/producto');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getClubs({}));
    dispatch(getTipoProductos({}));
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
      ...productoEntity,
      ...values,
      club: clubs.find(it => it.id.toString() === values.club.toString()),
      tipoProducto: tipoProductos.find(it => it.id.toString() === values.tipoProducto.toString()),
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
          ...productoEntity,
          createdDate: convertDateTimeFromServer(productoEntity.createdDate),
          updatedDate: convertDateTimeFromServer(productoEntity.updatedDate),
          club: productoEntity?.club?.id,
          tipoProducto: productoEntity?.tipoProducto?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="fidelizacion2App.producto.home.createOrEditLabel" data-cy="ProductoCreateUpdateHeading">
            <Translate contentKey="fidelizacion2App.producto.home.createOrEditLabel">Create or edit a Producto</Translate>
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
                  id="producto-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('fidelizacion2App.producto.nombre')}
                id="producto-nombre"
                name="nombre"
                data-cy="nombre"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('fidelizacion2App.producto.precio')}
                id="producto-precio"
                name="precio"
                data-cy="precio"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('fidelizacion2App.producto.costo')}
                id="producto-costo"
                name="costo"
                data-cy="costo"
                type="text"
              />
              <ValidatedField
                label={translate('fidelizacion2App.producto.precioPuntos')}
                id="producto-precioPuntos"
                name="precioPuntos"
                data-cy="precioPuntos"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('fidelizacion2App.producto.puntosRecompensa')}
                id="producto-puntosRecompensa"
                name="puntosRecompensa"
                data-cy="puntosRecompensa"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('fidelizacion2App.producto.descripcion')}
                id="producto-descripcion"
                name="descripcion"
                data-cy="descripcion"
                type="text"
              />
              <ValidatedField
                label={translate('fidelizacion2App.producto.estado')}
                id="producto-estado"
                name="estado"
                data-cy="estado"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('fidelizacion2App.producto.createdDate')}
                id="producto-createdDate"
                name="createdDate"
                data-cy="createdDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('fidelizacion2App.producto.updatedDate')}
                id="producto-updatedDate"
                name="updatedDate"
                data-cy="updatedDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                id="producto-club"
                name="club"
                data-cy="club"
                label={translate('fidelizacion2App.producto.club')}
                type="select"
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
              <ValidatedField
                id="producto-tipoProducto"
                name="tipoProducto"
                data-cy="tipoProducto"
                label={translate('fidelizacion2App.producto.tipoProducto')}
                type="select"
              >
                <option value="" key="0" />
                {tipoProductos
                  ? tipoProductos.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/producto" replace color="info">
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

export default ProductoUpdate;
