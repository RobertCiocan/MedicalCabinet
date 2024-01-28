import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { ContactComponent } from './contact/contact.component';
import { AboutComponent } from './about/about.component';
import { PatientPageComponent } from './appointments-patient/patient-page.component';
import { AdminPageComponent } from './create-doctor/admin-page.component';
import { AppointmentsDoctorComponent } from './appointments-doctor/appointments-doctor.component';
import { PatientConsultationsComponent } from './consultations-patient/patient-consultations.component';
import { ConsultationsDoctorComponent } from './consultations-doctor/consultations-doctor.component';

const routes: Routes = [
  {
    path:'contact',
    component:ContactComponent
  },
  {
    path:'about',
    component:AboutComponent
  },
  {
    path: 'users',
    loadChildren: () => import('./users/users.module').then(mod => mod.UsersModule)
  },
  {
    path: 'patient',
    component:PatientPageComponent
  },
  {
    path: 'create-doctor',
    component:AdminPageComponent
  },
  {
    path: 'appointments',
    component:PatientPageComponent
  },
  {
    path: 'consultations',
    component:PatientConsultationsComponent
  },
  {
    path: 'doctor-appointments',
    component:AppointmentsDoctorComponent
  },
  {
    path: 'doctor-consultations',
    component:ConsultationsDoctorComponent
  },
  {
    path: "**",
    component:AboutComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
