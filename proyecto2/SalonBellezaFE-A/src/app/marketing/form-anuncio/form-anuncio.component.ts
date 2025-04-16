import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-form-anuncio',
  standalone: true,
  imports: [],
  templateUrl: './form-anuncio.component.html',
  styleUrl: './form-anuncio.component.scss'
})

export class FormAnuncioComponent {
  private formAnuncio: FormGroup;
  private imagen: File | null = null;

  constructor(private formBuilder: FormBuilder){
    this.formAnuncio = this.formBuilder.group(
      {
        tipo: ['', [Validators.required]]
      }
    );
  }
}
