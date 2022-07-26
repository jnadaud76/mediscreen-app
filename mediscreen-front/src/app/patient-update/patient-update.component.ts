import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {PatientModel} from "../shared/model/patient.model";
import {PatientService} from "../shared/service/patient.service";
import {ActivatedRoute, Router} from "@angular/router";
import {map, Observable} from "rxjs";
import {dateValidation} from "../util/dob-validator";
import {noWhitespaceValidator} from "../util/whitespace-validator";

@Component({
  selector: 'app-patient-update',
  templateUrl: './patient-update.component.html',
  styleUrls: ['./patient-update.component.scss']
})
export class PatientUpdateComponent implements OnInit {
  patient$!: Observable<PatientModel>
  patientUpdateForm!: FormGroup;
  patientToUpdate!: PatientModel;
  patientUpdate!: PatientModel;
  patientId!: number

  constructor(private patientService: PatientService, private formBuilder: FormBuilder, private router: Router, private route: ActivatedRoute) {
  }

  ngOnInit(): void {
    this.patientId =
      +this.route.snapshot.params['id'];
    this.patient$ = this.patientService.getPatientById(this.patientId)
    this.patientUpdateForm = this.formBuilder.group({
      lastName: ['', {validators: [Validators.required, Validators.maxLength(100), noWhitespaceValidator]}],
      firstName: ['', {validators: [Validators.required, Validators.maxLength(100), noWhitespaceValidator]}],
      dateOfBirth: ['', {validators: [Validators.required, dateValidation.bind(this)]}],
      gender: ['', {validators: Validators.required}],
      address: ['', {validators: Validators.maxLength(300)}],
      phoneNumber: ['', {validators: Validators.maxLength(20)}],
    }, {
      updateOn: 'change'
    });
  }

  onSubmitForm() {
    this.patientUpdate = {
      id: this.patientId,
      firstName: this.patientUpdateForm.value.firstName,
      lastName: this.patientUpdateForm.value.lastName,
      dateOfBirth: this.patientUpdateForm.value.dateOfBirth,
      gender: this.patientUpdateForm.value.gender,
      address: this.patientUpdateForm.value.address,
      phoneNumber: this.patientUpdateForm.value.phoneNumber
    }

    this.patientService.updatePatient(this.patientUpdate)
      .pipe(map(() => this.router.navigateByUrl(`patient/${this.patientId}`))).subscribe();
  }

  onCancelForm() {
    this.router.navigateByUrl(`patient/${this.patientId}`)
  }

}
