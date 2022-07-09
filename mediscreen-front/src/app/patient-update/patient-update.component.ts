import {Component, Input, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {PatientModel} from "../shared/patient.model";
import {PatientService} from "../shared/patient.service";

@Component({
  selector: 'app-patient-update',
  templateUrl: './patient-update.component.html',
  styleUrls: ['./patient-update.component.scss']
})
export class PatientUpdateComponent implements OnInit {

  patientUpdateForm!: FormGroup;
  @Input()
  patientToUpdate!: PatientModel;
  @Input()
  showUpdate!: boolean;
  patientUpdate!: PatientModel;

  constructor(private patientService: PatientService, private formBuilder: FormBuilder) { }

  ngOnInit(): void {
    this.patientUpdateForm = this.formBuilder.group({
      lastName: [this.patientToUpdate.lastName, {validators: [Validators.required, Validators.maxLength(100)]}],
      firstName: [this.patientToUpdate.firstName, {validators: [Validators.required, Validators.maxLength(100)]}],
      dateOfBirth: [this.patientToUpdate.dateOfBirth, {validators: Validators.required}],
      gender: [this.patientToUpdate.gender, {validators: Validators.required}],
      address: [this.patientToUpdate.address, {validators: [Validators.required, Validators.maxLength(300)]}],
      phoneNumber: [this.patientToUpdate.phoneNumber, {validators: [Validators.required, Validators.maxLength(20)]}],
    }, {
      updateOn: 'change'
      });
  }

  onSubmitForm() {
    this.patientUpdate = {
      id: this.patientToUpdate.id,
      firstName: this.patientUpdateForm.value.firstName,
      lastName: this.patientUpdateForm.value.lastName,
      dateOfBirth: this.patientUpdateForm.value.dateOfBirth,
      gender: this.patientUpdateForm.value.gender,
      address: this.patientUpdateForm.value.address,
      phoneNumber: this.patientUpdateForm.value.phoneNumber
    }

    this.patientService.updatePatient(this.patientUpdate).subscribe();

    this.showUpdate=false;
    window.location.reload();
  }

  }
