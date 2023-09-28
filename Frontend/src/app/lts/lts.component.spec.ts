import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LtsComponent } from './lts.component';

describe('LtsComponent', () => {
  let component: LtsComponent;
  let fixture: ComponentFixture<LtsComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [LtsComponent]
    });
    fixture = TestBed.createComponent(LtsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
