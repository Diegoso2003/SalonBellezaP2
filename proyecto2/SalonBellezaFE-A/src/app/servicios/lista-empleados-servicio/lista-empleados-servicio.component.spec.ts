import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListaEmpleadosServicioComponent } from './lista-empleados-servicio.component';

describe('ListaEmpleadosServicioComponent', () => {
  let component: ListaEmpleadosServicioComponent;
  let fixture: ComponentFixture<ListaEmpleadosServicioComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ListaEmpleadosServicioComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ListaEmpleadosServicioComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
