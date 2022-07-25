import {Component, Input, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {PatientModel} from "../shared/model/patient.model";
import {PatientService} from "../shared/service/patient.service";
import {Router} from "@angular/router";
import {catchError, map, throwError} from "rxjs";
import {DatePipe} from "@angular/common";
import {dateValidation} from "../util/dob-validator";
import {noWhitespaceValidator} from "../util/whitespace-validator";


@Component({
  selector: 'app-patient-create',
  templateUrl: './patient-create.component.html',
  styleUrls: ['./patient-create.component.scss'],
  providers: [DatePipe]
})
export class PatientCreateComponent implements OnInit {

  patientCreateForm!: FormGroup;
  @Input()
  showCreate!: boolean;
  patientCreate!: PatientModel;
  errorMessage!: string;
  currentDate=new Date();

  constructor(private datePipe: DatePipe, private patientService: PatientService, private formBuilder: FormBuilder, private router: Router) { }

  ngOnInit(): void {

    this.patientCreateForm = this.formBuilder.group({
      lastName: ['', {validators: [Validators.required, Validators.maxLength(100), noWhitespaceValidator]}],
      firstName: ['', {validators: [Validators.required, Validators.maxLength(100), noWhitespaceValidator]}],
      dateOfBirth: [this.datePipe.transform(this.currentDate, 'yyyy-MM-dd'), {validators: [Validators.required, dateValidation.bind(this)]}],
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

    this.patientService.createPatient(this.patientCreate).pipe(catchError(error => {
      this.errorMessage = error;
      return throwError(() => error.message());
    }))
      .pipe(map(() => this.router.navigateByUrl('dashboard'))).subscribe();
  }

   onCancelForm() {
    this.router.navigateByUrl('dashboard')
  }

}
