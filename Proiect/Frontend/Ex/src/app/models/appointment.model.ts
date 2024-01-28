import { Doctor } from "./SimpleModels/simple-doctor.model";
import { Patient } from "./SimpleModels/simple-patient.model";


export interface Appointment {
    id_appointment: number;
    id_doc: number;
    id_pat: string;
    date: string;
    status: string;
    doctor: Doctor;
    patient: Patient;
}
