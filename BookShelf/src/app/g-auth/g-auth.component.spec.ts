import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GAuthComponent } from './g-auth.component';

describe('GAuthComponent', () => {
  let component: GAuthComponent;
  let fixture: ComponentFixture<GAuthComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [GAuthComponent]
    });
    fixture = TestBed.createComponent(GAuthComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
