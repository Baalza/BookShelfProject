import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ModalOwComponent } from './modal-ow.component';

describe('ModalOwComponent', () => {
  let component: ModalOwComponent;
  let fixture: ComponentFixture<ModalOwComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ModalOwComponent]
    });
    fixture = TestBed.createComponent(ModalOwComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
