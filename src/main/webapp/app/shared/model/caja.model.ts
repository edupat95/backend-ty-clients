import dayjs from 'dayjs';
import { ICajero } from 'app/shared/model/cajero.model';
import { IProductoCaja } from 'app/shared/model/producto-caja.model';
import { IClub } from 'app/shared/model/club.model';

export interface ICaja {
  id?: number;
  nombre?: string | null;
  estado?: boolean;
  createdDate?: string | null;
  updatedDate?: string | null;
  cajeros?: ICajero[] | null;
  productoCajas?: IProductoCaja[] | null;
  club?: IClub;
}

export const defaultValue: Readonly<ICaja> = {
  estado: false,
};
