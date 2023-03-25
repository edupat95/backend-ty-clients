import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IProducto } from 'app/shared/model/producto.model';
import { getEntities as getProductos } from 'app/entities/producto/producto.reducer';
import { ICaja } from 'app/shared/model/caja.model';
import { getEntities as getCajas } from 'app/entities/caja/caja.reducer';
import { IProductoCaja } from 'app/shared/model/producto-caja.model';
import { getEntity, updateEntity, createEntity, reset } from './producto-caja.reducer';

export const ProductoCajaUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const productos = useAppSelector(state => state.producto.entities);
  const cajas = useAppSelector(state => state.caja.entities);
  const productoCajaEntity = useAppSelector(state => state.productoCaja.entity);
  const loading = useAppSelector(state => state.productoCaja.loading);
  const updating = useAppSelector(state => state.productoCaja.updating);
  const updateSuccess = useAppSelector(state => state.productoCaja.updateSuccess);
  const handleClose = () => {
    props.history.push('/producto-caja');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getProductos({}));
    dispatch(getCajas({}));
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
      ...productoCajaEntity,
      ...values,
      producto: productos.find(it => it.id.toString() === values.producto.toString()),
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
          createdDate: displayDefaultDateTime(),
          updatedDate: displayDefaultDateTime(),
        }
      : {
          ...productoCajaEntity,
          createdDate: convertDateTimeFromServer(productoCajaEntity.createdDate),
          updatedDate: convertDateTimeFromServer(productoCajaEntity.updatedDate),
          producto: productoCajaEntity?.producto?.id,
          caja: productoCajaEntity?.caja?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="fidelizacion2App.productoCaja.home.createOrEditLabel" data-cy="ProductoCajaCreateUpdateHeading">
            <Translate contentKey="fidelizacion2App.productoCaja.home.createOrEditLabel">Create or edit a ProductoCaja</Translate>
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
                  id="producto-caja-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('fidelizacion2App.productoCaja.cantidad')}
                id="producto-caja-cantidad"
                name="cantidad"
                data-cy="cantidad"
                type="text"
              />
              <ValidatedField
                label={translate('fidelizacion2App.productoCaja.createdDate')}
                id="producto-caja-createdDate"
                name="createdDate"
                data-cy="createdDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('fidelizacion2App.productoCaja.updatedDate')}
                id="producto-caja-updatedDate"
                name="updatedDate"
                data-cy="updatedDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                id="producto-caja-producto"
                name="producto"
                data-cy="producto"
                label={translate('fidelizacion2App.productoCaja.producto')}
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
              <ValidatedField
                id="producto-caja-caja"
                name="caja"
                data-cy="caja"
                label={translate('fidelizacion2App.productoCaja.caja')}
                type="select"
                required
              >
                <option value="" key="0" />
                {cajas
                  ? cajas.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <FormText>
                <Translate contentKey="entity.validation.required">This field is required.</Translate>
              </FormText>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/producto-caja" replace color="info">
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

export default ProductoCajaUpdate;
