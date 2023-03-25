import dayjs from 'dayjs';
import { IUser } from 'app/shared/model/user.model';
import { IEvento } from 'app/shared/model/evento.model';
import { ITrabajador } from 'app/shared/model/trabajador.model';

export interface IAdminClub {
  id?: number;
  estado?: boolean;
  createdDate?: string | null;
  updatedDate?: string | null;
  user?: IUser;
  eventos?: IEvento[] | null;
  trabajadors?: ITrabajador[] | null;
}

export const defaultValue: Readonly<IAdminClub> = {
  estado: false,
};
