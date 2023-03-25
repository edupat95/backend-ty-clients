import dayjs from 'dayjs';
import { IProducto } from 'app/shared/model/producto.model';
import { ICaja } from 'app/shared/model/caja.model';

export interface IProductoCaja {
  id?: number;
  cantidad?: number | null;
  createdDate?: string | null;
  updatedDate?: string | null;
  producto?: IProducto;
  caja?: ICaja;
}

export const defaultValue: Readonly<IProductoCaja> = {};
