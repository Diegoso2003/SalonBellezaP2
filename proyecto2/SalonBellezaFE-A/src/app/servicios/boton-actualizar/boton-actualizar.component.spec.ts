import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BotonActualizarComponent } from './boton-actualizar.component';

describe('BotonActualizarComponent', () => {
  let component: BotonActualizarComponent;
  let fixture: ComponentFixture<BotonActualizarComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [BotonActualizarComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BotonActualizarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
