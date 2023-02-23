export interface ICustomers {
  id?: number;
  phoneNumber?: string | null;
  address?: string | null;
  email?: string | null;
  name?: string | null;
}

export const defaultValue: Readonly<ICustomers> = {};
