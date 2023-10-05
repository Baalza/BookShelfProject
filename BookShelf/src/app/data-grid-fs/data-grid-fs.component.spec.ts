import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DataGridFsComponent } from './data-grid-fs.component';

describe('DataGridFsComponent', () => {
  let component: DataGridFsComponent;
  let fixture: ComponentFixture<DataGridFsComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [DataGridFsComponent]
    });
    fixture = TestBed.createComponent(DataGridFsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
