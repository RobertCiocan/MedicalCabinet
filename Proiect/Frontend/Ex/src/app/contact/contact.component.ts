import { Component } from '@angular/core';
import { Form, FormControl, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-contact',
  templateUrl: './contact.component.html',
  styleUrls: ['./contact.component.scss']
})
export class ContactComponent {
  formGroup: FormGroup = new FormGroup({});
  firstName: FormControl = new FormControl('',[Validators.required]);
  lastName: FormControl = new FormControl('',[Validators.required]);
  email: FormControl = new FormControl('',[Validators.required]);
  message: FormControl = new FormControl('');

  ngOnInit(){
    this.formGroup = new FormGroup({
      firstName: this.firstName,
      lastName: this.lastName,
      email: this.email,
      message: this.message
    });
  }

  submitForm(){
    
  }
}
