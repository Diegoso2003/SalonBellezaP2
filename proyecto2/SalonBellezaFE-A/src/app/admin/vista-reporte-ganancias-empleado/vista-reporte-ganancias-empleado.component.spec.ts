import { ComponentFixture, TestBed } from '@angular/core/testing';

import { VistaReporteGananciasEmpleadoComponent } from './vista-reporte-ganancias-empleado.component';

describe('VistaReporteGananciasEmpleadoComponent', () => {
  let component: VistaReporteGananciasEmpleadoComponent;
  let fixture: ComponentFixture<VistaReporteGananciasEmpleadoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [VistaReporteGananciasEmpleadoComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(VistaReporteGananciasEmpleadoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
