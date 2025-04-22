import { ComponentFixture, TestBed } from '@angular/core/testing';

import { VistaCitasComponent } from './vista-citas.component';

describe('VistaCitasComponent', () => {
  let component: VistaCitasComponent;
  let fixture: ComponentFixture<VistaCitasComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [VistaCitasComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(VistaCitasComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
