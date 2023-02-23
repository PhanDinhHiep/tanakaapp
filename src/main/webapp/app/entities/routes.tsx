import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Category from './category';
import Customers from './customers';
import Orders from './orders';
import Orderdetails from './orderdetails';
import Products from './products';
/* jhipster-needle-add-route-import - JHipster will add routes here */

export default () => {
  return (
    <div>
      <ErrorBoundaryRoutes>
        {/* prettier-ignore */}
        <Route path="category/*" element={<Category />} />
        <Route path="customers/*" element={<Customers />} />
        <Route path="orders/*" element={<Orders />} />
        <Route path="orderdetails/*" element={<Orderdetails />} />
        <Route path="products/*" element={<Products />} />
        {/* jhipster-needle-add-route-path - JHipster will add routes here */}
      </ErrorBoundaryRoutes>
    </div>
  );
};
