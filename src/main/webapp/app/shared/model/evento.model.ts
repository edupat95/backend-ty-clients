import dayjs from 'dayjs';
import { IAcceso } from 'app/shared/model/acceso.model';
import { IAdminClub } from 'app/shared/model/admin-club.model';
import { IClub } from 'app/shared/model/club.model';

export interface IEvento {
  id?: number;
  fechaEvento?: string | null;
  fechaCreacion?: string | null;
  estado?: boolean;
  direccion?: string | null;
  createdDate?: string | null;
  updatedDate?: string | null;
  accesos?: IAcceso[] | null;
  adminClub?: IAdminClub;
  club?: IClub;
}

export const defaultValue: Readonly<IEvento> = {
  estado: false,
};
