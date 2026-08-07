import { Component, OnInit } from '@angular/core';
import { DashboardService} from '../../core/services/dashboard.service';
import { Dashboard as DashboardModel } from '../../core/models/dashboard';
import { DecimalPipe} from '@angular/common';

@Component({
  standalone: true,
  selector: 'app-dashboard',
  imports: [DecimalPipe],
  templateUrl: './dashboard.html',
  styleUrls: ['./dashboard.css'],
})
export class Dashboard implements OnInit{
  dashboard!: DashboardModel;
  ngOnInit() {
    this.dashboardService.getDashboard().subscribe({
      next: (response) => {
        this.dashboard=response.data;
        console.log(this.dashboard);
      },
      error: (error) => {
        console.error(error);
      }
    });
  }

  constructor(private dashboardService: DashboardService) {
    console.log("Dashboard Loaded");
  }
}
