import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {PractitionerNoteModel} from "../model/practitioner-note.model";

@Injectable({
  providedIn: 'root'
})
export class PractitionerNoteService {

  constructor(private http: HttpClient) {
  }

  getPractitionerNoteByPatientId(patientId: number | undefined): Observable<PractitionerNoteModel[]> {
    return this.http.get<PractitionerNoteModel[]>(`http://localhost:8082/api/history/patHistory/id?patientId=${patientId}`);
  }
}
