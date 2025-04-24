import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BotonImportarComponent } from './boton-importar.component';

describe('BotonImportarComponent', () => {
  let component: BotonImportarComponent;
  let fixture: ComponentFixture<BotonImportarComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [BotonImportarComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BotonImportarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
