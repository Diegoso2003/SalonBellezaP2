import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ImagenServicioComponent } from './imagen-servicio.component';

describe('ImagenServicioComponent', () => {
  let component: ImagenServicioComponent;
  let fixture: ComponentFixture<ImagenServicioComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ImagenServicioComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ImagenServicioComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
