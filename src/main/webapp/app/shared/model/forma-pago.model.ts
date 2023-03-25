import dayjs from 'dayjs';
import { IVenta } from 'app/shared/model/venta.model';
import { IClub } from 'app/shared/model/club.model';

export interface IFormaPago {
  id?: number;
  name?: string | null;
  estado?: boolean;
  createdDate?: string | null;
  updatedDate?: string | null;
  ventas?: IVenta[] | null;
  club?: IClub;
}

export const defaultValue: Readonly<IFormaPago> = {
  estado: false,
};
