import dayjs from 'dayjs';
import { IDocumento } from 'app/shared/model/documento.model';
import { IVenta } from 'app/shared/model/venta.model';
import { IAsociadoClub } from 'app/shared/model/asociado-club.model';

export interface IAsociado {
  id?: number;
  identificadorGeneral?: string | null;
  estado?: boolean | null;
  createdDate?: string | null;
  updatedDate?: string | null;
  documento?: IDocumento;
  ventas?: IVenta[] | null;
  asociadoClubs?: IAsociadoClub[] | null;
}

export const defaultValue: Readonly<IAsociado> = {
  estado: false,
};
