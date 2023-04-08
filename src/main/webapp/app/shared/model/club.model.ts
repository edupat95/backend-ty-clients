import dayjs from 'dayjs';
import { IAdminClub } from 'app/shared/model/admin-club.model';
import { IPlanContratado } from 'app/shared/model/plan-contratado.model';
import { ITipoProducto } from 'app/shared/model/tipo-producto.model';
import { IFormaPago } from 'app/shared/model/forma-pago.model';
import { ICaja } from 'app/shared/model/caja.model';
import { IDeposito } from 'app/shared/model/deposito.model';
import { ITrabajador } from 'app/shared/model/trabajador.model';
import { IAsociadoClub } from 'app/shared/model/asociado-club.model';
import { IEvento } from 'app/shared/model/evento.model';
import { IProducto } from 'app/shared/model/producto.model';
import { IMesa } from 'app/shared/model/mesa.model';

export interface IClub {
  id?: number;
  nombre?: string;
  direccion?: string | null;
  estado?: boolean;
  createdDate?: string | null;
  updatedDate?: string | null;
  adminClub?: IAdminClub;
  planContratado?: IPlanContratado;
  tipoProductos?: ITipoProducto[] | null;
  formaPagos?: IFormaPago[] | null;
  cajas?: ICaja[] | null;
  depositos?: IDeposito[] | null;
  trabajadors?: ITrabajador[] | null;
  asociadoClubs?: IAsociadoClub[] | null;
  eventos?: IEvento[] | null;
  productos?: IProducto[] | null;
  mesas?: IMesa[] | null;
}

export const defaultValue: Readonly<IClub> = {
  estado: false,
};
