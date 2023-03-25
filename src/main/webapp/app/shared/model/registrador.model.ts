import dayjs from 'dayjs';
import { ITrabajador } from 'app/shared/model/trabajador.model';
import { IAsociadoClub } from 'app/shared/model/asociado-club.model';

export interface IRegistrador {
  id?: number;
  estado?: boolean;
  createdDate?: string | null;
  updatedDate?: string | null;
  trabajador?: ITrabajador;
  asociadoClubs?: IAsociadoClub[] | null;
}

export const defaultValue: Readonly<IRegistrador> = {
  estado: false,
};
