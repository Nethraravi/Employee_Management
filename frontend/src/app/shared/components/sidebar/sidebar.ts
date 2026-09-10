import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';

import { MatListModule } from '@angular/material/list';
import { MatIconModule } from '@angular/material/icon';
import {CommonModule} from '@angular/common';

import { AuthService } from '../../../core/services/auth.service';

@Component({
  standalone: true,
  selector: 'app-sidebar',
  imports: [CommonModule, RouterLink,MatListModule,MatIconModule],
  templateUrl: './sidebar.html',
  styleUrl: './sidebar.css',
})
export class Sidebar {
  isLeaveMenuOpen = false;

  constructor(public authService: AuthService) {
  }

  toggleLeaveMenu(): void {
    this.isLeaveMenuOpen = !this.isLeaveMenuOpen;
  }
}
