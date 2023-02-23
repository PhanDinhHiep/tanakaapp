import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './customers.reducer';

export const CustomersDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const customersEntity = useAppSelector(state => state.customers.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="customersDetailsHeading">Customers</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">Id</span>
          </dt>
          <dd>{customersEntity.id}</dd>
          <dt>
            <span id="phoneNumber">Phone Number</span>
          </dt>
          <dd>{customersEntity.phoneNumber}</dd>
          <dt>
            <span id="address">Address</span>
          </dt>
          <dd>{customersEntity.address}</dd>
          <dt>
            <span id="email">Email</span>
          </dt>
          <dd>{customersEntity.email}</dd>
          <dt>
            <span id="name">Name</span>
          </dt>
          <dd>{customersEntity.name}</dd>
        </dl>
        <Button tag={Link} to="/customers" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Quay lại</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/customers/${customersEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Sửa</span>
        </Button>
      </Col>
    </Row>
  );
};

export default CustomersDetail;
