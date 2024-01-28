import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Doctor } from './models/doctor.model';
import { Observable } from 'rxjs';
import { Appointment } from './models/appointment.model';

@Injectable({
  providedIn: 'root'
})
export class AppointmentService {
  public apiUrl = 'http://localhost:8080/service1/api/medical_office/appointments';

  constructor(private http: HttpClient) {}

  public getAppointments(): Observable<Appointment[]> {
    const jwt = sessionStorage.getItem('jwt');

    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${jwt}`
    });

    return this.http.get<Appointment[]>(`${this.apiUrl}`, { headers });
  }

  public createAppointment(appointment: Appointment): Observable<Appointment> {
    console.log(appointment);

    const jwt = sessionStorage.getItem('jwt');
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      Authorization: `Bearer ${jwt}`,
    });

    return this.http.post<Appointment>(`${this.apiUrl}`, appointment, { headers });
  }

  public deleteAppointment(appointmentId: number): Observable<string> {
    console.log("Deleting:" + appointmentId);

    const jwt = sessionStorage.getItem('jwt');
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      Authorization: `Bearer ${jwt}`,
    });

    return this.http.delete<string>(`${this.apiUrl}/${appointmentId}`, { headers });
  }

  updateStatus(appointmentId: number, newStatus: string): Observable<any> {
    const jwt = sessionStorage.getItem('jwt');

    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      Authorization: `Bearer ${jwt}`,
    });

    return this.http.put(`${this.apiUrl}/${appointmentId}`, { status: newStatus }, { headers });
  }
}
