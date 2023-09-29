import { Component, OnInit } from '@angular/core';
import { UserRegister, Leave } from '../User';
import { faPenToSquare, faTrashCan } from '@fortawesome/free-solid-svg-icons';
import { BackendService } from '../backend.service';
import { NzMessageService } from 'ng-zorro-antd/message';
import { DatePipe } from '@angular/common';

@Component({
  selector: 'app-track-leaves',
  templateUrl: './track-leaves.component.html',
  styleUrls: ['./track-leaves.component.css'],
})
export class TrackLeavesComponent implements OnInit {
  constructor(private backend : BackendService,private message : NzMessageService){}
  currentUser: UserRegister = {
    role: '',
    name: '',
    phone: '',
    email: '',
    password: '',
    numberOfLeaves: 0,
    remainingLeaves:0
  };
  deleteIcon = faTrashCan;
  editIcon = faPenToSquare;
  allLeaves: Leave[] = [];
  filteredLeaves : Leave[]=[];
  searchInput : string ='';
  isFetched : boolean =false;

  ngOnInit(): void {
    const currentUserData = localStorage.getItem('currentUser');
    if (currentUserData) {
      this.currentUser = JSON.parse(currentUserData);
    }else{
      window.location.href='http://localhost:4200/login'
    }
    this.isFetched=false
    this.backend.getAllLeaves(this.currentUser.email).subscribe((res)=>{
      this.allLeaves = res.data
      this.filteredLeaves = this.allLeaves
      setTimeout(()=>{
        this.isFetched=true
      },500)
    })
    
  }
  delete(id: Number) {
    this.backend.deleteLeave(id).subscribe((res)=>console.log(res))
    this.message.success("Leave Deleted Successfully",{nzDuration:1500})
    setTimeout(()=>{
      window.location.reload()
    },1500)
  }
  search(){
    this.filteredLeaves = this.allLeaves.filter((leave)=> {
        let combinedLeave =leave.type + leave.endDate + leave.startDate + leave.status
        return combinedLeave.toLowerCase().includes(this.searchInput.toLowerCase())
    })
  }
}
