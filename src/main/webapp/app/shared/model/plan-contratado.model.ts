import dayjs from 'dayjs';
import { IPlan } from 'app/shared/model/plan.model';

export interface IPlanContratado {
  id?: number;
  tiempoContratado?: number | null;
  fechaVencimiento?: string | null;
  estado?: boolean;
  createdDate?: string | null;
  updatedDate?: string | null;
  plan?: IPlan;
}

export const defaultValue: Readonly<IPlanContratado> = {
  estado: false,
};
