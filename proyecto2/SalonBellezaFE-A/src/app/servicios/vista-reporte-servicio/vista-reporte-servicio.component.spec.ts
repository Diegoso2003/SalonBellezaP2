import { ComponentFixture, TestBed } from '@angular/core/testing';

import { VistaReporteServicioComponent } from './vista-reporte-servicio.component';

describe('VistaReporteServicioComponent', () => {
  let component: VistaReporteServicioComponent;
  let fixture: ComponentFixture<VistaReporteServicioComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [VistaReporteServicioComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(VistaReporteServicioComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
