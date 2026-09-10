import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../../../core/services/auth.service';

@Component({
  selector: 'app-change-password',
  imports: [FormsModule],
  templateUrl: './change-password.html',
  styleUrl: './change-password.css'
})
export class ChangePassword {

  currentPassword = '';
  newPassword = '';
  confirmPassword = '';

  errorMessage = '';
  successMessage = '';

  constructor(
    private authService: AuthService,
    private router: Router
  ) {}

  changePassword(): void {

    this.errorMessage = '';
    this.successMessage = '';

    if (this.newPassword !== this.confirmPassword) {
      this.errorMessage = 'New passwords do not match';
      return;
    }

    this.authService.changePassword(
      this.currentPassword,
      this.newPassword
    ).subscribe({
      next: () => {

        this.successMessage = 'Password changed successfully';

        setTimeout(() => {
          this.router.navigate(['/dashboard']);
        }, 1000);
      },

      error: (error) => {
        this.errorMessage =
          error.error?.message || 'Failed to change password';
      }
    });
  }
}
