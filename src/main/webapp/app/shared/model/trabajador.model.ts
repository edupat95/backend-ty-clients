import dayjs from 'dayjs';
import { IUser } from 'app/shared/model/user.model';
import { IClub } from 'app/shared/model/club.model';
import { IAdminClub } from 'app/shared/model/admin-club.model';

export interface ITrabajador {
  id?: number;
  sueldo?: number | null;
  reputacion?: number | null;
  descripcion?: string | null;
  fechaIngreso?: string;
  estado?: boolean;
  createdDate?: string | null;
  updatedDate?: string | null;
  user?: IUser;
  club?: IClub;
  adminClub?: IAdminClub;
}

export const defaultValue: Readonly<ITrabajador> = {
  estado: false,
};
