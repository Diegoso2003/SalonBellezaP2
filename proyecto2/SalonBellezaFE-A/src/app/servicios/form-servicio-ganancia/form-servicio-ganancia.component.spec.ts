import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FormServicioGananciaComponent } from './form-servicio-ganancia.component';

describe('FormServicioGananciaComponent', () => {
  let component: FormServicioGananciaComponent;
  let fixture: ComponentFixture<FormServicioGananciaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [FormServicioGananciaComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(FormServicioGananciaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
