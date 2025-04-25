import { ComponentFixture, TestBed } from '@angular/core/testing';

import { VistaServicioGananciaComponent } from './vista-servicio-ganancia.component';

describe('VistaServicioGananciaComponent', () => {
  let component: VistaServicioGananciaComponent;
  let fixture: ComponentFixture<VistaServicioGananciaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [VistaServicioGananciaComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(VistaServicioGananciaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
