import { ComponentFixture, TestBed } from '@angular/core/testing';

import { VistaAnuncioComponent } from './vista-anuncio.component';

describe('VistaAnuncioComponent', () => {
  let component: VistaAnuncioComponent;
  let fixture: ComponentFixture<VistaAnuncioComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [VistaAnuncioComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(VistaAnuncioComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
