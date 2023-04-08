import dayjs from 'dayjs';
import { ITrabajador } from 'app/shared/model/trabajador.model';
import { IVenta } from 'app/shared/model/venta.model';
import { ICaja } from 'app/shared/model/caja.model';

export interface ICajero {
  id?: number;
  plataDeCambio?: number | null;
  tipo?: number | null;
  estado?: boolean;
  creadDate?: string | null;
  updatedDate?: string | null;
  trabajador?: ITrabajador;
  ventas?: IVenta[] | null;
  caja?: ICaja | null;
}

export const defaultValue: Readonly<ICajero> = {
  estado: false,
};
