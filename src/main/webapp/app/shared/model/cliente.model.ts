import dayjs from 'dayjs';
import { IUser } from 'app/shared/model/user.model';
import { IDocumento } from 'app/shared/model/documento.model';

export interface ICliente {
  id?: number;
  mail?: string;
  phone?: string;
  createdDate?: string | null;
  updatedDate?: string | null;
  user?: IUser;
  documento?: IDocumento | null;
}

export const defaultValue: Readonly<ICliente> = {};
