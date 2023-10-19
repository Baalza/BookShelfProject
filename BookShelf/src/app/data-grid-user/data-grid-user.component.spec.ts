import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DataGridUserComponent } from './data-grid-user.component';

describe('DataGridUserComponent', () => {
  let component: DataGridUserComponent;
  let fixture: ComponentFixture<DataGridUserComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [DataGridUserComponent]
    });
    fixture = TestBed.createComponent(DataGridUserComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
