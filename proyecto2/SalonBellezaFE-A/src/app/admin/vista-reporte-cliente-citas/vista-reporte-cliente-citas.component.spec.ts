import { ComponentFixture, TestBed } from '@angular/core/testing';

import { VistaReporteClienteCitasComponent } from './vista-reporte-cliente-citas.component';

describe('VistaReporteClienteCitasComponent', () => {
  let component: VistaReporteClienteCitasComponent;
  let fixture: ComponentFixture<VistaReporteClienteCitasComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [VistaReporteClienteCitasComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(VistaReporteClienteCitasComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
