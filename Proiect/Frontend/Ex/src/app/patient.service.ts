import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { PatientRequest } from './models/Requests/create-patient-request';

import { Observable } from 'rxjs';
import { map, switchMap } from 'rxjs/operators';
import { GetIdResponse } from './models/Responses/getId-response';
import { Appointment } from './models/appointment.model';

@Injectable({
  providedIn: 'root'
})
export class PatientService {
  public apiUrl = 'http://localhost:8080/service1/api/medical_office/patients';

  constructor(private http: HttpClient) {}

  createPatient(patientRequest: PatientRequest): Observable<PatientRequest> {
    console.log(patientRequest);
    return this.http.post<PatientRequest>(`${this.apiUrl}`, patientRequest);
  }

  public getAppointments(): Observable<Appointment[]> {
    const jwt = sessionStorage.getItem('jwt');
    const tokenParts = jwt ? jwt.split('.') : [];
    const payload = JSON.parse(atob(tokenParts[1]));

    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${jwt}`
    });

    console.log('Patient ID:', payload.jti);

    return this.getPatientIdFromUserId(payload.jti).pipe(
      switchMap(response => {
        console.log(response.uid);
        return this.http.get<Appointment[]>(`${this.apiUrl}/${response.uid}/doctors`, { headers });
      })
    );
  }

  public getPatientIdFromUserId(userId: String): Observable<GetIdResponse> {
    const jwt = sessionStorage.getItem('jwt');

    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${jwt}`
    });

    return this.http.get<any>(`${this.apiUrl}/patientId?userId=${userId}`, { headers });
  }
}
