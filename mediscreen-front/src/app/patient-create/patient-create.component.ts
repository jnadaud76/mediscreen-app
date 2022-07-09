import {Component, Input, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {PatientModel} from "../shared/patient.model";
import {PatientService} from "../shared/patient.service";

@Component({
  selector: 'app-patient-create',
  templateUrl: './patient-create.component.html',
  styleUrls: ['./patient-create.component.scss']
})
export class PatientCreateComponent implements OnInit {

  patientCreateForm!: FormGroup;
  @Input()
  showCreate!: boolean;
  patientCreate!: PatientModel;

  constructor(private patientService: PatientService, private formBuilder: FormBuilder) { }

  ngOnInit(): void {
    this.patientCreateForm = this.formBuilder.group({
      lastName: ['', {validators: [Validators.required, Validators.maxLength(100)]}],
      firstName: ['', {validators: [Validators.required, Validators.maxLength(100)]}],
      dateOfBirth: ['', {validators: Validators.required}],
      gender: ['', {validators: Validators.required}],
      address: ['', {validators: [Validators.required, Validators.maxLength(300)]}],
      phoneNumber: ['', {validators: [Validators.required, Validators.maxLength(20)]}],
    }, {
      updateOn: 'change'
    });
  }

  onSubmitForm() {
    this.patientCreate = {
      firstName: this.patientCreateForm.value.firstName,
      lastName: this.patientCreateForm.value.lastName,
      dateOfBirth: this.patientCreateForm.value.dateOfBirth,
      gender: this.patientCreateForm.value.gender,
      address: this.patientCreateForm.value.address,
      phoneNumber: this.patientCreateForm.value.phoneNumber
    }

    this.patientService.createPatient(this.patientCreate).subscribe();

    this.showCreate=false;
    window.location.reload();
  }

}
