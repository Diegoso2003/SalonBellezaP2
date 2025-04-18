import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BotonAgendarComponent } from './boton-agendar.component';

describe('BotonAgendarComponent', () => {
  let component: BotonAgendarComponent;
  let fixture: ComponentFixture<BotonAgendarComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [BotonAgendarComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BotonAgendarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
