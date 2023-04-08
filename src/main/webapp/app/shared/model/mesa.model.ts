import dayjs from 'dayjs';
import { IProductoMesa } from 'app/shared/model/producto-mesa.model';
import { IClub } from 'app/shared/model/club.model';

export interface IMesa {
  id?: number;
  numeroMesa?: number | null;
  estado?: boolean;
  createdDate?: string | null;
  updatedDate?: string | null;
  productoMesas?: IProductoMesa[] | null;
  club?: IClub | null;
}

export const defaultValue: Readonly<IMesa> = {
  estado: false,
};
