import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HorarioEmpleadoComponent } from './horario-empleado.component';

describe('HorarioEmpleadoComponent', () => {
  let component: HorarioEmpleadoComponent;
  let fixture: ComponentFixture<HorarioEmpleadoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HorarioEmpleadoComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(HorarioEmpleadoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
