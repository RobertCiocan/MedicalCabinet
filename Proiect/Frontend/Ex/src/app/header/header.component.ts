import { Component } from '@angular/core';
import { Router } from '@angular/router';

import { UsersService } from '../users/users.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent {
  constructor(private userService: UsersService, private router: Router) {}

  isUserLoggedIn(): boolean {
    const jwt = sessionStorage.getItem('jwt');
    return jwt !== null && jwt !== undefined;
  }

  hasRole(): string | null {
    const jwt = sessionStorage.getItem('jwt');

    if (!jwt) {
      return null;
    }

    const tokenParts = jwt.split('.');

    if (tokenParts.length !== 3) {
      return null;
    }

    const payload = JSON.parse(atob(tokenParts[1]));
    return payload.role;
  }

  logout(): void {
    this.userService.logoutUser(sessionStorage.getItem('jwt')).subscribe(
      () => {
        console.log('Logout successful');

        this.router.navigate(['/users/sign-in']);
      },
      (error) => {
        console.error('Error logging out', error);
      }
    );
  }
}
