export interface IOrderdetails {
  id?: number;
  color?: string | null;
  price?: number | null;
  quantity?: number | null;
  sizes?: string | null;
  unitPrice?: number | null;
  orderId?: number | null;
  productId?: number | null;
}

export const defaultValue: Readonly<IOrderdetails> = {};
