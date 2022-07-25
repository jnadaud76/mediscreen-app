import {Component, Input, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {PractitionerNoteModel} from "../shared/model/practitioner-note.model";
import {PractitionerNoteService} from "../shared/service/practitioner-note.service";
import {ActivatedRoute, Router} from "@angular/router";
import {noWhitespaceValidator} from "../util/whitespace-validator";

@Component({
  selector: 'app-practitionernote-update',
  templateUrl: './practitionernote-update.component.html',
  styleUrls: ['./practitionernote-update.component.scss']
})
export class PractitionernoteUpdateComponent implements OnInit {

  noteUpdateForm!: FormGroup;
  @Input()
  showNoteUpdate!: boolean;
  @Input()
  noteToUpdate!: PractitionerNoteModel;
  noteUpdate!: PractitionerNoteModel;
  patientId!:number

  constructor(private practitionerNoteService: PractitionerNoteService, private formBuilder: FormBuilder, private router: Router, private route : ActivatedRoute) { }

  ngOnInit(): void {
    this.noteUpdateForm = this.formBuilder.group({
      note: [this.noteToUpdate.note, {validators: [Validators.required, noWhitespaceValidator]}],
    }, {
      updateOn: 'change'
    });
    this.patientId=
      +this.route.snapshot.params['id'];
  }


  onSubmitForm(){
    this.noteUpdate = {
      id: this.noteToUpdate.id,
      patientId: this.patientId,
      note: this.noteUpdateForm.value.note,
      creationDate: this.noteToUpdate.creationDate
    }
    this.practitionerNoteService.updatePractitionerNote(this.noteUpdate).subscribe()
    this.showNoteUpdate=false;
    window.location.reload()
  }

  onCancelForm() {
    this.showNoteUpdate=false;
    window.location.reload()
  }
}
