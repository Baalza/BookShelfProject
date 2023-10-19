import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DataGridCompanyComponent } from './data-grid-company.component';

describe('DataGridCompanyComponent', () => {
  let component: DataGridCompanyComponent;
  let fixture: ComponentFixture<DataGridCompanyComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [DataGridCompanyComponent]
    });
    fixture = TestBed.createComponent(DataGridCompanyComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
