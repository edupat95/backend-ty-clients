import { IProducto } from 'app/shared/model/producto.model';
import { IClub } from 'app/shared/model/club.model';

export interface ITipoProducto {
  id?: number;
  estado?: boolean;
  name?: string | null;
  productos?: IProducto[] | null;
  club?: IClub;
}

export const defaultValue: Readonly<ITipoProducto> = {
  estado: false,
};
