import {Component, Input, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {PatientModel} from "../shared/model/patient.model";
import {PatientService} from "../shared/service/patient.service";
import {Router} from "@angular/router";
import {map} from "rxjs";

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

  constructor(private patientService: PatientService, private formBuilder: FormBuilder, private router: Router) { }

  ngOnInit(): void {
    this.patientCreateForm = this.formBuilder.group({
      lastName: ['', {validators: [Validators.required, Validators.maxLength(100)]}],
      firstName: ['', {validators: [Validators.required, Validators.maxLength(100)]}],
      dateOfBirth: ['', {validators: [Validators.required]}],
      gender: ['', {validators: Validators.required}],
      address: ['', {validators: [Validators.maxLength(300)]}],
      phoneNumber: ['', {validators: [Validators.maxLength(20)]}],
    }, {
      updateOn: 'change',
    });
  }

  onSubmitForm() {
    this.patientCreate = {
      id:0,
      firstName: this.patientCreateForm.value.firstName,
      lastName: this.patientCreateForm.value.lastName,
      dateOfBirth: this.patientCreateForm.value.dateOfBirth,
      gender: this.patientCreateForm.value.gender,
      address: this.patientCreateForm.value.address,
      phoneNumber: this.patientCreateForm.value.phoneNumber
    }

    this.patientService.createPatient(this.patientCreate)
      .pipe(map(() => this.router.navigateByUrl('dashboard'))).subscribe();
  }

  onCancelForm() {
    this.router.navigateByUrl('dashboard')
  }

}
