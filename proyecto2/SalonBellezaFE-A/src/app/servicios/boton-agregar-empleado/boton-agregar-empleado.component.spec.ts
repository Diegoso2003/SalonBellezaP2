import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BotonAgregarEmpleadoComponent } from './boton-agregar-empleado.component';

describe('BotonAgregarEmpleadoComponent', () => {
  let component: BotonAgregarEmpleadoComponent;
  let fixture: ComponentFixture<BotonAgregarEmpleadoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [BotonAgregarEmpleadoComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BotonAgregarEmpleadoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
