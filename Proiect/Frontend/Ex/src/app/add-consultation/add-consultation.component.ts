import { Component, OnInit } from '@angular/core';
import { AppointmentService } from '../appointment.service';
import { ConsultationService } from '../consultation.service';
import { DoctorService } from '../doctor.service';
import { PatientService } from '../patient.service';
import { Consultation } from '../models/consultation.model';
import { ConsultationRequest } from '../models/Requests/consultation-request';
import { Patient } from '../models/patient.model';


@Component({
  selector: 'app-add-consultation',
  templateUrl: './add-consultation.component.html',
  styleUrls: ['./add-consultation.component.scss']
})
export class AddConsultationComponent implements OnInit {
  patients: Patient[] = [];
  selectedPatientId: string = '';
  selectedAppointmentId: string = '';
  newConsultationDate: string = '';

  constructor(
    private doctorService: DoctorService,
    private patientService: PatientService,
    private appointmentService: AppointmentService,
    private consultationService: ConsultationService
  ) { }

  ngOnInit(): void {
    this.loadPatients();
  }

  loadPatients() {
    this.doctorService.getAppointments().subscribe(
      (appointments) => {
        console.log('Appointments:', appointments);
        for (const appointment of appointments) {
          this.patientService.getPatient(appointment.id_pat).subscribe(
            (patient) => {
              console.log('Patient:', patient);
              this.patients.push(patient);
            },
            (error) => {
              console.error('Error fetching patient', error);
            }
          );
        }
      },
      (error) => {
        console.error('Error fetching patients', error);
      }
    );
  }

  createConsultation() {
    const jwt = sessionStorage.getItem('jwt');
    const tokenParts = jwt ? jwt.split('.') : [];
    const payload = JSON.parse(atob(tokenParts[1]));

    this.doctorService.getDoctorIdFromUserId(payload.jti).subscribe(
      (response) => {
        const newConsultation:ConsultationRequest= {
          id_patient: this.selectedPatientId,
          id_doctor: response.uid,
          date: new Date(this.newConsultationDate),
          diagnostic: "NOT_DIAGNOSED",
          investigation: []
        };
    
        this.consultationService.createConsultation(newConsultation).subscribe(
          (response) => {
            console.log('Consultation added:', response);
            this.loadPatients();
          },
          (error) => {
            console.error('Error adding consultation', error);
          }
        );
      },
      (error) => {
        console.error('Error fetching patient id', error);
      }
    );
  }
}
