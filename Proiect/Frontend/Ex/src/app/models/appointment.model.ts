import { Doctor } from "./SimpleModels/simple-doctor.model";
import { Patient } from "./SimpleModels/simple-patient.model";


export interface Appointment {
    id_appointment: number;
    date: Date;
    status: string;
    doctor: Doctor;
    patient: Patient;
}
