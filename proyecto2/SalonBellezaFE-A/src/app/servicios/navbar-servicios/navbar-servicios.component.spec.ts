import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NavbarServiciosComponent } from './navbar-servicios.component';

describe('NavbarServiciosComponent', () => {
  let component: NavbarServiciosComponent;
  let fixture: ComponentFixture<NavbarServiciosComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [NavbarServiciosComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(NavbarServiciosComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
