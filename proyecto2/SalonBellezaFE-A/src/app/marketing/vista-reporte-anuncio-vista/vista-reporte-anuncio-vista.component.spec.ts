import { ComponentFixture, TestBed } from '@angular/core/testing';

import { VistaReporteAnuncioVistaComponent } from './vista-reporte-anuncio-vista.component';

describe('VistaReporteAnuncioVistaComponent', () => {
  let component: VistaReporteAnuncioVistaComponent;
  let fixture: ComponentFixture<VistaReporteAnuncioVistaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [VistaReporteAnuncioVistaComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(VistaReporteAnuncioVistaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
