import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FormEmpleadoGananciasComponent } from './form-empleado-ganancias.component';

describe('FormEmpleadoGananciasComponent', () => {
  let component: FormEmpleadoGananciasComponent;
  let fixture: ComponentFixture<FormEmpleadoGananciasComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [FormEmpleadoGananciasComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(FormEmpleadoGananciasComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
