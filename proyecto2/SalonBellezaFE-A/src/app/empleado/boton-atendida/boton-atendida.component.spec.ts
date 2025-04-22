import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BotonAtendidaComponent } from './boton-atendida.component';

describe('BotonAtendidaComponent', () => {
  let component: BotonAtendidaComponent;
  let fixture: ComponentFixture<BotonAtendidaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [BotonAtendidaComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BotonAtendidaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
