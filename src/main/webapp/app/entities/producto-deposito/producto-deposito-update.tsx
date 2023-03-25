import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IDeposito } from 'app/shared/model/deposito.model';
import { getEntities as getDepositos } from 'app/entities/deposito/deposito.reducer';
import { IProducto } from 'app/shared/model/producto.model';
import { getEntities as getProductos } from 'app/entities/producto/producto.reducer';
import { IProductoDeposito } from 'app/shared/model/producto-deposito.model';
import { getEntity, updateEntity, createEntity, reset } from './producto-deposito.reducer';

export const ProductoDepositoUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const depositos = useAppSelector(state => state.deposito.entities);
  const productos = useAppSelector(state => state.producto.entities);
  const productoDepositoEntity = useAppSelector(state => state.productoDeposito.entity);
  const loading = useAppSelector(state => state.productoDeposito.loading);
  const updating = useAppSelector(state => state.productoDeposito.updating);
  const updateSuccess = useAppSelector(state => state.productoDeposito.updateSuccess);
  const handleClose = () => {
    props.history.push('/producto-deposito');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getDepositos({}));
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
      ...productoDepositoEntity,
      ...values,
      deposito: depositos.find(it => it.id.toString() === values.deposito.toString()),
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
          ...productoDepositoEntity,
          createdDate: convertDateTimeFromServer(productoDepositoEntity.createdDate),
          updatedDate: convertDateTimeFromServer(productoDepositoEntity.updatedDate),
          deposito: productoDepositoEntity?.deposito?.id,
          producto: productoDepositoEntity?.producto?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="fidelizacion2App.productoDeposito.home.createOrEditLabel" data-cy="ProductoDepositoCreateUpdateHeading">
            <Translate contentKey="fidelizacion2App.productoDeposito.home.createOrEditLabel">Create or edit a ProductoDeposito</Translate>
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
                  id="producto-deposito-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('fidelizacion2App.productoDeposito.cantidad')}
                id="producto-deposito-cantidad"
                name="cantidad"
                data-cy="cantidad"
                type="text"
              />
              <ValidatedField
                label={translate('fidelizacion2App.productoDeposito.createdDate')}
                id="producto-deposito-createdDate"
                name="createdDate"
                data-cy="createdDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('fidelizacion2App.productoDeposito.updatedDate')}
                id="producto-deposito-updatedDate"
                name="updatedDate"
                data-cy="updatedDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                id="producto-deposito-deposito"
                name="deposito"
                data-cy="deposito"
                label={translate('fidelizacion2App.productoDeposito.deposito')}
                type="select"
                required
              >
                <option value="" key="0" />
                {depositos
                  ? depositos.map(otherEntity => (
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
                id="producto-deposito-producto"
                name="producto"
                data-cy="producto"
                label={translate('fidelizacion2App.productoDeposito.producto')}
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/producto-deposito" replace color="info">
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

export default ProductoDepositoUpdate;
