import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, switchMap } from 'rxjs';
import { Consultation } from './models/consultation.model';
import { PatientService } from './patient.service';
import { DoctorService } from './doctor.service';

@Injectable({
  providedIn: 'root'
})
export class ConsultationService {
  public apiUrl = 'http://localhost:8080/service2/api/medical_office/consultations';

  private readonly patientService: PatientService;
  private readonly doctorService: DoctorService;
  constructor(doctorService: DoctorService, patientService: PatientService, private http: HttpClient) {
    this.doctorService = doctorService;
    this.patientService = patientService;
  }

  public getConsultations(isDoctor: boolean): Observable<Consultation[]> {
    const jwt = sessionStorage.getItem('jwt');
    const tokenParts = jwt ? jwt.split('.') : [];
    const payload = JSON.parse(atob(tokenParts[1]));

    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${jwt}`
    });
  
    if(isDoctor){
      return this.doctorService.getDoctorIdFromUserId(payload.jti).pipe(
        switchMap(response => {
          return this.http.get<any>(`${this.apiUrl}?doctorId=${response.uid}`, { headers });
        })
      );
    }
    return this.patientService.getPatientIdFromUserId(payload.jti).pipe(
      switchMap(response => {
        console.log(response.uid);
        return this.http.get<any>(`${this.apiUrl}?patientId=${response.uid}`, { headers });
      })
    );
  }
 

  updateConsultationDiagnostic(consultationId: string, newDiagnostic: string): Observable<any> {
    const jwt = sessionStorage.getItem('jwt');

    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      Authorization: `Bearer ${jwt}`,
    });

    return this.http.put(`${this.apiUrl}/${consultationId}`, { diagnostic: newDiagnostic }, { headers });
  }
}
