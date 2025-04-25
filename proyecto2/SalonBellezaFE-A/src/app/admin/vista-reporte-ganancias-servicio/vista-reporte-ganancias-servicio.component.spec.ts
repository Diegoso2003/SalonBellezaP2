import { ComponentFixture, TestBed } from '@angular/core/testing';

import { VistaReporteGananciasServicioComponent } from './vista-reporte-ganancias-servicio.component';

describe('VistaReporteGananciasServicioComponent', () => {
  let component: VistaReporteGananciasServicioComponent;
  let fixture: ComponentFixture<VistaReporteGananciasServicioComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [VistaReporteGananciasServicioComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(VistaReporteGananciasServicioComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
