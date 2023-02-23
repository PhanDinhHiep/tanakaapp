import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Orderdetails from './orderdetails';
import OrderdetailsDetail from './orderdetails-detail';
import OrderdetailsUpdate from './orderdetails-update';
import OrderdetailsDeleteDialog from './orderdetails-delete-dialog';

const OrderdetailsRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Orderdetails />} />
    <Route path="new" element={<OrderdetailsUpdate />} />
    <Route path=":id">
      <Route index element={<OrderdetailsDetail />} />
      <Route path="edit" element={<OrderdetailsUpdate />} />
      <Route path="delete" element={<OrderdetailsDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default OrderdetailsRoutes;
