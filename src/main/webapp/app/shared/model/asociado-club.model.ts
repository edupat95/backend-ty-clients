import dayjs from 'dayjs';
import { IAsociado } from 'app/shared/model/asociado.model';
import { IClub } from 'app/shared/model/club.model';
import { IRegistrador } from 'app/shared/model/registrador.model';

export interface IAsociadoClub {
  id?: number;
  identificador?: string;
  fechaAsociacion?: string | null;
  puntosClub?: number | null;
  estado?: boolean | null;
  createdDate?: string | null;
  updatedDate?: string | null;
  asociado?: IAsociado;
  club?: IClub;
  registrador?: IRegistrador | null;
}

export const defaultValue: Readonly<IAsociadoClub> = {
  estado: false,
};
