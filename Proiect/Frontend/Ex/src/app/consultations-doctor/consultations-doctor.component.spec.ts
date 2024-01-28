import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ConsultationsDoctorComponent } from './consultations-doctor.component';

describe('ConsultationsDoctorComponent', () => {
  let component: ConsultationsDoctorComponent;
  let fixture: ComponentFixture<ConsultationsDoctorComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ConsultationsDoctorComponent]
    });
    fixture = TestBed.createComponent(ConsultationsDoctorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
