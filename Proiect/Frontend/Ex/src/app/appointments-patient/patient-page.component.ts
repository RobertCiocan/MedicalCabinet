import { Component, OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { PatientService } from '../patient.service';
import { Router } from '@angular/router';
import { Appointment } from '../models/appointment.model';
import { DoctorService } from '../doctor.service';
import { Doctor } from '../models/doctor.model';
import { AppointmentService } from '../appointment.service';
import { MatSnackBar } from '@angular/material/snack-bar';


@Component({
  selector: 'app-patient-page',
  templateUrl: './patient-page.component.html',
  styleUrls: ['./patient-page.component.scss']
})
export class PatientPageComponent implements OnInit {
  appointments: Appointment[] = [];
  doctors: Doctor[] = [];
  newAppointmentDate: string = '';
  selectedDoctorId: number = 0;

  constructor(
    private patientService: PatientService, 
    private doctorService: DoctorService, 
    private appointmentService: AppointmentService, 
    private router: Router,
    private datePipe: DatePipe,
    private snackBar: MatSnackBar
    ) {}

  ngOnInit(): void {
    this.loadAppointments();
    this.loadDoctors();
  }

  private loadAppointments() {
    this.patientService.getAppointments().subscribe(
      (appointments) => {
        console.log('Appointments:', appointments);
        this.appointments = appointments;
      },
      (error) => {
        console.error('Error fetching appointments', error);
      }
    );
  }

  private loadDoctors() {
    this.doctorService.getDoctors().subscribe(
      (doctors) => {
        console.log('Doctors:', doctors);
        this.doctors = doctors;
      },
      (error) => {
        console.error('Error fetching doctors', error);
      }
    );
  }

  addNewAppointment() {
    const jwt = sessionStorage.getItem('jwt');
    const tokenParts = jwt ? jwt.split('.') : [];
    const payload = JSON.parse(atob(tokenParts[1]));

    console.log('Patient ID:', payload.jti);

    this.patientService.getPatientIdFromUserId(payload.jti).subscribe(
      response => {
        if (this.selectedDoctorId && this.newAppointmentDate) {
          const formattedDate = this.datePipe.transform(this.newAppointmentDate, 'dd:MM:yyyy HH:mm') || '';
          const newAppointment: Appointment = {
            id_appointment: 0,
            date: formattedDate,
            status: 'scheduled',
            doctor: {
              id_doctor: ""
            },
            id_doc : this.selectedDoctorId,
            patient: {
              id_patient: response.uid
            },
            id_pat: response.uid
          };

          console.log('New appointment:', newAppointment);
          
          this.appointmentService.createAppointment(newAppointment).subscribe(
            (createdAppointment) => {
              console.log('Appointment created:', createdAppointment);
              this.loadAppointments();
            },
            (error) => {
              console.error(error?.error?.message);
              this.showErrorSnackBar(error?.error?.message || 'An unexpected error occurred');
            }
          );
        } else {
          console.warn('Please select a doctor and provide a date for the new appointment.');
        }
      },
      error => {
        console.error(error?.error?.message);
        this.showErrorSnackBar(error?.error?.message || 'An unexpected error occurred');
      }
    );
  }

  deleteAppointment(appointmentId: number) {
    this.appointmentService.deleteAppointment(appointmentId).subscribe(
      (response) => {
        console.log('Deleted appointment:', response);
        this.loadAppointments();
      },
      (error) => {
        console.error('Error fetching doctors', error);
      }
    );
  }

  selectDoctor(doctorId: number) {
    this.selectedDoctorId = doctorId;
  }

  private showErrorSnackBar(message: string) {
    this.snackBar.open(message, 'Close', {
        duration: 5000,
        panelClass: ['error-snackbar']
    });
  }
}
