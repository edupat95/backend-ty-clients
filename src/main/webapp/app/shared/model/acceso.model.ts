import dayjs from 'dayjs';
import { IAsociado } from 'app/shared/model/asociado.model';
import { IEvento } from 'app/shared/model/evento.model';

export interface IAcceso {
  id?: number;
  costoPuntos?: number | null;
  fechaCanje?: string | null;
  estado?: boolean;
  createdDate?: string | null;
  updatedDate?: string | null;
  asociado?: IAsociado;
  evento?: IEvento;
}

export const defaultValue: Readonly<IAcceso> = {
  estado: false,
};
