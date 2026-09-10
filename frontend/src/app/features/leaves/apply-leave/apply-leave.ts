import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';

import { LeaveService } from '../../../core/services/leave.service';
import { LeaveRequest } from '../../../core/models/leave-request';

@Component({
  selector: 'app-apply-leave',
  imports: [FormsModule, CommonModule],
  templateUrl: './apply-leave.html',
  styleUrl: './apply-leave.css'
})
export class ApplyLeave {

  leaveType = '';
  startDate = '';
  endDate = '';
  reason = '';

  errorMessage = '';
  successMessage = '';

  constructor(
    private leaveService: LeaveService,
    private router: Router
  ) {}

  applyLeave(): void {

    this.errorMessage = '';
    this.successMessage = '';

    const request: LeaveRequest = {
      leaveType: this.leaveType,
      startDate: this.startDate,
      endDate: this.endDate,
      reason: this.reason
    };

    this.leaveService.applyLeave(request).subscribe({
      next: () => {
        this.successMessage = 'Leave applied successfully';

        this.leaveType = '';
        this.startDate = '';
        this.endDate = '';
        this.reason = '';

        setTimeout(() => {
          this.successMessage = '';
        }, 1500);
      },

      error: (error: any) => {
        console.log('Leave application failed:', error);
        this.errorMessage =
          error.error?.message || 'Failed to apply leave';
      }
    });
  }
}
