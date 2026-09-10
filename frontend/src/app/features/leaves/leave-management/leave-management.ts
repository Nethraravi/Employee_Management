import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { LeaveService } from '../../../core/services/leave.service';
import { LeaveResponse } from '../../../core/models/leave-response';

@Component({
  selector: 'app-leave-management',
  imports: [CommonModule, FormsModule],
  templateUrl: './leave-management.html',
  styleUrl: './leave-management.css'
})
export class LeaveManagement implements OnInit {

  leaves: LeaveResponse[] = [];

  leaveId = '';
  employeeId = '';
  status = '';

  errorMessage = '';
  successMessage = '';
  isLoading = false;

  constructor(private leaveService: LeaveService) {}

  ngOnInit(): void {
    this.loadLeaves();
  }

  loadLeaves(): void {
    this.isLoading = true;
    this.errorMessage = '';

    // Search by Leave ID
    if (this.leaveId) {
      this.leaveService.getLeaveById(Number(this.leaveId)).subscribe({
        next: (response: any) => {
          this.leaves = [response.data];
          this.isLoading = false;
        },
        error: (error: any) => {
          console.log('Failed to find leave:', error);
          this.leaves = [];
          this.errorMessage = error.error?.message || 'Leave not found';
          this.isLoading = false;
        }
      });

      return;
    }

    // Search by Employee ID
    if (this.employeeId) {
      this.leaveService.getLeavesByEmployee(Number(this.employeeId)).subscribe({
        next: (response: any) => {
          this.leaves = response.data;
          this.isLoading = false;
        },
        error: (error: any) => {
          console.log('Failed to load employee leaves:', error);
          this.leaves = [];
          this.errorMessage =
            error.error?.message || 'Failed to load employee leaves';
          this.isLoading = false;
        }
      });

      return;
    }

    // Filter by Status
    if (this.status) {
      this.leaveService.getLeavesByStatus(this.status).subscribe({
        next: (response: any) => {
          this.leaves = response.data;
          this.isLoading = false;
        },
        error: (error: any) => {
          console.log('Failed to load leaves by status:', error);
          this.leaves = [];
          this.errorMessage =
            error.error?.message || 'Failed to load leaves';
          this.isLoading = false;
        }
      });

      return;
    }

    // No filters → get all leaves
    this.leaveService.getAllLeaves().subscribe({
      next: (response: any) => {
        this.leaves = response.data;
        this.isLoading = false;
      },
      error: (error: any) => {
        console.log('Failed to load leaves:', error);
        this.leaves = [];
        this.errorMessage =
          error.error?.message || 'Failed to load leaves';
        this.isLoading = false;
      }
    });
  }

  updateStatus(
    id: number,
    status: string
  ): void {

    this.errorMessage = '';
    this.successMessage = '';

    this.leaveService
      .updateLeaveStatus(id, status)
      .subscribe({

        next: () => {

          this.successMessage =
            `Leave ${status.toLowerCase()} successfully`;

          this.loadLeaves();

          setTimeout(() => {
            this.successMessage = '';
          }, 1500);
        },

        error: (error: any) => {

          console.log(
            'Failed to update leave status:',
            error
          );

          this.errorMessage =
            error.error?.message ||
            'Failed to update leave status';
        }
      });
  }

  resetFilters(): void {
    this.leaveId = '';
    this.employeeId = '';
    this.status = '';

    this.loadLeaves();
  }
}
