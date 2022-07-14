import {Component, OnInit} from '@angular/core';
import {PatientModel} from "../shared/model/patient.model";
import {Observable} from "rxjs";
import {PatientService} from "../shared/service/patient.service";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Router} from "@angular/router";

@Component({
  selector: 'app-patient-list',
  templateUrl: './patient-list.component.html',
  styleUrls: ['./patient-list.component.scss']
})
export class PatientListComponent implements OnInit {

  patientList$!: Observable<PatientModel[]>
  patient$!: Observable<PatientModel>
  patientForm! : FormGroup;
  pageNumber: number = 1
  showPatients=true;
  showPatientDetails=false;
  showPatient=false;
  patientId!: number;

  constructor(private patientService: PatientService, private formBuilder: FormBuilder, private router: Router) { }

  ngOnInit(): void {
    this.patientList$ = this.patientService.getAllPatient();
    this.patientForm = this.formBuilder.group({
      lastName: ['', {validators: [Validators.required, Validators.maxLength(100)]}],
      firstName: ['', {validators: [Validators.required, Validators.maxLength(100)]}],

    }, {
      updateOn: 'change'
    });
  }

  onSubmitSearch() {
    this.patient$ = this.patientService.getPatient(this.patientForm.value.firstName, this.patientForm.value.lastName);
    this.showPatients=false;
    this.showPatient=true;
   // this.showPatientDetails=false

  }

  onSubmitClear() {
    window.location.reload()
  }

  onSubmitCreate() {
    // @ts-ignore
    this.router.navigateByUrl('patient-create');
  }

  onShow (id: number) {
    this.patientId=id
    this.router.navigateByUrl(`patient/${this.patientId}`)
  }

}
