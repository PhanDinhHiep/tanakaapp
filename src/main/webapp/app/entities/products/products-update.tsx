import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IProducts } from 'app/shared/model/products.model';
import { getEntity, updateEntity, createEntity, reset } from './products.reducer';

export const ProductsUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const productsEntity = useAppSelector(state => state.products.entity);
  const loading = useAppSelector(state => state.products.loading);
  const updating = useAppSelector(state => state.products.updating);
  const updateSuccess = useAppSelector(state => state.products.updateSuccess);

  const handleClose = () => {
    navigate('/products' + location.search);
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
      ...productsEntity,
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
          ...productsEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="tanakaApp.products.home.createOrEditLabel" data-cy="ProductsCreateUpdateHeading">
            Thêm mới hoặc cập nhật Products
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="products-id" label="Id" validate={{ required: true }} /> : null}
              <ValidatedField label="Description" id="products-description" name="description" data-cy="description" type="text" />
              <ValidatedField label="Name" id="products-name" name="name" data-cy="name" type="text" />
              <ValidatedField label="Price" id="products-price" name="price" data-cy="price" type="text" />
              <ValidatedField label="Status" id="products-status" name="status" data-cy="status" type="text" />
              <ValidatedField label="Category Id" id="products-categoryId" name="categoryId" data-cy="categoryId" type="text" />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/products" replace color="info">
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

export default ProductsUpdate;
