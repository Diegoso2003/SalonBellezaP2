import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BotonCatalogoComponent } from './boton-catalogo.component';

describe('BotonCatalogoComponent', () => {
  let component: BotonCatalogoComponent;
  let fixture: ComponentFixture<BotonCatalogoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [BotonCatalogoComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BotonCatalogoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
