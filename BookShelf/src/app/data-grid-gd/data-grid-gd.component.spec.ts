import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DataGridGdComponent } from './data-grid-gd.component';

describe('DataGridGdComponent', () => {
  let component: DataGridGdComponent;
  let fixture: ComponentFixture<DataGridGdComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [DataGridGdComponent]
    });
    fixture = TestBed.createComponent(DataGridGdComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
