import { Investigation } from "../investigation.model";

export interface ConsultationRequest {
    id_patient: string;
    id_doctor: string;
    date: Date;
    diagnostic: string;
    investigation: Investigation[];
}