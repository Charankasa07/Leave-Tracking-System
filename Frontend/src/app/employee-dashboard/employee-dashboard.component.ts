import { Component, OnInit } from '@angular/core';
import { UserRegister } from '../User';

@Component({
  selector: 'app-employee-dashboard',
  templateUrl: './employee-dashboard.component.html',
  styleUrls: ['./employee-dashboard.component.css'],
})
export class EmployeeDashboardComponent implements OnInit {
  currentUser: UserRegister = {
    role: '',
    name: '',
    phone: '',
    email: '',
    password: '',
    numberOfLeaves: 0,
    remainingLeaves:0,
  };
  ngOnInit(): void {
    const currentUserData = localStorage.getItem('currentUser');
    if (currentUserData) {
      //getting the data from the cookie storage
      this.currentUser = JSON.parse(currentUserData);
    }else{
      window.location.href='http://localhost:4200/login'
    }
  }
}
