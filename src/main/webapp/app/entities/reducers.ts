import category from 'app/entities/category/category.reducer';
import customers from 'app/entities/customers/customers.reducer';
import orders from 'app/entities/orders/orders.reducer';
import orderdetails from 'app/entities/orderdetails/orderdetails.reducer';
import products from 'app/entities/products/products.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

const entitiesReducers = {
  category,
  customers,
  orders,
  orderdetails,
  products,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
};

export default entitiesReducers;
