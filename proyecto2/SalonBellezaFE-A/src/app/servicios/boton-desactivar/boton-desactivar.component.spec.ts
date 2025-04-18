import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BotonDesactivarComponent } from './boton-desactivar.component';

describe('BotonDesactivarComponent', () => {
  let component: BotonDesactivarComponent;
  let fixture: ComponentFixture<BotonDesactivarComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [BotonDesactivarComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BotonDesactivarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
