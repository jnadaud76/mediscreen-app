import {PatientModel} from "./patient.model";
import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class PatientService {

  constructor(private http: HttpClient) {
  }

  getAllPatient(): Observable<PatientModel[]> {
    return this.http.get<PatientModel[]>(`http://localhost:8081/api/patient/patient/patients`);
  }

  getPatientById(patientId: number): Observable<PatientModel> {
    return this.http.get<PatientModel>(`http://localhost:8081/api/patient/patient/id?patientId=${patientId}`);
  }

  getPatient(firstName: string, lastName: string): Observable<PatientModel> {
    return this.http.get<PatientModel>(`http://localhost:8081/api/patient/patient?firstName=${firstName}&lastName=${lastName}`);
  }

  updatePatient(patientModel: PatientModel) {
      return this.http.put(`http://localhost:8081/api/patient/patient/update`, patientModel);
  }

  createPatient(patientModel: PatientModel) {
    return this.http.post(`http://localhost:8081/api/patient/patient/add/json`, patientModel);
  }

}
