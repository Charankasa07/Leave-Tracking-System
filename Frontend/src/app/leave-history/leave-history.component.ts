import { Component, OnInit } from '@angular/core';
import { ignoreElements } from 'rxjs';
import { Leave, UserRegister } from '../User';
import { BackendService } from '../backend.service';

@Component({
  selector: 'app-leave-history',
  templateUrl: './leave-history.component.html',
  styleUrls: ['./leave-history.component.css'],
})
export class LeaveHistoryComponent implements OnInit {
  constructor(private backend : BackendService){}
  currentUser: UserRegister = {
    role: '',
    name: '',
    phone: '',
    email: '',
    password: '',
    numberOfLeaves: 0,
    remainingLeaves:0
  };
  AcceptedLeaves!:Number ;
  RejectedLeaves!:Number ;
  PendingLeaves!:Number ;
  leaves : Leave[]=[]
  numberOfLeaves = 0;
  ngOnInit(): void {
    const currentUserData = localStorage.getItem('currentUser');
    if(currentUserData){
      this.currentUser = JSON.parse(currentUserData)
    }else{
      window.location.href='http://localhost:4200/login'
    }
   
    this.backend.getAllLeaves(this.currentUser.email).subscribe((res)=>{
      this.leaves = res.data
      this.AcceptedLeaves = this.leaves.filter(
        (leave) => leave.status === 'accepted'
      ).length;
      console.log(this.AcceptedLeaves);
      
      this.RejectedLeaves = this.leaves.filter(
        (leave) => leave.status === 'rejected'
      ).length;
      console.log(this.RejectedLeaves);
      
      this.PendingLeaves = this.leaves.filter(
        (leave) => leave.status === 'pending'
      ).length;
      console.log(this.PendingLeaves);
    })
 
  }
}