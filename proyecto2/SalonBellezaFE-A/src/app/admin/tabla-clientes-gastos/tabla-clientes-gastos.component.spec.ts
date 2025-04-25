import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TablaClientesGastosComponent } from './tabla-clientes-gastos.component';

describe('TablaClientesGastosComponent', () => {
  let component: TablaClientesGastosComponent;
  let fixture: ComponentFixture<TablaClientesGastosComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TablaClientesGastosComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TablaClientesGastosComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
