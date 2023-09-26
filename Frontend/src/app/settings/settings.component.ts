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
  users: UserRegister[] = [];
  ngOnInit(): void {
    const currentUserData = localStorage.getItem('currentUser');
    if (currentUserData) {
      console.log('hi');
    } else {
      window.location.href = 'http://localhost:4200/login';
    }
  }
  onSubmit() {
    this.backend.updateLeaveCount(this.numberOfLeaves).subscribe((res)=>console.log(res))
    this.message.success("Leave Count Updated Successfully",{nzDuration:1500})
    setTimeout(()=>{
      window.location.href = 'http://localhost:4200/manager/new-requests';
    },1500)
  }
}
