import dayjs from 'dayjs';
import { IMesa } from 'app/shared/model/mesa.model';
import { IProducto } from 'app/shared/model/producto.model';

export interface IProductoMesa {
  id?: number;
  costoTotal?: number | null;
  costoTotalPuntos?: number | null;
  cantidad?: number | null;
  estado?: boolean;
  createdDate?: string | null;
  updatedDate?: string | null;
  mesa?: IMesa | null;
  producto?: IProducto;
}

export const defaultValue: Readonly<IProductoMesa> = {
  estado: false,
};
