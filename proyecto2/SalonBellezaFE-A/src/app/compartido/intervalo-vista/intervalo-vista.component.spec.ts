import { ComponentFixture, TestBed } from '@angular/core/testing';

import { IntervaloVistaComponent } from './intervalo-vista.component';

describe('IntervaloVistaComponent', () => {
  let component: IntervaloVistaComponent;
  let fixture: ComponentFixture<IntervaloVistaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [IntervaloVistaComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(IntervaloVistaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
