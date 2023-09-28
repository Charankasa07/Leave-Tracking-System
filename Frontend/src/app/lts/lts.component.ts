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
    }
  }

  logout() {
    //removing user from cookie storage as he/she is logged out
    localStorage.removeItem('currentUser')
    window.location.href = 'http://localhost:4200/';
  }
}
