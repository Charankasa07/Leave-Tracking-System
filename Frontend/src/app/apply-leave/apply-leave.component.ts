import { Component, OnInit } from '@angular/core';
import { v4 as uuid } from 'uuid';
import { Location } from '@angular/common';
import { Leave, UserRegister } from '../User';
import { BackendService } from '../backend.service';
import { NzMessageService } from 'ng-zorro-antd/message';

@Component({
  selector: 'app-apply-leave',
  templateUrl: './apply-leave.component.html',
  styleUrls: ['./apply-leave.component.css'],
})
export class ApplyLeaveComponent implements OnInit {
  constructor(
    private location: Location,
    private backend : BackendService,
    private message : NzMessageService
  ) {}
  leaves: Leave[] = [];
  users: UserRegister[] = [];
  currentUser: UserRegister = {
    role: '',
    name: '',
    phone: '',
    email: '',
    password: '',
    numberOfLeaves: 0,
    remainingLeaves:0
  };
  leave: Leave = {
    id: 0,
    name: '',
    email:'',
    type: 'sick leave',
    startDate:'',
    endDate: '',
    reason: '',
    status: 'pending',
    message: '',
  };
  //function for applying a leave
  onApplyLeave() {
       //converting the startDate of the leave to the ISO String format
      const startDate = new Date(this.leave.startDate).toISOString();
      //comparing whether the startdate is greater than the current date or not
      if (startDate >= new Date().toISOString()) {
        //converting the endDate of the leave to the ISO String format
        const endDate = new Date(this.leave.endDate).toISOString();
         //comparing whether the endDate is greater than the startDate or not
        if (endDate >= startDate) {
            this.leave.email = this.currentUser.email;
            this.leave.name = this.currentUser.name;
            this.backend.applyLeave(this.leave).subscribe((res)=>console.log(res))
            this.message.success("Leave Applied Successfully",{nzDuration:1500})
            setTimeout(()=>{
              window.location.href="http://localhost:4200/employee/track-leaves"
            },1500)
        } else {
          //displayin error message if the endDate is not greater than startDate
          this.message.error("End Date must be greater than Start Date",{nzDuration:1500})
        }
      } else {
        //displaying error message if the startDate is not greater than the current Date
        this.message.error("Start Date must be greater than Today's Date",{nzDuration:1500})
      }
  }
  ngOnInit(): void {
    const currentUserData = localStorage.getItem('currentUser');
    if(currentUserData){
      this.currentUser = JSON.parse(currentUserData)
    }else{
      window.location.href='http://localhost:4200/login'
    }
  }
}
