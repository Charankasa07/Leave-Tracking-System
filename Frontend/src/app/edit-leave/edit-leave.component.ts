import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, TitleStrategy } from '@angular/router';
import { Leave, UserRegister } from '../User';
import { BackendService } from '../backend.service';

@Component({
  selector: 'app-edit-leave',
  templateUrl: './edit-leave.component.html',
  styleUrls: ['./edit-leave.component.css'],
})
export class EditLeaveComponent implements OnInit {
  constructor(
    private route: ActivatedRoute,
    private backend : BackendService
  ) {}
  leaves: Leave[] = [];
  leaveId! : number;
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
    name: this.currentUser.name,
    email:this.currentUser.email,
    type: '',
    startDate: '',
    endDate: '',
    reason: '',
    status: '',
    message: '',
  };
  message = '';
  displayMessage = false;
  ngOnInit(): void {
    //taking the param from the route
    this.leaveId = this.route.snapshot.params['id'];

    this.backend.getLeave(this.leaveId).subscribe((res)=> {
      this.leave = res.data
    })
    
    const currentUserData = localStorage.getItem('currentUser');
    //getting the current user data
    if (currentUserData) {
      this.currentUser = JSON.parse(currentUserData);
    }else{
      window.location.href='http://localhost:4200/login'
    }
  }
  //function for saving the changes
  save() {
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
      } else {
        //displayin error message if the endDate is not greater than startDate
        this.message =
          'End Date must be Start Date or greater than Start Date';
        this.displayMessage = true;
        setTimeout(() => (this.displayMessage = false), 2000);
      }
    } else {
      //displaying error message if the startDate is not greater than the current Date
      this.message = 'Start Date must be from Today or greater';
      this.displayMessage = true;
      setTimeout(() => (this.displayMessage = false), 2000);
    }
  }
}
