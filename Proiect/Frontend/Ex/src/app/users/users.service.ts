import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse, HttpHeaders } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { map, catchError } from 'rxjs/operators';

import { AuthenticationRequest } from './models/Requests/authentication-request';
import { RegistrationRequest } from './models/Requests/registration-request';
import { RegistrationResponse } from './models/Responses/registration-response';

@Injectable({
  providedIn: 'root',
})
export class UsersService {
  // private apiUrl = 'http://localhost:8080/idm-service/api/v1';
  public apiUrl = 'http://localhost:8080/service3/api';

  constructor(private http: HttpClient) {}

  registerUser(registrationRequest: RegistrationRequest): Observable<RegistrationResponse> {
    console.log(registrationRequest);
    return this.http.post<RegistrationResponse>(`${this.apiUrl}/register`, registrationRequest);
  }

  signInUser(authenticationRequest: AuthenticationRequest): Observable<string> {
    return this.http.post<string>(`${this.apiUrl}/auth`, authenticationRequest);
  }

  logoutUser(jwt: string | null): Observable<string> {
    const headers = jwt
      ? new HttpHeaders({ Authorization: `Bearer ${jwt}` })
      : new HttpHeaders();
      
    return this.http.post<string>(`${this.apiUrl}/logout`, null, { headers, observe: 'response' }).pipe(
      catchError((error) => {
        console.error('Error logging out', error);
        return throwError(error);
      }),
      map((response: HttpResponse<string>) => {
        if (response.status === 200) {
          sessionStorage.removeItem('jwt');
        }
        return response.body || '';
      })
    );
  }
}
