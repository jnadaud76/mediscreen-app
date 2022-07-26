import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {PractitionerNoteModel} from "../model/practitioner-note.model";

@Injectable({
  providedIn: 'root'
})
export class PractitionerNoteService {

  private _HISTORY_API_URL: string = "http://localhost:8082/api"

  get HISTORY_API_URL(): string {
    return this._HISTORY_API_URL;
  }

  constructor(private http: HttpClient) {
  }

  getPractitionerNoteByPatientId(patientId: number | undefined): Observable<PractitionerNoteModel[]> {
    return this.http.get<PractitionerNoteModel[]>(this.HISTORY_API_URL + `/patHistory/id?patientId=${patientId}`);
  }

  updatePractitionerNote(practitionerNoteModel: PractitionerNoteModel): Observable<PractitionerNoteModel> {
    return this.http.put<PractitionerNoteModel>(this.HISTORY_API_URL + `/patHistory/update`, practitionerNoteModel)
  }

  createPractitionerNote(practitionerNoteModel: PractitionerNoteModel): Observable<PractitionerNoteModel> {
    return this.http.post<PractitionerNoteModel>(this.HISTORY_API_URL + `/patHistory/add/json`, practitionerNoteModel)
  }
}
