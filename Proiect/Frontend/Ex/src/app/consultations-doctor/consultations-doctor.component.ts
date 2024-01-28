import { Component, OnInit } from '@angular/core';
import { ConsultationService } from '../consultation.service';
import { Consultation } from '../models/consultation.model';
import { Diagnostic } from '../models/Enums/diagnostic-enum.model';
import { Investigation } from '../models/investigation.model';

@Component({
  selector: 'app-consultations-doctor',
  templateUrl: './consultations-doctor.component.html',
  styleUrls: ['./consultations-doctor.component.scss']
})
export class ConsultationsDoctorComponent {
  consultations: Consultation[] = [];
  diagnostics: string[] = ['HEALTHY', 'SICK'];
  selectedConsultationId: string | undefined;
  newInvestigationName: string | undefined;
  newInvestigationDuration: number | undefined;

  constructor(private consultationService: ConsultationService) { }

  ngOnInit(): void {
    this.loadConsultations();
  }

  private loadConsultations() {
    this.consultationService.getConsultations(true).subscribe(
      (consultations) => {
        console.log('Doctor Consultations:', consultations);
        this.consultations = consultations;
        console.log(this.consultations[0].investigation);
      },
      (error) => {
        console.error('Error fetching doctor consultations', error);
      }
    );
  }

  updateConsultationDiagnostic(consultationId: string, newDiagnostic: string): void {
    this.consultationService.updateConsultationDiagnostic(consultationId, newDiagnostic).subscribe(
      (response) => {
        console.log('Diagnostic updated successfully:', response);
        this.loadConsultations();
      },
      (error) => {
        console.error('Error updating diagnostic', error);
      }
    );
  }

  addNewInvestigation() {
    if (!this.selectedConsultationId || !this.newInvestigationName || !this.newInvestigationDuration) {
      console.error('Please fill in all fields.');
      return;
    }

    const consultationId = this.selectedConsultationId;
    const newInvestigation: Investigation = {
      name: this.newInvestigationName,
      duration: this.newInvestigationDuration,
      result: 'Pending'
    };

    this.consultationService.addNewInvestigation(consultationId, newInvestigation).subscribe(
      (response) => {
        console.log('Investigation added successfully:', response);
        this.loadConsultations();
      },
      (error) => {
        console.error('Error adding investigation', error);
      }
    );
  }
}
