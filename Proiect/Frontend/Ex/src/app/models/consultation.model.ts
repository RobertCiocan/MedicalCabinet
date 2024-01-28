import { Diagnostic } from "./Enums/diagnostic-enum.model";
import { Investigation } from "./investigation.model";

export interface Consultation {
    id: string;
    id_patient: string;
    id_doctor: string;
    date: Date;
    diagnostic: string;
    investigation: Investigation[];
}
