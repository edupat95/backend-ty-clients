import dayjs from 'dayjs';
import { IUser } from 'app/shared/model/user.model';

export interface IDocumento {
  id?: number;
  numeroTramite?: number | null;
  apellidos?: string | null;
  nombres?: string | null;
  sexo?: string | null;
  numeroDni?: number | null;
  ejemplar?: string | null;
  nacimiento?: string | null;
  fechaEmision?: string | null;
  inicioFinCuil?: number | null;
  createdDate?: string | null;
  updatedDate?: string | null;
  user?: IUser | null;
}

export const defaultValue: Readonly<IDocumento> = {};
