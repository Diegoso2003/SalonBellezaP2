import { AfterViewInit, Component, Input, OnDestroy } from '@angular/core';
import { Informacion } from '../../models/informacion';
import { NgbToastModule } from '@ng-bootstrap/ng-bootstrap';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-informe-exito',
  standalone: true,
  imports: [NgbToastModule, CommonModule],
  templateUrl: './informe-exito.component.html',
  styleUrl: './informe-exito.component.scss'
})
export class InformeExitoComponent implements AfterViewInit, OnDestroy{
  @Input({ required: true })
  informacion!: Informacion;

  mostrar:boolean = true;
  autohide = true;
  delay = 5000;

  constructor() { }

  ngAfterViewInit(): void {
    setTimeout(() => {
      this.mostrarToast();
    });
  }

  ngOnDestroy(): void {
    this.ocultarToast();
  }

  mostrarToast() {
    this.mostrar = true;
  }

  ocultarToast() {
    this.mostrar = false;
    this.informacion.ocultarExito();
  }
}
