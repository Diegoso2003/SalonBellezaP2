import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FormClienteCitasComponent } from './form-cliente-citas.component';

describe('FormClienteCitasComponent', () => {
  let component: FormClienteCitasComponent;
  let fixture: ComponentFixture<FormClienteCitasComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [FormClienteCitasComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(FormClienteCitasComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
