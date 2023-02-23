import dayjs from 'dayjs';

export interface IOrders {
  id?: number;
  deliveredDate?: string | null;
  note?: string | null;
  orderDate?: string | null;
  orderStatus?: string | null;
  totalPrice?: number | null;
  customerId?: number | null;
}

export const defaultValue: Readonly<IOrders> = {};
