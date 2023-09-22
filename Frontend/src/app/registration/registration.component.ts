import { Component, OnInit } from '@angular/core';
import { UserRegister } from '../User';
import { BackendService } from '../backend.service';

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.css'],
})
export class RegistrationComponent implements OnInit {
  constructor(private backend: BackendService) { }
  displayMessage: boolean = false;
  message = '';
  user: UserRegister = {
    role: 'employee',
    name: '',
    phone: '',
    email: '',
    password: '',
    numberOfLeaves: 0,
    remainingLeaves:0
  };
  
  onRegister() {

    this.backend.onRegister(this.user).subscribe((res) => {
      console.log(res)
      if (res.statusCode !== "CREATED") {
        this.message = res.message
        this.displayMessage = true
        setTimeout(() => { this.displayMessage = false }, 2000)
      }
      else{
        localStorage.setItem('currentUser',JSON.stringify(res.data))
        if(res.data.role === 'employee'){
          window.location.href = 'http://localhost:4200/employee';
        }else{
          window.location.href = 'http://localhost:4200/manager';
        }
      }
    });

  }
  ngOnInit(): void {
    const userData = localStorage.getItem('currentUser');
    if (userData) {
      let user: UserRegister = JSON.parse(userData);
      //if there is already user data in the cookie storage
      //then redirect them to respective dashboards
      if (user.role === 'employee') {
        window.location.href = 'http://localhost:4200/employee';
      } else {
        window.location.href = 'http://localhost:4200/manager';
      }
    }
  }
}
