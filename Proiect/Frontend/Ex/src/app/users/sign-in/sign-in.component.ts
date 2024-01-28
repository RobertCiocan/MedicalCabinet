import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { UsersService } from '../users.service';
import { Router } from '@angular/router';
import { AuthenticationRequest } from '../models/Requests/authentication-request';
import { IdmUser } from '../models/idm_user.model copy';

@Component({
  selector: 'app-sing-in',
  templateUrl: './sign-in.component.html',
  styleUrls: ['./sign-in.component.scss']
})
export class SignInComponent {
  signInForm: FormGroup = this.formBuilder.group({
    username: ['', Validators.required],
    password: ['', Validators.required],
  });

  invalidCredentials: boolean = false;

  constructor(private usersService: UsersService, private router: Router, private formBuilder: FormBuilder) {}

  signIn() {
    if (this.signInForm.valid) {
      const authenticationRequest: AuthenticationRequest = {
        username: this.signInForm.value.username,
        password: this.signInForm.value.password
      };

      this.usersService.signInUser(authenticationRequest).subscribe({
        next: (response: any) => {
          console.log('User signed in:', response);
          const jwt = response.jwt;

          if (jwt) {
            const tokenParts = jwt.split('.');

            const payload = JSON.parse(atob(tokenParts[1]));
            sessionStorage.setItem('jwt', jwt);

            this.invalidCredentials = false;

            this.router.navigate(['/about']);
          } else {
            console.error('No JWT found in the response headers');
          }
        },
        error: (error) => {
          console.error('Sign in failed', error);
          this.invalidCredentials = true;
        }
      });
    }
  }
}
