import dayjs from 'dayjs';
import { IVenta } from 'app/shared/model/venta.model';
import { IProducto } from 'app/shared/model/producto.model';

export interface IProductoVenta {
  id?: number;
  costoTotal?: number | null;
  costoTotalPuntos?: number | null;
  cantidad?: number | null;
  createdDate?: string | null;
  updatedDate?: string | null;
  venta?: IVenta;
  producto?: IProducto;
}

export const defaultValue: Readonly<IProductoVenta> = {};
