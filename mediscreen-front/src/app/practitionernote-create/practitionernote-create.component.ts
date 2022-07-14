import {Component, Input, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {PractitionerNoteModel} from "../shared/model/practitioner-note.model";
import {ActivatedRoute, Router} from "@angular/router";
import {PractitionerNoteService} from "../shared/service/practitioner-note.service";

@Component({
  selector: 'app-practitionernote-create',
  templateUrl: './practitionernote-create.component.html',
  styleUrls: ['./practitionernote-create.component.scss']
})
export class PractitionernoteCreateComponent implements OnInit {

  noteCreateForm!: FormGroup;
  @Input()
  showNoteCreate!: boolean;
  noteCreate!: PractitionerNoteModel;
  patientId!:number

  constructor(private practitionerNoteService: PractitionerNoteService, private formBuilder: FormBuilder, private router: Router, private route : ActivatedRoute) { }

  ngOnInit(): void {
    this.noteCreateForm = this.formBuilder.group({
      note: ['', {validators: Validators.required}],
    }, {
      updateOn: 'change'
    });
    this.patientId=
      +this.route.snapshot.params['id'];
  }


  onSubmitForm(){
    this.noteCreate = {
      patientId: this.patientId,
      note: this.noteCreateForm.value.note
     }
    this.practitionerNoteService.createPractitionerNote(this.noteCreate).subscribe()
    this.showNoteCreate=false;
    window.location.reload()
  }

  onCancelForm() {
    this.showNoteCreate=false;
    window.location.reload()
  }
}
