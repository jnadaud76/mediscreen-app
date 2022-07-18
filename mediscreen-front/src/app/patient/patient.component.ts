import {Component, OnInit} from '@angular/core';
import {PatientModel} from "../shared/model/patient.model";
import {PatientService} from "../shared/service/patient.service";
import {catchError, map, Observable, throwError} from "rxjs";
import {PractitionerNoteModel} from "../shared/model/practitioner-note.model";
import {PractitionerNoteService} from "../shared/service/practitioner-note.service";
import {ActivatedRoute, Router} from "@angular/router";
import {ReportModel} from "../shared/model/report.model";
import {ReportService} from "../shared/service/report.service";

@Component({
  selector: 'app-patient',
  templateUrl: './patient.component.html',
  styleUrls: ['./patient.component.scss']
})
export class PatientComponent implements OnInit {
  patient$!: Observable<PatientModel>;
  practitionerNote$!: Observable<PractitionerNoteModel[]>;
  report$!: Observable<ReportModel>;
  patientModel!: PatientModel;
  practitionerNote!: PractitionerNoteModel;
  practitionerNoteToUpdate!: PractitionerNoteModel;
  showUpdateNote = false;
  showCreateNote = false;
  patientId!: number;
  errorMessage!: string
  errorMessageNote!: string

  constructor(private patientService: PatientService, private practitionerNoteService: PractitionerNoteService
    , private reportService: ReportService ,private route: ActivatedRoute, private router: Router) {
  }

  ngOnInit() {
    this.patientId =
      +this.route.snapshot.params['id'];
    this.patient$ = this.patientService.getPatientById(this.patientId).pipe(
      map(response => {
        this.patientModel = response
        this.practitionerNote$ = this.practitionerNoteService.getPractitionerNoteByPatientId(this.patientModel.id).pipe(catchError(error => {
          this.errorMessageNote = error;
          return throwError(() => error.message());
        }))
        this.report$ = this.reportService.getReportByPatientId(this.patientModel.id)
        return this.patientModel
      })).pipe(catchError(error => {
      this.errorMessage = error;
      return throwError(() => error.message());
    }));
  }


  onSubmitCreate() {
    this.showCreateNote = true;
    this.showUpdateNote = false;
    this.errorMessageNote = "";
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
