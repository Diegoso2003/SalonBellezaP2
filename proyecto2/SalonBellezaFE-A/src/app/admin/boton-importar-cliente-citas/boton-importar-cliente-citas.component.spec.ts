import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BotonImportarClienteCitasComponent } from './boton-importar-cliente-citas.component';

describe('BotonImportarClienteCitasComponent', () => {
  let component: BotonImportarClienteCitasComponent;
  let fixture: ComponentFixture<BotonImportarClienteCitasComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [BotonImportarClienteCitasComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BotonImportarClienteCitasComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
