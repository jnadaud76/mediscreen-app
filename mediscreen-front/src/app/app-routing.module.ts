import {RouterModule, Routes} from "@angular/router";
import {NgModule} from "@angular/core";
import {PatientComponent} from "./patient/patient.component";
import {PatientUpdateComponent} from "./patient-update/patient-update.component";
import {DashboardComponent} from "./dashboard/dashboard.component";
import {PatientCreateComponent} from "./patient-create/patient-create.component";
import {PatientListComponent} from "./patient-list/patient-list.component";

const routes: Routes = [
  {path: '', redirectTo: 'dashboard', pathMatch: 'full'},
  {path: 'patient/:id', component: PatientComponent},
  {path: 'patients', component: PatientListComponent},
  {path: 'patient-update/:id', component: PatientUpdateComponent},
  {path: 'patient-create', component: PatientCreateComponent},
  {path: 'dashboard', component: DashboardComponent},
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
