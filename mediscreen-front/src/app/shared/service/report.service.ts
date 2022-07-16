import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {ReportModel} from "../model/report.model";

@Injectable({
  providedIn: 'root'
})
export class ReportService {

  constructor(private http: HttpClient) {
  }

  getReportByPatientId(patientId: number): Observable<ReportModel> {
    return this.http.get<ReportModel>(`http://localhost:8080/api/report/id?patientId=${patientId}`);
  }
}
