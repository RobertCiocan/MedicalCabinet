import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { UsersService } from './users.service';
import { AuthenticationRequest } from './models/Requests/authentication-request';
import { RegistrationRequest } from './models/Requests/registration-request';

describe('UsersService', () => {
  let service: UsersService;
  let httpTestingController: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    service = TestBed.inject(UsersService);
    httpTestingController = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpTestingController.verify();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should register a user', () => {
    const mockRegistrationRequest: RegistrationRequest = {
      username: 'testuser',
      password: 'password123',
      role: 'ROLE_USER',
    };

    service.registerUser(mockRegistrationRequest).subscribe(
      (response) => {
        console.log('User registered:', response);
      },
      (error) => {
        console.error('Registration failed', error);
      }
    );

    const req = httpTestingController.expectOne(`${service.apiUrl}/register`);
    expect(req.request.method).toEqual('POST');
    req.flush('Registration successful', { status: 200, statusText: 'OK' });
  });

  it('should sign in a user', () => {
    const mockAuthenticationRequest: AuthenticationRequest = {
      username: 'testuser',
      password: 'password123',
    };

    const mockToken = 'mockToken';

    service.signInUser(mockAuthenticationRequest).subscribe((token) => {
      expect(token).toEqual(mockToken);
    });

    const req = httpTestingController.expectOne(`${service.apiUrl}/login`);
    expect(req.request.method).toEqual('POST');
    req.flush(mockToken);
  });

  it('should logout a user', () => {
    const mockJwt = 'mockJwt';

    service.logoutUser(mockJwt).subscribe((response) => {
      expect(response).toBeTruthy();
    });

    const req = httpTestingController.expectOne(`${service.apiUrl}/logout`);
    expect(req.request.method).toEqual('POST');
    req.flush('', { status: 200, statusText: 'OK' });
  });
});
