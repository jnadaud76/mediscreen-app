import {Component, OnInit} from '@angular/core';
import {PatientModel} from "../shared/model/patient.model";
import {PatientService} from "../shared/service/patient.service";
import {map, Observable} from "rxjs";
import {PractitionerNoteModel} from "../shared/model/practitioner-note.model";
import {PractitionerNoteService} from "../shared/service/practitioner-note.service";
import {ActivatedRoute, Router} from "@angular/router";

@Component({
  selector: 'app-patient',
  templateUrl: './patient.component.html',
  styleUrls: ['./patient.component.scss']
})
export class PatientComponent implements OnInit {
  patient$!: Observable<PatientModel>;
  practitionerNote$!: Observable<PractitionerNoteModel[]>;
  patientModel!: PatientModel;
  practitionerNote!: PractitionerNoteModel;
  practitionerNoteToUpdate!: PractitionerNoteModel;
  showUpdateNote = false;
  showCreateNote = false;
  patientId!: number;


  constructor(private patientService: PatientService, private practitionerNoteService: PractitionerNoteService
    , private route: ActivatedRoute, private router: Router) {
  }

  ngOnInit() {
    this.patientId =
      +this.route.snapshot.params['id'];
    this.patient$ = this.patientService.getPatientById(this.patientId).pipe(
      map(response => {
        this.patientModel = response
        this.practitionerNote$ = this.practitionerNoteService.getPractitionerNoteByPatientId(this.patientModel.id)
        return this.patientModel
      }));
  }


  onSubmitCreate() {
    this.showCreateNote = true;
    this.showUpdateNote = false
  }

  onUpdate() {
    this.router.navigateByUrl(`patient-update/${this.patientId}`)
  }

  onUpdateNote(note: PractitionerNoteModel) {
    this.practitionerNoteToUpdate = note;
    this.showUpdateNote = true;
    this.showCreateNote = false
  }

}
