import dayjs from 'dayjs';
import { IDeposito } from 'app/shared/model/deposito.model';
import { IProducto } from 'app/shared/model/producto.model';

export interface IProductoDeposito {
  id?: number;
  cantidad?: number | null;
  createdDate?: string | null;
  updatedDate?: string | null;
  deposito?: IDeposito;
  producto?: IProducto;
}

export const defaultValue: Readonly<IProductoDeposito> = {};
