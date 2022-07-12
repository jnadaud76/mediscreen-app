import {Component, OnInit} from '@angular/core';
import {PatientModel} from "../shared/model/patient.model";
import {PatientService} from "../shared/service/patient.service";
import {map, Observable} from "rxjs";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {PractitionerNoteModel} from "../shared/model/practitioner-note.model";
import {PractitionerNoteService} from "../shared/service/practitioner-note.service";

@Component({
  selector: 'app-patient',
  templateUrl: './patient.component.html',
  styleUrls: ['./patient.component.scss']
})
export class PatientComponent implements OnInit{
  patient$!: Observable<PatientModel>;
  practitionerNote$!: Observable<PractitionerNoteModel[]>;
  patientForm! : FormGroup;
  patientModel! : PatientModel;
  patient!: PatientModel;
  showUpdate=false;
  showCreate=false;


  constructor(private patientService: PatientService, private practitionerNoteService : PractitionerNoteService,
              private formBuilder: FormBuilder) { }

  ngOnInit() {
    this.patientForm = this.formBuilder.group({
      lastName: ['', {validators: [Validators.required, Validators.maxLength(100)]}],
      firstName: ['', {validators: [Validators.required, Validators.maxLength(100)]}],

    }, {
      updateOn: 'change'
    });

  }

  onSubmitSearch() {
    this.patient$ = this.patientService.getPatient(this.patientForm.value.firstName, this.patientForm.value.lastName).pipe(
      map(response => {
        this.patientModel = response
        this.practitionerNote$ = this.practitionerNoteService.getPractitionerNoteByPatientId(this.patientModel.id)
        return this.patientModel
      }));

  this.showUpdate=false;
  this.showCreate=false;
  }

  onSubmitCreate() {
  this.showCreate=true;
  this.showUpdate=false;
  }

  onUpdate() {
  this.showUpdate=true;
  this.showCreate=false;
  }

  onUpdateNote(){

  }

}
