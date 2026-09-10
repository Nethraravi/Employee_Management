import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';

import { LeaveService } from '../../../core/services/leave.service';
import { LeaveResponse } from '../../../core/models/leave-response';

@Component({
  selector: 'app-my-leaves',
  imports: [CommonModule],
  templateUrl: './my-leaves.html',
  styleUrl: './my-leaves.css'
})
export class MyLeaves implements OnInit {

  leaves: LeaveResponse[] = [];
  errorMessage = '';
  isLoading = false;

  constructor(private leaveService: LeaveService) {}

  ngOnInit(): void {
    this.loadMyLeaves();
  }

  loadMyLeaves(): void {

    this.isLoading = true;
    this.errorMessage = '';

    this.leaveService.getMyLeaves().subscribe({

      next: (response: any) => {
        this.leaves = response.data;
        this.isLoading = false;
      },

      error: (error: any) => {
        console.log('Failed to load leaves:', error);

        this.errorMessage =
          error.error?.message || 'Failed to load leave history';

        this.isLoading = false;
      }
    });
  }
}
