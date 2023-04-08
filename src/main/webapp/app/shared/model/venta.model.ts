import dayjs from 'dayjs';
import { IProductoVenta } from 'app/shared/model/producto-venta.model';
import { ICajero } from 'app/shared/model/cajero.model';
import { IAsociado } from 'app/shared/model/asociado.model';
import { IFormaPago } from 'app/shared/model/forma-pago.model';
import { IEntregador } from 'app/shared/model/entregador.model';

export interface IVenta {
  id?: number;
  costoTotal?: number;
  costoTotalPuntos?: number;
  identificadorTicket?: string;
  entregado?: boolean;
  createdDate?: string | null;
  updatedDate?: string | null;
  productoVentas?: IProductoVenta[] | null;
  cajero?: ICajero;
  asociado?: IAsociado | null;
  formaPago?: IFormaPago | null;
  entregador?: IEntregador | null;
}

export const defaultValue: Readonly<IVenta> = {
  entregado: false,
};
