import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TablaEmpleadoInformeComponent } from './tabla-empleado-informe.component';

describe('TablaEmpleadoInformeComponent', () => {
  let component: TablaEmpleadoInformeComponent;
  let fixture: ComponentFixture<TablaEmpleadoInformeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TablaEmpleadoInformeComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TablaEmpleadoInformeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
