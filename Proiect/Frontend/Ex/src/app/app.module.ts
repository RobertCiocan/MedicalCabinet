import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { ContactComponent } from './contact/contact.component';
import { HeaderComponent } from './header/header.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { HttpClientModule } from '@angular/common/http';
import { UsersModule } from './users/users.module';
import { AboutComponent } from './about/about.component';
import { PatientPageComponent } from './appointments-patient/patient-page.component';
import { AdminPageComponent } from './create-doctor/admin-page.component';
import { AppointmentsDoctorComponent } from './appointments-doctor/appointments-doctor.component';
import { ConsultationsDoctorComponent } from './consultations-doctor/consultations-doctor.component';
import { PatientConsultationsComponent } from './consultations-patient/patient-consultations.component';
@NgModule({
  declarations: [
    AppComponent,
    ContactComponent,
    HeaderComponent,
    AboutComponent,
    PatientPageComponent,
    AdminPageComponent,
    AppointmentsDoctorComponent,
    ConsultationsDoctorComponent,
    PatientConsultationsComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    ReactiveFormsModule,
    FormsModule,
    UsersModule,
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
