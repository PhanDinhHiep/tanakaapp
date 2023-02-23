import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IOrderdetails } from 'app/shared/model/orderdetails.model';
import { getEntity, updateEntity, createEntity, reset } from './orderdetails.reducer';

export const OrderdetailsUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const orderdetailsEntity = useAppSelector(state => state.orderdetails.entity);
  const loading = useAppSelector(state => state.orderdetails.loading);
  const updating = useAppSelector(state => state.orderdetails.updating);
  const updateSuccess = useAppSelector(state => state.orderdetails.updateSuccess);

  const handleClose = () => {
    navigate('/orderdetails' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...orderdetailsEntity,
      ...values,
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
          ...orderdetailsEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="tanakaApp.orderdetails.home.createOrEditLabel" data-cy="OrderdetailsCreateUpdateHeading">
            Thêm mới hoặc cập nhật Orderdetails
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="orderdetails-id" label="Id" validate={{ required: true }} /> : null}
              <ValidatedField label="Color" id="orderdetails-color" name="color" data-cy="color" type="text" />
              <ValidatedField label="Price" id="orderdetails-price" name="price" data-cy="price" type="text" />
              <ValidatedField label="Quantity" id="orderdetails-quantity" name="quantity" data-cy="quantity" type="text" />
              <ValidatedField label="Sizes" id="orderdetails-sizes" name="sizes" data-cy="sizes" type="text" />
              <ValidatedField label="Unit Price" id="orderdetails-unitPrice" name="unitPrice" data-cy="unitPrice" type="text" />
              <ValidatedField label="Order Id" id="orderdetails-orderId" name="orderId" data-cy="orderId" type="text" />
              <ValidatedField label="Product Id" id="orderdetails-productId" name="productId" data-cy="productId" type="text" />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/orderdetails" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">Quay lại</span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp; Lưu
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default OrderdetailsUpdate;
