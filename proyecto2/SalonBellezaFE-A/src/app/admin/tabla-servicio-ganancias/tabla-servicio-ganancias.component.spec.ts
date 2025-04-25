import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TablaServicioGananciasComponent } from './tabla-servicio-ganancias.component';

describe('TablaServicioGananciasComponent', () => {
  let component: TablaServicioGananciasComponent;
  let fixture: ComponentFixture<TablaServicioGananciasComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TablaServicioGananciasComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TablaServicioGananciasComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
