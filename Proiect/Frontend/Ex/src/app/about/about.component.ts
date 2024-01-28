import { Component } from '@angular/core';

@Component({
  selector: 'app-about',
  templateUrl: './about.component.html',
  styleUrls: ['./about.component.scss']
})
export class AboutComponent {
  medicalTeam: string[] = ['Dr. Ana Medicina', 'Dr. Ion Chirurg', 'As. Maria Asistența', 'Dr. Radu Radiolog'];
  location: string = '123 Strada Sănătății, Medicopolis, 5678';
  aboutClinic: string = 'Dedicare pentru Sănătate!';
}

