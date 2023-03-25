import dayjs from 'dayjs';
import { IProductoDeposito } from 'app/shared/model/producto-deposito.model';
import { IClub } from 'app/shared/model/club.model';

export interface IDeposito {
  id?: number;
  name?: string | null;
  estado?: boolean | null;
  createdDate?: string | null;
  updatedDate?: string | null;
  productoDepositos?: IProductoDeposito[] | null;
  club?: IClub;
}

export const defaultValue: Readonly<IDeposito> = {
  estado: false,
};
