import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FormEmpleadoCitasComponent } from './form-empleado-citas.component';

describe('FormEmpleadoCitasComponent', () => {
  let component: FormEmpleadoCitasComponent;
  let fixture: ComponentFixture<FormEmpleadoCitasComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [FormEmpleadoCitasComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(FormEmpleadoCitasComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
