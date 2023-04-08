import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IMesa } from 'app/shared/model/mesa.model';
import { getEntities as getMesas } from 'app/entities/mesa/mesa.reducer';
import { IProducto } from 'app/shared/model/producto.model';
import { getEntities as getProductos } from 'app/entities/producto/producto.reducer';
import { IProductoMesa } from 'app/shared/model/producto-mesa.model';
import { getEntity, updateEntity, createEntity, reset } from './producto-mesa.reducer';

export const ProductoMesaUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const mesas = useAppSelector(state => state.mesa.entities);
  const productos = useAppSelector(state => state.producto.entities);
  const productoMesaEntity = useAppSelector(state => state.productoMesa.entity);
  const loading = useAppSelector(state => state.productoMesa.loading);
  const updating = useAppSelector(state => state.productoMesa.updating);
  const updateSuccess = useAppSelector(state => state.productoMesa.updateSuccess);
  const handleClose = () => {
    props.history.push('/producto-mesa');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getMesas({}));
    dispatch(getProductos({}));
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
      ...productoMesaEntity,
      ...values,
      mesa: mesas.find(it => it.id.toString() === values.mesa.toString()),
      producto: productos.find(it => it.id.toString() === values.producto.toString()),
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
          ...productoMesaEntity,
          createdDate: convertDateTimeFromServer(productoMesaEntity.createdDate),
          updatedDate: convertDateTimeFromServer(productoMesaEntity.updatedDate),
          mesa: productoMesaEntity?.mesa?.id,
          producto: productoMesaEntity?.producto?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="fidelizacion2App.productoMesa.home.createOrEditLabel" data-cy="ProductoMesaCreateUpdateHeading">
            <Translate contentKey="fidelizacion2App.productoMesa.home.createOrEditLabel">Create or edit a ProductoMesa</Translate>
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
                  id="producto-mesa-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('fidelizacion2App.productoMesa.costoTotal')}
                id="producto-mesa-costoTotal"
                name="costoTotal"
                data-cy="costoTotal"
                type="text"
              />
              <ValidatedField
                label={translate('fidelizacion2App.productoMesa.costoTotalPuntos')}
                id="producto-mesa-costoTotalPuntos"
                name="costoTotalPuntos"
                data-cy="costoTotalPuntos"
                type="text"
              />
              <ValidatedField
                label={translate('fidelizacion2App.productoMesa.cantidad')}
                id="producto-mesa-cantidad"
                name="cantidad"
                data-cy="cantidad"
                type="text"
              />
              <ValidatedField
                label={translate('fidelizacion2App.productoMesa.estado')}
                id="producto-mesa-estado"
                name="estado"
                data-cy="estado"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('fidelizacion2App.productoMesa.createdDate')}
                id="producto-mesa-createdDate"
                name="createdDate"
                data-cy="createdDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('fidelizacion2App.productoMesa.updatedDate')}
                id="producto-mesa-updatedDate"
                name="updatedDate"
                data-cy="updatedDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                id="producto-mesa-mesa"
                name="mesa"
                data-cy="mesa"
                label={translate('fidelizacion2App.productoMesa.mesa')}
                type="select"
              >
                <option value="" key="0" />
                {mesas
                  ? mesas.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="producto-mesa-producto"
                name="producto"
                data-cy="producto"
                label={translate('fidelizacion2App.productoMesa.producto')}
                type="select"
                required
              >
                <option value="" key="0" />
                {productos
                  ? productos.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <FormText>
                <Translate contentKey="entity.validation.required">This field is required.</Translate>
              </FormText>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/producto-mesa" replace color="info">
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

export default ProductoMesaUpdate;
