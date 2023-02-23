import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './orders.reducer';

export const OrdersDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const ordersEntity = useAppSelector(state => state.orders.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="ordersDetailsHeading">Orders</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">Id</span>
          </dt>
          <dd>{ordersEntity.id}</dd>
          <dt>
            <span id="deliveredDate">Delivered Date</span>
          </dt>
          <dd>
            {ordersEntity.deliveredDate ? (
              <TextFormat value={ordersEntity.deliveredDate} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="note">Note</span>
          </dt>
          <dd>{ordersEntity.note}</dd>
          <dt>
            <span id="orderDate">Order Date</span>
          </dt>
          <dd>
            {ordersEntity.orderDate ? <TextFormat value={ordersEntity.orderDate} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="orderStatus">Order Status</span>
          </dt>
          <dd>{ordersEntity.orderStatus}</dd>
          <dt>
            <span id="totalPrice">Total Price</span>
          </dt>
          <dd>{ordersEntity.totalPrice}</dd>
          <dt>
            <span id="customerId">Customer Id</span>
          </dt>
          <dd>{ordersEntity.customerId}</dd>
        </dl>
        <Button tag={Link} to="/orders" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Quay lại</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/orders/${ordersEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Sửa</span>
        </Button>
      </Col>
    </Row>
  );
};

export default OrdersDetail;
