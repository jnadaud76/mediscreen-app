import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import {MatButtonModule} from '@angular/material/button';

import { AppComponent } from './app.component';
import { PatientComponent } from './patient/patient.component';
import {HttpClientModule} from "@angular/common/http";
import {ReactiveFormsModule} from "@angular/forms";
import {AppRoutingModule} from "./app-routing.module";
import { HeaderComponent } from './header/header.component';
import { PatientUpdateComponent } from './patient-update/patient-update.component';
import { PatientCreateComponent } from './patient-create/patient-create.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { PatientListComponent } from './patient-list/patient-list.component';
import {NgxPaginationModule} from "ngx-pagination";
import { PractitionernoteCreateComponent } from './practitionernote-create/practitionernote-create.component';
import { PractitionernoteUpdateComponent } from './practitionernote-update/practitionernote-update.component';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import {MatIconModule} from "@angular/material/icon";



@NgModule({
  declarations: [
    AppComponent,
    PatientComponent,
    HeaderComponent,
    PatientUpdateComponent,
    PatientCreateComponent,
    DashboardComponent,
    PatientListComponent,
    PractitionernoteCreateComponent,
    PractitionernoteUpdateComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    ReactiveFormsModule,
    AppRoutingModule,
    NgxPaginationModule,
    MatButtonModule,
    NoopAnimationsModule,
    MatIconModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
