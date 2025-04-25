import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TablaClienteCitasComponent } from './tabla-cliente-citas.component';

describe('TablaClienteCitasComponent', () => {
  let component: TablaClienteCitasComponent;
  let fixture: ComponentFixture<TablaClienteCitasComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TablaClienteCitasComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TablaClienteCitasComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
