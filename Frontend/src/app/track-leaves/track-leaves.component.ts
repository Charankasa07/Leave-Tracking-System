import { Component, OnInit } from '@angular/core';
import { UserRegister, Leave } from '../User';
import { faPenToSquare, faTrashCan } from '@fortawesome/free-solid-svg-icons';
import { BackendService } from '../backend.service';

@Component({
  selector: 'app-track-leaves',
  templateUrl: './track-leaves.component.html',
  styleUrls: ['./track-leaves.component.css'],
})
export class TrackLeavesComponent implements OnInit {
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
  deleteIcon = faTrashCan;
  editIcon = faPenToSquare;
  leaves: Leave[] = [];

  ngOnInit(): void {
    const currentUserData = localStorage.getItem('currentUser');
    if (currentUserData) {
      this.currentUser = JSON.parse(currentUserData);
    }else{
      window.location.href='http://localhost:4200/login'
    }

    this.backend.getAllLeaves(this.currentUser.email).subscribe((res)=>{
      this.leaves = res.data
    })
    
  }
  delete(id: Number) {
    this.backend.deleteLeave(id).subscribe((res)=>console.log(res))
    window.location.reload();
  }
}
