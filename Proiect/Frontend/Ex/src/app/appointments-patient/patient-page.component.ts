import { Component, OnInit } from '@angular/core';
import { PatientService } from '../patient.service';
import { Router } from '@angular/router';
import { Appointment } from '../models/appointment.model';
import { DoctorService } from '../doctor.service';
import { Doctor } from '../models/doctor.model';
import { AppointmentService } from '../appointment.service';
import { map, switchMap } from 'rxjs/operators';


@Component({
  selector: 'app-patient-page',
  templateUrl: './patient-page.component.html',
  styleUrls: ['./patient-page.component.scss']
})
export class PatientPageComponent implements OnInit {
  appointments: Appointment[] = [];
  doctors: Doctor[] = [];
  newAppointmentDate: string = '';
  selectedDoctorId: string = '';

  constructor(
    private patientService: PatientService, 
    private doctorService: DoctorService, 
    private appointmentService: AppointmentService, 
    private router: Router
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
          const newAppointment: Appointment = {
            id_appointment: 0,
            date: new Date(this.newAppointmentDate),
            status: 'scheduled',
            doctor: {
              id_doctor: this.selectedDoctorId
            },
            patient: {
              id_patient: response.uid
            }
          };

          console.log('New appointment:', newAppointment);
          
          this.appointmentService.createAppointment(newAppointment).subscribe(
            (createdAppointment) => {
              console.log('Appointment created:', createdAppointment);
              this.loadAppointments();
            },
            (error) => {
              console.error('Error creating appointment', error);
            }
          );
        } else {
          console.warn('Please select a doctor and provide a date for the new appointment.');
        }
      },
      error => {
        console.error('Error creating appointment', error);
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

  selectDoctor(doctorId: string) {
    this.selectedDoctorId = doctorId;
  }
}
