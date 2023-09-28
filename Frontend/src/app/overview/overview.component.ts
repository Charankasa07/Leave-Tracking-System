import { Component, OnInit } from '@angular/core';
import { Leave } from '../User';
import { BackendService } from '../backend.service';

@Component({
  selector: 'app-overview',
  templateUrl: './overview.component.html',
  styleUrls: ['./overview.component.css'],
})
export class OverviewComponent implements OnInit {
  constructor(private backend : BackendService){}
  allLeaves: Leave[] = [];
  isFetched=false;
  ngOnInit(): void {
    
    this.isFetched = false
    this.backend.getOverviewLeaves().subscribe((res)=>{
      console.log(res.data)
      this.allLeaves = res.data;
      this.allLeaves = this.allLeaves.filter((leave)=> leave.status !=='pending');
      setTimeout(()=>{
        this.isFetched=true
      },500)
    });

    const currentUserData = localStorage.getItem('currentUser')
    if(currentUserData){
      console.log("hi");
    }
    else{
      window.location.href='http://localhost:4200/login'
    }
    
  }
}
