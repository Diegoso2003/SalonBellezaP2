import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FormServicioGananciasComponent } from './form-servicio-ganancias.component';

describe('FormServicioGananciasComponent', () => {
  let component: FormServicioGananciasComponent;
  let fixture: ComponentFixture<FormServicioGananciasComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [FormServicioGananciasComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(FormServicioGananciasComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
