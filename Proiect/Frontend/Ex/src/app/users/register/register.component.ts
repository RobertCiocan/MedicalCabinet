import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

import { UsersService } from '../users.service';
import { Router } from '@angular/router';
import { PatientRequest } from '../../models/Requests/create-patient-request';
import { RegistrationRequest } from '../models/Requests/registration-request';
import { PatientService } from 'src/app/patient.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent{
  registerForm: FormGroup = this.formBuilder.group({
    firstName: ['', Validators.required],
    lastName: ['', Validators.required],
    phoneNumber: ['', Validators.required],
    cnp: ['', Validators.compose([Validators.required, Validators.minLength(13), Validators.maxLength(13)])],
    username: ['', Validators.required],
    email: ['', [Validators.required, Validators.email]],
    password: ['', [Validators.required, Validators.minLength(6)]],
  });
  notificationMessage: string = '';
  

  private readonly usersService: UsersService;
  private readonly patientService: PatientService;
  private readonly router: Router;
  constructor(usersService: UsersService, patientService: PatientService, router: Router, private formBuilder: FormBuilder) {
    this.usersService = usersService;
    this.patientService = patientService;
    this.router = router;
  }

  register() {
    if (this.registerForm.valid) {
      const registrationRequest: RegistrationRequest = {
        username: this.registerForm.value.username,
        password: this.registerForm.value.password,
        role: 'user',
      };
      
      this.usersService.registerUser(registrationRequest).subscribe(
        (response) => {
          console.log(response.uid);
          const client_request:PatientRequest = {
            id_user: response.uid,
            cnp: this.registerForm.value.cnp,
            last_name: this.registerForm.value.lastName,
            first_name: this.registerForm.value.firstName,
            email: this.registerForm.value.email,
            phone_number: this.registerForm.value.phoneNumber
          };
          if(client_request.id_user == "0"){
            this.notificationMessage = 'User already exists';
            return;
          }
          this.patientService.createPatient(client_request).subscribe(
            (response) => {
              console.log('Client created');
              this.notificationMessage = 'Patient created';
            },
            (error) => {
              console.error('Registration failed', error);
              this.notificationMessage = 'Registration failed';
            }
          );
        },
        (error) => {
          console.error('Registration failed', error);
          this.notificationMessage = 'Registration failed - CNP already exists';
        }
      );
    }
  }
}
