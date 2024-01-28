import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

import { Router } from '@angular/router';
import { UsersService } from '../users/users.service';
import { RegistrationRequest } from '../users/models/Requests/registration-request';
import { DoctorService } from '../doctor.service';
import { DoctorRequest } from '../models/Requests/create-doctor-request';

@Component({
  selector: 'app-register',
  templateUrl: './admin-page.component.html',
  styleUrls: ['./admin-page.component.scss']
})
export class AdminPageComponent{
  registerForm: FormGroup = this.formBuilder.group({
    firstName: ['', Validators.required],
    lastName: ['', Validators.required],
    phoneNumber: ['', Validators.required],
    specialization: ['', Validators.required],
    username: ['', Validators.required],
    email: ['', [Validators.required, Validators.email]],
    password: ['', [Validators.required, Validators.minLength(6)]],
  });
  notificationMessage: string = '';
  

  private readonly usersService: UsersService;
  private readonly doctorService: DoctorService;
  private readonly router: Router;
  constructor(usersService: UsersService, doctorService: DoctorService, router: Router, private formBuilder: FormBuilder) {
    this.usersService = usersService;
    this.doctorService = doctorService;
    this.router = router;
  }

  addDoctor() {
    if (this.registerForm.valid) {
      const registrationRequest: RegistrationRequest = {
        username: this.registerForm.value.username,
        password: this.registerForm.value.password,
        role: 'doctor',
      };
      
      this.usersService.registerUser(registrationRequest).subscribe(
        (response) => {
          const doctor_request:DoctorRequest = {
            id_user: response.uid,
            specialization: this.registerForm.value.specialization,
            last_name: this.registerForm.value.lastName,
            first_name: this.registerForm.value.firstName,
            email: this.registerForm.value.email,
            phone_nr: this.registerForm.value.phoneNumber
          };
          if (response.uid === "0") {
            console.error('Registration failed');
            this.notificationMessage = 'Add doctor failed - try again';
            return;
          }
          this.doctorService.createDoctor(doctor_request).subscribe(
            (response) => {
              console.log('Doctor created');
              this.notificationMessage = 'Doctor creat cu succes!';
            },
            (error) => {
              console.error('Registration failed', error);
              this.notificationMessage = 'Add doctort failed - try again';
            }
          );
        },
        (error) => {
          console.error('Registration failed', error);
          this.notificationMessage = 'Add doctort failed - try again';
        }
      );
    }
  }
}
