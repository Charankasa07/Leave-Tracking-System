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
  isFetched=true;
  ngOnInit(): void {
    
    this.isFetched = true
    this.backend.getOverviewLeaves().subscribe((res)=>{
      console.log(res.data)
      this.allLeaves = res.data;
      this.allLeaves = this.allLeaves.filter((leave)=> leave.status !=='pending');
      this.isFetched = false
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
