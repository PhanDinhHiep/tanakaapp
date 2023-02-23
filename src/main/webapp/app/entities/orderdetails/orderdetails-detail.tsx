import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './orderdetails.reducer';

export const OrderdetailsDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const orderdetailsEntity = useAppSelector(state => state.orderdetails.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="orderdetailsDetailsHeading">Orderdetails</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">Id</span>
          </dt>
          <dd>{orderdetailsEntity.id}</dd>
          <dt>
            <span id="color">Color</span>
          </dt>
          <dd>{orderdetailsEntity.color}</dd>
          <dt>
            <span id="price">Price</span>
          </dt>
          <dd>{orderdetailsEntity.price}</dd>
          <dt>
            <span id="quantity">Quantity</span>
          </dt>
          <dd>{orderdetailsEntity.quantity}</dd>
          <dt>
            <span id="sizes">Sizes</span>
          </dt>
          <dd>{orderdetailsEntity.sizes}</dd>
          <dt>
            <span id="unitPrice">Unit Price</span>
          </dt>
          <dd>{orderdetailsEntity.unitPrice}</dd>
          <dt>
            <span id="orderId">Order Id</span>
          </dt>
          <dd>{orderdetailsEntity.orderId}</dd>
          <dt>
            <span id="productId">Product Id</span>
          </dt>
          <dd>{orderdetailsEntity.productId}</dd>
        </dl>
        <Button tag={Link} to="/orderdetails" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Quay lại</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/orderdetails/${orderdetailsEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Sửa</span>
        </Button>
      </Col>
    </Row>
  );
};

export default OrderdetailsDetail;
