import { ComponentFixture, TestBed } from '@angular/core/testing';

import { VistaReporteEmpleadoCitasComponent } from './vista-reporte-empleado-citas.component';

describe('VistaReporteEmpleadoCitasComponent', () => {
  let component: VistaReporteEmpleadoCitasComponent;
  let fixture: ComponentFixture<VistaReporteEmpleadoCitasComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [VistaReporteEmpleadoCitasComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(VistaReporteEmpleadoCitasComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
