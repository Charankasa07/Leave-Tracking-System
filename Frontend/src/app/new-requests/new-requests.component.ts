import { Component, OnInit } from '@angular/core';
import { faCheck, faXmark } from '@fortawesome/free-solid-svg-icons';
import { Leave, UserRegister } from '../User';
import { BackendService } from '../backend.service';

@Component({
  selector: 'app-new-requests',
  templateUrl: './new-requests.component.html',
  styleUrls: ['./new-requests.component.css'],
})
export class NewRequestsComponent implements OnInit {
  constructor(private backend : BackendService){}
  isVisible = false;
  flag : boolean = true;
  id !: Number ;

  showModal(flag : boolean , id : Number): void {
    this.isVisible = true;
    this.flag = flag
    this.id = id
  }
  
  handleOk(): void {
    if(this.flag){
      this.accept(this.id);
    }
    else{
      this.reject(this.id);
    }
    this.isVisible = false;
  }

  handleCancel(): void {
    this.isVisible = false;
  }
  
  leaves: Leave[] = [];
  managerMessage: string = '';
  acceptIcon = faCheck;
  rejectIcon = faXmark;
  ngOnInit(): void {

    this.backend.getNewRequests().subscribe((res)=>{
      this.leaves = res.data
    })

    const currentUserData = localStorage.getItem('currentUser')
    if(currentUserData){
      console.log("hi");
    }
    else{
      window.location.href='http://localhost:4200/login'
    }
    
  }
  //function for accepting the leave based on the leave id
  accept(id: Number) {
      this.backend.reactToLeave(id,'accepted',this.managerMessage).subscribe((res)=>console.log(res))
      window.location.reload()
  }
  //function for rejecting a leave based on the leave id
  reject(id: Number) {
    this.backend.reactToLeave(id,'rejected',this.managerMessage).subscribe((res)=>console.log(res))
    window.location.reload()
  }
}
