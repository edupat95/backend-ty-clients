import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IVenta } from 'app/shared/model/venta.model';
import { getEntities as getVentas } from 'app/entities/venta/venta.reducer';
import { IProducto } from 'app/shared/model/producto.model';
import { getEntities as getProductos } from 'app/entities/producto/producto.reducer';
import { IProductoVenta } from 'app/shared/model/producto-venta.model';
import { getEntity, updateEntity, createEntity, reset } from './producto-venta.reducer';

export const ProductoVentaUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const ventas = useAppSelector(state => state.venta.entities);
  const productos = useAppSelector(state => state.producto.entities);
  const productoVentaEntity = useAppSelector(state => state.productoVenta.entity);
  const loading = useAppSelector(state => state.productoVenta.loading);
  const updating = useAppSelector(state => state.productoVenta.updating);
  const updateSuccess = useAppSelector(state => state.productoVenta.updateSuccess);
  const handleClose = () => {
    props.history.push('/producto-venta');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getVentas({}));
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
      ...productoVentaEntity,
      ...values,
      venta: ventas.find(it => it.id.toString() === values.venta.toString()),
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
          ...productoVentaEntity,
          createdDate: convertDateTimeFromServer(productoVentaEntity.createdDate),
          updatedDate: convertDateTimeFromServer(productoVentaEntity.updatedDate),
          venta: productoVentaEntity?.venta?.id,
          producto: productoVentaEntity?.producto?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="fidelizacion2App.productoVenta.home.createOrEditLabel" data-cy="ProductoVentaCreateUpdateHeading">
            <Translate contentKey="fidelizacion2App.productoVenta.home.createOrEditLabel">Create or edit a ProductoVenta</Translate>
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
                  id="producto-venta-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('fidelizacion2App.productoVenta.costoTotal')}
                id="producto-venta-costoTotal"
                name="costoTotal"
                data-cy="costoTotal"
                type="text"
              />
              <ValidatedField
                label={translate('fidelizacion2App.productoVenta.costoTotalPuntos')}
                id="producto-venta-costoTotalPuntos"
                name="costoTotalPuntos"
                data-cy="costoTotalPuntos"
                type="text"
              />
              <ValidatedField
                label={translate('fidelizacion2App.productoVenta.cantidad')}
                id="producto-venta-cantidad"
                name="cantidad"
                data-cy="cantidad"
                type="text"
              />
              <ValidatedField
                label={translate('fidelizacion2App.productoVenta.createdDate')}
                id="producto-venta-createdDate"
                name="createdDate"
                data-cy="createdDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('fidelizacion2App.productoVenta.updatedDate')}
                id="producto-venta-updatedDate"
                name="updatedDate"
                data-cy="updatedDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                id="producto-venta-venta"
                name="venta"
                data-cy="venta"
                label={translate('fidelizacion2App.productoVenta.venta')}
                type="select"
                required
              >
                <option value="" key="0" />
                {ventas
                  ? ventas.map(otherEntity => (
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
                id="producto-venta-producto"
                name="producto"
                data-cy="producto"
                label={translate('fidelizacion2App.productoVenta.producto')}
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/producto-venta" replace color="info">
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

export default ProductoVentaUpdate;
