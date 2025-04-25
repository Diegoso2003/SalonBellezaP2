import { ComponentFixture, TestBed } from '@angular/core/testing';

import { VistaReporteClienteGastosComponent } from './vista-reporte-cliente-gastos.component';

describe('VistaReporteClienteGastosComponent', () => {
  let component: VistaReporteClienteGastosComponent;
  let fixture: ComponentFixture<VistaReporteClienteGastosComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [VistaReporteClienteGastosComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(VistaReporteClienteGastosComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
