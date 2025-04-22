import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BotonAusenteComponent } from './boton-ausente.component';

describe('BotonAusenteComponent', () => {
  let component: BotonAusenteComponent;
  let fixture: ComponentFixture<BotonAusenteComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [BotonAusenteComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BotonAusenteComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
