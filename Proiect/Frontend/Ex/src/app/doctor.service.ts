import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Doctor } from './models/doctor.model';
import { Observable, switchMap } from 'rxjs';
import { DoctorRequest } from './models/Requests/create-doctor-request';
import { Appointment } from './models/appointment.model';
import { GetIdResponse } from './models/Responses/getId-response';

@Injectable({
  providedIn: 'root'
})
export class DoctorService {
  public apiUrl = 'http://localhost:8080/service1/api/medical_office/doctors';

  constructor(private http: HttpClient) {}

  public getDoctors(): Observable<Doctor[]> {
    const jwt = sessionStorage.getItem('jwt');

    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${jwt}`
    });

    return this.http.get<Doctor[]>(`${this.apiUrl}`, { headers });
  }

  createDoctor(doctor_request: DoctorRequest) {
    const jwt = sessionStorage.getItem('jwt');

    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${jwt}`
    });

    return this.http.post(`${this.apiUrl}`, doctor_request, { headers });
  }

  public getAppointments(): Observable<Appointment[]> {
    const jwt = sessionStorage.getItem('jwt');
    const tokenParts = jwt ? jwt.split('.') : [];
    const payload = JSON.parse(atob(tokenParts[1]));

    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${jwt}`
    });

    console.log('ID:', payload.jti);

    return this.getDoctorIdFromUserId(payload.jti).pipe(
      switchMap(response => {
        console.log(response.uid);
        return this.http.get<Appointment[]>(`${this.apiUrl}/${response.uid}/patients`, { headers });
      })
    );
  }

  public getDoctorIdFromUserId(userId: String): Observable<GetIdResponse> {
    const jwt = sessionStorage.getItem('jwt');

    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${jwt}`
    });

    return this.http.get<any>(`${this.apiUrl}/doctorId?userId=${userId}`, { headers });
  }

}
