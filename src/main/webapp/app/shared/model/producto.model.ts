import dayjs from 'dayjs';
import { IProductoVenta } from 'app/shared/model/producto-venta.model';
import { IProductoCaja } from 'app/shared/model/producto-caja.model';
import { IProductoDeposito } from 'app/shared/model/producto-deposito.model';
import { IProductoMesa } from 'app/shared/model/producto-mesa.model';
import { IClub } from 'app/shared/model/club.model';
import { ITipoProducto } from 'app/shared/model/tipo-producto.model';

export interface IProducto {
  id?: number;
  nombre?: string;
  precio?: number;
  costo?: number | null;
  precioPuntos?: number;
  puntosRecompensa?: number;
  descripcion?: string | null;
  estado?: boolean;
  createdDate?: string | null;
  updatedDate?: string | null;
  productoVentas?: IProductoVenta[] | null;
  productoCajas?: IProductoCaja[] | null;
  productoDepositos?: IProductoDeposito[] | null;
  productoMesas?: IProductoMesa[] | null;
  club?: IClub | null;
  tipoProducto?: ITipoProducto | null;
}

export const defaultValue: Readonly<IProducto> = {
  estado: false,
};
