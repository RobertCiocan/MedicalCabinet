import { Component } from '@angular/core';
import { Router } from '@angular/router';

//import { UsersService } from '../users.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent {
  isAdmin(): boolean { return true}
  isUserLoggedIn(): boolean { return true}
  // constructor(private userService: UsersService, private router: Router) {}

  // isUserLoggedIn(): boolean {
  //   const jwt = sessionStorage.getItem('jwt');
  //   return jwt !== null && jwt !== undefined;
  // }

  // isAdmin(): boolean {
  //   const jwt = sessionStorage.getItem('jwt');

  //   if (!jwt) {
  //     return false;
  //   }

  //   const tokenParts = jwt.split('.');

  //   if (tokenParts.length !== 3) {
  //     return false;
  //   }

  //   const payload = JSON.parse(atob(tokenParts[1]));
  //   console.log('Payload', payload);
  //   return payload && payload.role && payload.role.includes('ADMIN');
  // }

  // logout(): void {
  //   this.userService.logoutUser(sessionStorage.getItem('jwt')).subscribe(
  //     () => {
  //       console.log('Logout successful');

  //       this.router.navigate(['/home']);
  //     },
  //     (error) => {
  //       console.error('Error logging out', error);
  //     }
  //   );
  // }
}
