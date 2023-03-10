import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './products.reducer';

export const ProductsDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const productsEntity = useAppSelector(state => state.products.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="productsDetailsHeading">Products</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">Id</span>
          </dt>
          <dd>{productsEntity.id}</dd>
          <dt>
            <span id="description">Description</span>
          </dt>
          <dd>{productsEntity.description}</dd>
          <dt>
            <span id="name">Name</span>
          </dt>
          <dd>{productsEntity.name}</dd>
          <dt>
            <span id="price">Price</span>
          </dt>
          <dd>{productsEntity.price}</dd>
          <dt>
            <span id="status">Status</span>
          </dt>
          <dd>{productsEntity.status}</dd>
          <dt>
            <span id="categoryId">Category Id</span>
          </dt>
          <dd>{productsEntity.categoryId}</dd>
        </dl>
        <Button tag={Link} to="/products" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Quay lại</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/products/${productsEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Sửa</span>
        </Button>
      </Col>
    </Row>
  );
};

export default ProductsDetail;
