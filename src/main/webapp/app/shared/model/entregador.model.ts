import dayjs from 'dayjs';
import { ITrabajador } from 'app/shared/model/trabajador.model';
import { IVenta } from 'app/shared/model/venta.model';

export interface IEntregador {
  id?: number;
  estado?: boolean;
  createdDate?: string | null;
  updatedDate?: string | null;
  trabajador?: ITrabajador;
  ventas?: IVenta[] | null;
}

export const defaultValue: Readonly<IEntregador> = {
  estado: false,
};
