import { Component, OnInit } from '@angular/core';
import { UserRegister } from '../User';

@Component({
  selector: 'app-lts',
  templateUrl: './lts.component.html',
  styleUrls: ['./lts.component.css']
})
export class LtsComponent implements OnInit{
  currentUser: UserRegister = {
    role: '',
    name: '',
    phone: '',
    email: '',
    password: '',
    numberOfLeaves: 0,
    remainingLeaves:0
  };
  ngOnInit(): void {
    const currentUserData = localStorage.getItem("currentUser")
    if(currentUserData){
      this.currentUser = JSON.parse(currentUserData)
      if (this.currentUser.role === 'employee') {
        window.location.href = 'http://localhost:4200/employee';
      } else {
        window.location.href = 'http://localhost:4200/manager';
      }
    }

  }
}
