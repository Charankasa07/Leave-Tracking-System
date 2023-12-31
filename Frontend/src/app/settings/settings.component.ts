import { Component, OnInit } from '@angular/core';
import { UserRegister } from '../User';
import { BackendService } from '../backend.service';
import { NzMessageService } from 'ng-zorro-antd/message';

@Component({
  selector: 'app-settings',
  templateUrl: './settings.component.html',
  styleUrls: ['./settings.component.css'],
})
export class SettingsComponent implements OnInit {
  constructor(private backend : BackendService,private message : NzMessageService){}
  oldNumberOfLeaves: number = 0;
  numberOfLeaves!: number;
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
    const currentUserData = localStorage.getItem('currentUser');
    if (currentUserData) {
      this.currentUser= JSON.parse(currentUserData)
      this.oldNumberOfLeaves = this.currentUser.numberOfLeaves
    } else {
      window.location.href = 'http://localhost:4200/login';
    }
  }
  onSubmit() {
    this.currentUser.numberOfLeaves = this.numberOfLeaves
    localStorage.setItem("currentUser",JSON.stringify(this.currentUser));
    this.backend.updateLeaveCount(this.numberOfLeaves).subscribe((res)=>{
      if(res.statusCode === "OK"){
        this.message.success(res.message,{nzDuration:1500})
        setTimeout(()=>{
          window.location.href = 'http://localhost:4200/manager/new-requests';
        },1500)
      }
      else{
        this.message.error(res.message,{nzDuration:2500})
      }
    })
  }
}
