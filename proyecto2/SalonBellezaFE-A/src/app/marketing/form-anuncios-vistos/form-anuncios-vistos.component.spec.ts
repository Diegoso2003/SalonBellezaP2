import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FormAnunciosVistosComponent } from './form-anuncios-vistos.component';

describe('FormAnunciosVistosComponent', () => {
  let component: FormAnunciosVistosComponent;
  let fixture: ComponentFixture<FormAnunciosVistosComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [FormAnunciosVistosComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(FormAnunciosVistosComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
