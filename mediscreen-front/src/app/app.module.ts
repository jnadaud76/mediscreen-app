import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import { PatientComponent } from './patient/patient.component';
import {HttpClientModule} from "@angular/common/http";
import {ReactiveFormsModule} from "@angular/forms";
import {AppRoutingModule} from "./app-routing.module";
import { HeaderComponent } from './header/header.component';
import { PatientUpdateComponent } from './patient-update/patient-update.component';
import { PatientCreateComponent } from './patient-create/patient-create.component';

@NgModule({
  declarations: [
    AppComponent,
    PatientComponent,
    HeaderComponent,
    PatientUpdateComponent,
    PatientCreateComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    ReactiveFormsModule,
    AppRoutingModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
