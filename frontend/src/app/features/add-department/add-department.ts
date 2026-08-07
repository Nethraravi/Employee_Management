import { Component } from '@angular/core';
import { FormsModule} from '@angular/forms';
import {CreateDepartmentRequest} from '../../core/models/create-department-request';
import { Router} from '@angular/router';
import { DepartmentService} from '../../core/services/department.service';
import { OnInit} from '@angular/core';
import { ActivatedRoute} from '@angular/router';
import {NotificationService} from '../../core/services/notification.service';

@Component({
  standalone: true,
  selector: 'app-add-department',
  imports: [FormsModule],
  templateUrl: './add-department.html',
  styleUrl: './add-department.css',
})
export class AddDepartment implements OnInit{
  department: CreateDepartmentRequest = {
    name: ''
  };

  constructor(private departmentService: DepartmentService,
              private notificationService: NotificationService,
              private router: Router,
              private route: ActivatedRoute) {
  }

  ngOnInit() {
    const id = this.route.snapshot.paramMap.get('id');
    if(id)
    {
      this.departmentId = Number(id);
      this.isEditMode = true;
      console.log("Editing Department:",this.departmentId);

      this.departmentService.getDepartmentById(this.departmentId).subscribe({
        next: (response) => {
          this.department.name=response.data.name;
        },
        error: (error) => {
          console.error(error);
        }
      });
    }
  }

  departmentId?: number;
  isEditMode = false;

  saveDepartment(): void
  {
    if(this.isEditMode)
    {
      this.departmentService.updateDepartment(this.departmentId!, this.department).subscribe({
        next: (response) => {
          //alert(response.message);
          this.notificationService.showSuccess(response.message);
          this.router.navigate(['/departments']);
        },
        error: (error) => {
          console.error(error);
          this.notificationService.showApiError(error);
        }
      });
    }
    else
    {
      this.departmentService.createDepartment(this.department).subscribe({
        next: (response) => {
          //alert(response.message);
          this.notificationService.showSuccess(response.message);
          this.router.navigate(['/departments']);
        },
        error: (error) =>
        {
          console.error(error);
          this.notificationService.showApiError(error);
        }
      });
    }
  }
}
