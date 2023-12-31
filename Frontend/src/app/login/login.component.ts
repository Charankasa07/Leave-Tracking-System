import { Component, OnInit } from '@angular/core';
import { UserLogin, UserRegister } from '../User';
import { BackendService } from '../backend.service';
import { NzMessageService } from 'ng-zorro-antd/message';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
})
export class LoginComponent implements OnInit {
  constructor(private backend : BackendService,private message : NzMessageService){}
  currentUser: UserLogin = {
    email: '',
    password: '',
  };
  
  onLogin(){
      let statusCode !:Number; 
      this.backend.onLogin(this.currentUser).subscribe((res) => {
        console.log(res)
        if(res.statusCode !=='OK'){
          this.message.error(res.message,{nzDuration:2000})
        }
        else{
          localStorage.setItem('currentUser',JSON.stringify(res.data))
          if(res.data.role === 'employee'){
            window.location.href = 'http://localhost:4200/employee';
          }
          else{
            window.location.href = 'http://localhost:4200/manager';
          }
        }
      });
  }
  ngOnInit(): void {

    const user = localStorage.getItem('currentUser');
    if (user) {
      let userData: UserRegister = JSON.parse(user);
      //if there is already user data in the cookie storage
      //then redirect them to respective dashboards
      if (userData.role === 'employee') {
        window.location.href = 'http://localhost:4200/employee';
      } else {
        window.location.href = 'http://localhost:4200/manager';
      }
    }
  }
}
