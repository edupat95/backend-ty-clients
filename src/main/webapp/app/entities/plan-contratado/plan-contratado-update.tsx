import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IPlan } from 'app/shared/model/plan.model';
import { getEntities as getPlans } from 'app/entities/plan/plan.reducer';
import { IPlanContratado } from 'app/shared/model/plan-contratado.model';
import { getEntity, updateEntity, createEntity, reset } from './plan-contratado.reducer';

export const PlanContratadoUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const plans = useAppSelector(state => state.plan.entities);
  const planContratadoEntity = useAppSelector(state => state.planContratado.entity);
  const loading = useAppSelector(state => state.planContratado.loading);
  const updating = useAppSelector(state => state.planContratado.updating);
  const updateSuccess = useAppSelector(state => state.planContratado.updateSuccess);
  const handleClose = () => {
    props.history.push('/plan-contratado');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getPlans({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    values.fechaVencimiento = convertDateTimeToServer(values.fechaVencimiento);
    values.createdDate = convertDateTimeToServer(values.createdDate);
    values.updatedDate = convertDateTimeToServer(values.updatedDate);

    const entity = {
      ...planContratadoEntity,
      ...values,
      plan: plans.find(it => it.id.toString() === values.plan.toString()),
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
          fechaVencimiento: displayDefaultDateTime(),
          createdDate: displayDefaultDateTime(),
          updatedDate: displayDefaultDateTime(),
        }
      : {
          ...planContratadoEntity,
          fechaVencimiento: convertDateTimeFromServer(planContratadoEntity.fechaVencimiento),
          createdDate: convertDateTimeFromServer(planContratadoEntity.createdDate),
          updatedDate: convertDateTimeFromServer(planContratadoEntity.updatedDate),
          plan: planContratadoEntity?.plan?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="fidelizacion2App.planContratado.home.createOrEditLabel" data-cy="PlanContratadoCreateUpdateHeading">
            <Translate contentKey="fidelizacion2App.planContratado.home.createOrEditLabel">Create or edit a PlanContratado</Translate>
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
                  id="plan-contratado-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('fidelizacion2App.planContratado.tiempoContratado')}
                id="plan-contratado-tiempoContratado"
                name="tiempoContratado"
                data-cy="tiempoContratado"
                type="text"
              />
              <ValidatedField
                label={translate('fidelizacion2App.planContratado.fechaVencimiento')}
                id="plan-contratado-fechaVencimiento"
                name="fechaVencimiento"
                data-cy="fechaVencimiento"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('fidelizacion2App.planContratado.estado')}
                id="plan-contratado-estado"
                name="estado"
                data-cy="estado"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('fidelizacion2App.planContratado.createdDate')}
                id="plan-contratado-createdDate"
                name="createdDate"
                data-cy="createdDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('fidelizacion2App.planContratado.updatedDate')}
                id="plan-contratado-updatedDate"
                name="updatedDate"
                data-cy="updatedDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                id="plan-contratado-plan"
                name="plan"
                data-cy="plan"
                label={translate('fidelizacion2App.planContratado.plan')}
                type="select"
                required
              >
                <option value="" key="0" />
                {plans
                  ? plans.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <FormText>
                <Translate contentKey="entity.validation.required">This field is required.</Translate>
              </FormText>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/plan-contratado" replace color="info">
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

export default PlanContratadoUpdate;
