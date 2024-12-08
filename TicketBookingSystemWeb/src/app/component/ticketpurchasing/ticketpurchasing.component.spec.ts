import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TicketpurchasingComponent } from './ticketpurchasing.component';

describe('TicketpurchasingComponent', () => {
  let component: TicketpurchasingComponent;
  let fixture: ComponentFixture<TicketpurchasingComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TicketpurchasingComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TicketpurchasingComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
