import { Component } from '@angular/core';
import { FormsModule} from '@angular/forms';
import { CreateEmployeeRequest} from '../../core/models/create-employee-request';
import { Department} from '../../core/models/department';
import { DepartmentService} from '../../core/services/department.service';
import { OnInit } from '@angular/core';
import { EmployeeService} from '../../core/services/employee.service';
import { Router} from '@angular/router';
import { ActivatedRoute} from '@angular/router';
import { CommonModule } from '@angular/common';
import {finalize} from "rxjs";
import {NotificationService} from '../../core/services/notification.service';

@Component({
  standalone: true,
  selector: 'app-add-employee',
  imports: [CommonModule, FormsModule],
  templateUrl: './add-employee.html',
  styleUrl: './add-employee.css'
})

export class AddEmployee implements OnInit{
  constructor(private departmentService: DepartmentService,
              private employeeService: EmployeeService,
              private notificationService: NotificationService,
              private router: Router,
              private route: ActivatedRoute)
  {

  }
  employee: CreateEmployeeRequest = {
    name: '',
    salary: null,
    departmentId: null
  };

  departments: Department[] = [];

  isEditMode = false;
  employeeId=0;
  isSaving = false;

  ngOnInit() {
    this.departmentService.getAllDepartments(0, 100, 'id', 'asc','').subscribe({
      next: (response) => {
        this.departments=response.data.content;
        console.log(this.departments);

        const id = this.route.snapshot.paramMap.get('id');
        console.log(id);

        if(id)
        {
          this.isEditMode=true;
          this.employeeId=+id;

          this.employeeService.getEmployeeById(+id).subscribe({  //In +id the unary + converts string(id="7") to number(id=7)
            next: (response) => {
              this.employee = {
                name: response.data.name,
                salary: response.data.salary,
                departmentId: response.data.departmentId};
            }
          });
        }
      }
    });
  }

  save(): void {
    if(this.isEditMode)
    {
      this.isSaving=true;
      console.log("Saving Started",this.isSaving);
      this.employeeService.UpdateEmployee(this.employeeId, this.employee).pipe(finalize(() => {
        this.isSaving = false;
        console.log("Saving Finished", this.isSaving);
      })).subscribe({
        next: (response) => {
          //alert(response.message);
          this.notificationService.showSuccess(response.message);
          this.router.navigate(['/employees']);
        },
        error: (error) => {
          console.error(error);
          //alert(error.error);
          this.notificationService.showApiError(error);
        }
      });
    }
    else {
      this.isSaving=true;
      console.log("Saving Started",this.isSaving);
      this.employeeService.createEmployee(this.employee).pipe(finalize(() => {
        this.isSaving = false;
        console.log("Saving Finished", this.isSaving);
      })).subscribe({
        next: (response) => {
          console.log(response);
          //alert(response.message);
          this.notificationService.showSuccess(response.message);
          this.router.navigate(['/employees']);
        },
        error: (error) => {
          console.error(error);
          this.notificationService.showApiError(error);
        }
      });
    }
  }
}
