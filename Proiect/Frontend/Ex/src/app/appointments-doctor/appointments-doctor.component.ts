import { Component, OnInit } from '@angular/core';
import { PatientService } from '../patient.service';
import { Router } from '@angular/router';
import { Appointment } from '../models/appointment.model';
import { DoctorService } from '../doctor.service';
import { Doctor } from '../models/doctor.model';
import { AppointmentService } from '../appointment.service';

@Component({
  selector: 'app-appointments-doctor',
  templateUrl: './appointments-doctor.component.html',
  styleUrls: ['./appointments-doctor.component.scss']
})
export class AppointmentsDoctorComponent implements OnInit {
  appointments: Appointment[] = [];
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
  }

  private loadAppointments() {
    this.doctorService.getAppointments().subscribe(
      (appointments) => {
        console.log('Appointments:', appointments);
        this.appointments = appointments;
        //this.appointments.forEach(appointment => appointment.status = stat);
      },
      (error) => {
        console.error('Error fetching appointments', error);
      }
    );
  }

  updateStatus(appointmentId: number) {
    const updatedAppointment = this.appointments.find(appointment => appointment.id_appointment === appointmentId);
    if (updatedAppointment) {
      this.appointmentService.updateStatus(appointmentId, updatedAppointment.status).subscribe(
        (response) => {
          console.log('Status updated successfully');
        },
        (error) => {
          console.error('Error updating status', error);
        }
      );
    }
  }
}
