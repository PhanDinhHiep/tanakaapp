export interface ICategory {
  id?: number;
  description?: string | null;
  name?: string | null;
}

export const defaultValue: Readonly<ICategory> = {};
