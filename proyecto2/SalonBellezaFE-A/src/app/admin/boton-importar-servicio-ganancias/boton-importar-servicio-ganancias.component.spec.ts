import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BotonImportarServicioGananciasComponent } from './boton-importar-servicio-ganancias.component';

describe('BotonImportarServicioGananciasComponent', () => {
  let component: BotonImportarServicioGananciasComponent;
  let fixture: ComponentFixture<BotonImportarServicioGananciasComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [BotonImportarServicioGananciasComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BotonImportarServicioGananciasComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
