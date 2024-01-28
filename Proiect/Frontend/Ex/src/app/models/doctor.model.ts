import { Appointment } from "./appointment.model";

export interface Doctor {
    id_doctor: string;
    id_user: string;
    last_name: string;
    first_name: string;
    email: string;
    phone_nr: string;
    specialization: string;
    appointments: Appointment[];
}
