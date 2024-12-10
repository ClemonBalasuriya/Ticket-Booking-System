import { TestBed } from '@angular/core/testing';

import { RelesingService } from './relesing.service';

describe('RelesingService', () => {
  let service: RelesingService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(RelesingService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
