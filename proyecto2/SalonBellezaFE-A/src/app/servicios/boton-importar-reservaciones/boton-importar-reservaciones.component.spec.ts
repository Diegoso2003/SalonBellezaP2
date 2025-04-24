import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BotonImportarReservacionesComponent } from './boton-importar-reservaciones.component';

describe('BotonImportarReservacionesComponent', () => {
  let component: BotonImportarReservacionesComponent;
  let fixture: ComponentFixture<BotonImportarReservacionesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [BotonImportarReservacionesComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BotonImportarReservacionesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
