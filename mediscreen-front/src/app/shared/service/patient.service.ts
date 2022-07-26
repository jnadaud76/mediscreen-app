import {PatientModel} from "../model/patient.model";
import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class PatientService {

  private _PATIENT_API_URL: string = "http://localhost:8081/api"

  get PATIENT_API_URL(): string {
    return this._PATIENT_API_URL;
  }

  constructor(private http: HttpClient) {
  }

  getAllPatient(): Observable<PatientModel[]> {
    return this.http.get<PatientModel[]>(this.PATIENT_API_URL + `/patients`);
  }

  getPatientById(patientId: number): Observable<PatientModel> {
    return this.http.get<PatientModel>(this.PATIENT_API_URL + `/patient/id?patientId=${patientId}`);
  }

  getPatient(firstName: string, lastName: string): Observable<PatientModel> {
    return this.http.get<PatientModel>(this.PATIENT_API_URL + `/patient?firstName=${firstName}&lastName=${lastName}`);
  }

  updatePatient(patientModel: PatientModel) {
    return this.http.put(this.PATIENT_API_URL + `/patient/update`, patientModel);
  }

  createPatient(patientModel: PatientModel) {
    return this.http.post(this.PATIENT_API_URL + `/patient/add/json`, patientModel);
  }

}
