import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ICajero } from 'app/shared/model/cajero.model';
import { getEntities as getCajeros } from 'app/entities/cajero/cajero.reducer';
import { IAsociado } from 'app/shared/model/asociado.model';
import { getEntities as getAsociados } from 'app/entities/asociado/asociado.reducer';
import { IFormaPago } from 'app/shared/model/forma-pago.model';
import { getEntities as getFormaPagos } from 'app/entities/forma-pago/forma-pago.reducer';
import { IEntregador } from 'app/shared/model/entregador.model';
import { getEntities as getEntregadors } from 'app/entities/entregador/entregador.reducer';
import { IVenta } from 'app/shared/model/venta.model';
import { getEntity, updateEntity, createEntity, reset } from './venta.reducer';

export const VentaUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const cajeros = useAppSelector(state => state.cajero.entities);
  const asociados = useAppSelector(state => state.asociado.entities);
  const formaPagos = useAppSelector(state => state.formaPago.entities);
  const entregadors = useAppSelector(state => state.entregador.entities);
  const ventaEntity = useAppSelector(state => state.venta.entity);
  const loading = useAppSelector(state => state.venta.loading);
  const updating = useAppSelector(state => state.venta.updating);
  const updateSuccess = useAppSelector(state => state.venta.updateSuccess);
  const handleClose = () => {
    props.history.push('/venta');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getCajeros({}));
    dispatch(getAsociados({}));
    dispatch(getFormaPagos({}));
    dispatch(getEntregadors({}));
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
      ...ventaEntity,
      ...values,
      cajero: cajeros.find(it => it.id.toString() === values.cajero.toString()),
      asociado: asociados.find(it => it.id.toString() === values.asociado.toString()),
      formaPago: formaPagos.find(it => it.id.toString() === values.formaPago.toString()),
      entregador: entregadors.find(it => it.id.toString() === values.entregador.toString()),
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
          ...ventaEntity,
          createdDate: convertDateTimeFromServer(ventaEntity.createdDate),
          updatedDate: convertDateTimeFromServer(ventaEntity.updatedDate),
          cajero: ventaEntity?.cajero?.id,
          asociado: ventaEntity?.asociado?.id,
          formaPago: ventaEntity?.formaPago?.id,
          entregador: ventaEntity?.entregador?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="fidelizacion2App.venta.home.createOrEditLabel" data-cy="VentaCreateUpdateHeading">
            <Translate contentKey="fidelizacion2App.venta.home.createOrEditLabel">Create or edit a Venta</Translate>
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
                  id="venta-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('fidelizacion2App.venta.costoTotal')}
                id="venta-costoTotal"
                name="costoTotal"
                data-cy="costoTotal"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('fidelizacion2App.venta.costoTotalPuntos')}
                id="venta-costoTotalPuntos"
                name="costoTotalPuntos"
                data-cy="costoTotalPuntos"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('fidelizacion2App.venta.identificadorTicket')}
                id="venta-identificadorTicket"
                name="identificadorTicket"
                data-cy="identificadorTicket"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('fidelizacion2App.venta.entregado')}
                id="venta-entregado"
                name="entregado"
                data-cy="entregado"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('fidelizacion2App.venta.createdDate')}
                id="venta-createdDate"
                name="createdDate"
                data-cy="createdDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('fidelizacion2App.venta.updatedDate')}
                id="venta-updatedDate"
                name="updatedDate"
                data-cy="updatedDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                id="venta-cajero"
                name="cajero"
                data-cy="cajero"
                label={translate('fidelizacion2App.venta.cajero')}
                type="select"
                required
              >
                <option value="" key="0" />
                {cajeros
                  ? cajeros.map(otherEntity => (
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
                id="venta-asociado"
                name="asociado"
                data-cy="asociado"
                label={translate('fidelizacion2App.venta.asociado')}
                type="select"
              >
                <option value="" key="0" />
                {asociados
                  ? asociados.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="venta-formaPago"
                name="formaPago"
                data-cy="formaPago"
                label={translate('fidelizacion2App.venta.formaPago')}
                type="select"
              >
                <option value="" key="0" />
                {formaPagos
                  ? formaPagos.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="venta-entregador"
                name="entregador"
                data-cy="entregador"
                label={translate('fidelizacion2App.venta.entregador')}
                type="select"
              >
                <option value="" key="0" />
                {entregadors
                  ? entregadors.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/venta" replace color="info">
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

export default VentaUpdate;
