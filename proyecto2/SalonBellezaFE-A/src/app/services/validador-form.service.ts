import { Injectable } from '@angular/core';
import { FormGroup } from '@angular/forms';

@Injectable({
  providedIn: 'root'
})
export class ValidadorFormService {
  form !: FormGroup;
  constructor() {
  }

  public initForm(form: FormGroup) {
      this.form = form;
  }
  public hasErrors(field: string, typeError: string): boolean | undefined{
      return this.form.get(field)?.hasError(typeError) && this.form.get(field)?.touched;
  }

  public esValido(field: string): boolean | undefined{
      return this.form.get(field)?.valid && this.form.get(field)?.touched;
  }
}
