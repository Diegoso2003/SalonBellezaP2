import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FormClienteGastosComponent } from './form-cliente-gastos.component';

describe('FormClienteGastosComponent', () => {
  let component: FormClienteGastosComponent;
  let fixture: ComponentFixture<FormClienteGastosComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [FormClienteGastosComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(FormClienteGastosComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
