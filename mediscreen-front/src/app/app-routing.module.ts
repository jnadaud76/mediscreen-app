import {RouterModule, Routes} from "@angular/router";
import {NgModule} from "@angular/core";
import {PatientComponent} from "./patient/patient.component";
import {PatientUpdateComponent} from "./patient-update/patient-update.component";

const routes: Routes = [
  {path: '', redirectTo: 'patient', pathMatch: 'full'},
  {path: 'patient', component: PatientComponent},
  {path: 'patient-update', component: PatientUpdateComponent}
];

@NgModule({
  imports: [
    RouterModule.forRoot(routes)
  ],
  exports: [
    RouterModule
  ]
})
export class AppRoutingModule {
}
