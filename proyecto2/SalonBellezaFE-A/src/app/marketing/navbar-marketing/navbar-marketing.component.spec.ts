import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NavbarMarketingComponent } from './navbar-marketing.component';

describe('NavbarMarketingComponent', () => {
  let component: NavbarMarketingComponent;
  let fixture: ComponentFixture<NavbarMarketingComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [NavbarMarketingComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(NavbarMarketingComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
