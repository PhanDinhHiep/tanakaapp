export interface IProducts {
  id?: number;
  description?: string | null;
  name?: string | null;
  price?: number | null;
  status?: string | null;
  categoryId?: number | null;
}

export const defaultValue: Readonly<IProducts> = {};
