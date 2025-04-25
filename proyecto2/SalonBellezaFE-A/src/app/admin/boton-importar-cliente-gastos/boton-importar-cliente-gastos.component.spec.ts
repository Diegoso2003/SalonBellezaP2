import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BotonImportarClienteGastosComponent } from './boton-importar-cliente-gastos.component';

describe('BotonImportarClienteGastosComponent', () => {
  let component: BotonImportarClienteGastosComponent;
  let fixture: ComponentFixture<BotonImportarClienteGastosComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [BotonImportarClienteGastosComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BotonImportarClienteGastosComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
