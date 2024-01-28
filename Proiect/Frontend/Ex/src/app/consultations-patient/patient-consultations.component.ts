import { Component, OnInit  } from '@angular/core';
import { ConsultationService } from '../consultation.service';
import { Consultation } from '../models/consultation.model';

@Component({
  selector: 'app-patient-consultations',
  templateUrl: './patient-consultations.component.html',
  styleUrls: ['./patient-consultations.component.scss']
})
export class PatientConsultationsComponent implements OnInit {
  consultations: Consultation[] = [];

  constructor(private consultationService: ConsultationService) { }

  ngOnInit(): void {
    this.loadConsultations();
  }

  private loadConsultations() {
    this.consultationService.getConsultations(false).subscribe(
      (consultations) => {
        console.log('Consultations:', consultations);
        this.consultations = consultations;
      },
      (error) => {
        console.error('Error fetching consultations', error);
      }
    );
  }
}