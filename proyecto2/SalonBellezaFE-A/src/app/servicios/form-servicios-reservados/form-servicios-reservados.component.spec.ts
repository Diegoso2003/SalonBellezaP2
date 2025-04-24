import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FormServiciosReservadosComponent } from './form-servicios-reservados.component';

describe('FormServiciosReservadosComponent', () => {
  let component: FormServiciosReservadosComponent;
  let fixture: ComponentFixture<FormServiciosReservadosComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [FormServiciosReservadosComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(FormServiciosReservadosComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
