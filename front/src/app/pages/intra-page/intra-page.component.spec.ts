import { ComponentFixture, TestBed } from '@angular/core/testing';

import { IntraPageComponent } from './intra-page.component';

describe('IntraPageComponent', () => {
  let component: IntraPageComponent;
  let fixture: ComponentFixture<IntraPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ IntraPageComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(IntraPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
