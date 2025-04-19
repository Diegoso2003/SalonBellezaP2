import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FormCitaHijoComponent } from './form-cita-hijo.component';

describe('FormCitaHijoComponent', () => {
  let component: FormCitaHijoComponent;
  let fixture: ComponentFixture<FormCitaHijoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [FormCitaHijoComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(FormCitaHijoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
