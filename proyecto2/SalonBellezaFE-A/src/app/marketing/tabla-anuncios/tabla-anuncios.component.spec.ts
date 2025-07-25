import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TablaAnunciosComponent } from './tabla-anuncios.component';

describe('TablaAnunciosComponent', () => {
  let component: TablaAnunciosComponent;
  let fixture: ComponentFixture<TablaAnunciosComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TablaAnunciosComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TablaAnunciosComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
