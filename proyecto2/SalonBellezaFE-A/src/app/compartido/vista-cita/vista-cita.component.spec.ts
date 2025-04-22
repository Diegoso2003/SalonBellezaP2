import { ComponentFixture, TestBed } from '@angular/core/testing';

import { VistaCitaComponent } from './vista-cita.component';

describe('VistaCitaComponent', () => {
  let component: VistaCitaComponent;
  let fixture: ComponentFixture<VistaCitaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [VistaCitaComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(VistaCitaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
