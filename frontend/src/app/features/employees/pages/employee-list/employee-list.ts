import { Component, OnInit} from '@angular/core';
import { EmployeeService } from '../../../../core/services/employee.service';
@Component({
  standalone: true,
  selector: 'app-employee-list',
  imports: [],
  templateUrl: './employee-list.html',
  styleUrl: './employee-list.css',
})
export class EmployeeList implements OnInit{
  employees: any[] = [];
  constructor(private employeeService: EmployeeService)
  {

  }
  ngOnInit() {
    this.employeeService.getAllEmployees(0,5,'id','asc', 'name','department').subscribe(response =>
    {
      console.log(response);
    });
    // console.log("Employee List Loaded");
  }
}
