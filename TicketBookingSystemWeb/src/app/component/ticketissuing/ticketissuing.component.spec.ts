import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TicketissuingComponent } from './ticketissuing.component';

describe('TicketissuingComponent', () => {
  let component: TicketissuingComponent;
  let fixture: ComponentFixture<TicketissuingComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TicketissuingComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TicketissuingComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
