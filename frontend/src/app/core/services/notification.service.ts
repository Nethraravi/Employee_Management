import { Injectable } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';

@Injectable({
  providedIn: 'root'
})

export class NotificationService
{
  constructor(private snackBar: MatSnackBar) {
  }

  showSuccess(message: string): void
  {
    this.snackBar.open(message, 'Close', {duration:3000});
  }

  showError(message: string): void
  {
    this.snackBar.open(message, 'Close', {duration: 3000});
  }

  showApiError(error: any): void
  {
    if(typeof error.error === 'string')
    {
      this.showError(error.error);
    }
    else
    {
      const messages = Object.values(error.error);
      this.showError(messages.join('\n'));
    }
  }
}
