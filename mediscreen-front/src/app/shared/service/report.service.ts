import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {ReportModel} from "../model/report.model";

@Injectable({
  providedIn: 'root'
})
export class ReportService {

  private _REPORT_API_URL: string = "http://localhost:8080/api"

  get REPORT_API_URL(): string {
    return this._REPORT_API_URL;
  }

  constructor(private http: HttpClient) {
  }

  getReportByPatientId(patientId: number): Observable<ReportModel> {
    return this.http.get<ReportModel>(this.REPORT_API_URL + `/report/id?patientId=${patientId}`);
  }
}
