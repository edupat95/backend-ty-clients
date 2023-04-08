import dayjs from 'dayjs';

export interface IPlan {
  id?: number;
  numeroPlan?: number | null;
  descripcion?: string | null;
  estado?: boolean;
  createdDate?: string | null;
  updatedDate?: string | null;
}

export const defaultValue: Readonly<IPlan> = {
  estado: false,
};
