import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ReactiveFormsModule } from '@angular/forms';
import { SignInComponent } from './sign-in.component';
import { UsersService } from '../users.service';
import { Router } from '@angular/router';
import { of, throwError } from 'rxjs';

describe('SignInComponent', () => {
  let component: SignInComponent;
  let fixture: ComponentFixture<SignInComponent>;
  let usersServiceSpy: jasmine.SpyObj<UsersService>;
  let routerSpy: jasmine.SpyObj<Router>;

  beforeEach(() => {
    usersServiceSpy = jasmine.createSpyObj('UsersService', ['signInUser']);
    routerSpy = jasmine.createSpyObj('Router', ['navigate']);

    TestBed.configureTestingModule({
      declarations: [SignInComponent],
      imports: [ReactiveFormsModule],
      providers: [
        { provide: UsersService, useValue: usersServiceSpy },
        { provide: Router, useValue: routerSpy },
      ],
    }).compileComponents();

    fixture = TestBed.createComponent(SignInComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should sign in a user', () => {
    const mockAuthenticationRequest = {
      username: 'john.doe',
      password: 'password123',
    };

    usersServiceSpy.signInUser.and.returnValue(of( 'mockToken' ));

    component.signInForm.setValue(mockAuthenticationRequest);
    component.signIn();

    expect(usersServiceSpy.signInUser).toHaveBeenCalledWith(mockAuthenticationRequest);
    expect(sessionStorage.getItem('jwt')).toBe('mockToken');
    expect(routerSpy.navigate).toHaveBeenCalledWith(['/menu']);
  });

  it('should handle sign in failure', () => {
    const mockAuthenticationRequest = {
      username: 'john.doe',
      password: 'password123',
    };

    usersServiceSpy.signInUser.and.returnValue(throwError('Authentication failed'));

    component.signInForm.setValue(mockAuthenticationRequest);
    component.signIn();

    expect(usersServiceSpy.signInUser).toHaveBeenCalledWith(mockAuthenticationRequest);
    expect(sessionStorage.getItem('jwt')).toBeFalsy();
    expect(routerSpy.navigate).not.toHaveBeenCalled();
    expect(component.invalidCredentials).toBe(true);
  });
});
