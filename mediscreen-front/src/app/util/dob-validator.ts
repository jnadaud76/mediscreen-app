import {FormControl} from "@angular/forms";

export function dateValidation(control : FormControl): { [p: string]: boolean } | null {
  let curDate = new Date();
  curDate = new Date(Date.UTC(curDate.getUTCFullYear(), curDate.getUTCMonth(), curDate.getUTCDate()));

  let inputDate = new Date(control.value);

  inputDate = new Date(Date.UTC(inputDate.getUTCFullYear(), inputDate.getUTCMonth(), inputDate.getUTCDate()));

  if(curDate < inputDate) {
    return {invalidDate: true};
  }
  return null;
}
