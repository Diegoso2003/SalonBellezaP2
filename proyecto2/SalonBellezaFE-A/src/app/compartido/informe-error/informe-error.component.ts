import { AfterViewInit, Component, Input, TemplateRef, ViewChild } from '@angular/core';
import { Informacion } from '../../models/informacion';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-informe-error',
  standalone: true,
  imports: [],
  templateUrl: './informe-error.component.html',
  styleUrl: './informe-error.component.scss'
})
export class InformeErrorComponent implements AfterViewInit{
  @ViewChild('modalError') modalError!: TemplateRef<any>;

  @Input({ required: true })
  informacion!: Informacion;

  constructor(private modalService: NgbModal) { }

  ngAfterViewInit(): void {
    setTimeout(() => {
      this.abrirModal();
    });
  }

  abrirModal() {
    this.modalService.open(this.modalError, { 
      backdrop: 'static'
    });
  }

  cerrar(){
    this.modalService.dismissAll();
    this.informacion.ocultarError();
  }
}
