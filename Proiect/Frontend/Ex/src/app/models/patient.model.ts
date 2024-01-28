import { Appointment } from "./appointment.model";

export interface Patient {
    id_patient: string;
    id_user: string;
    cnp: string;
    last_name: string;
    first_name: string;
    email: string;
    phone_nr: string;
    birth_date: Date;
    appointments: Appointment[];
    _active: boolean;
}
