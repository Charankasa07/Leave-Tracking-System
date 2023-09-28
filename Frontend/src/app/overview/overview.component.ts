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
  filteredLeaves : Leave[]=[];
  isFetched=false;
  searchInput : string =''
  ngOnInit(): void {
    
    this.isFetched = false
    this.backend.getOverviewLeaves().subscribe((res)=>{
      this.allLeaves = res.data;
      this.filteredLeaves = this.allLeaves.filter((leave)=> leave.status !=='pending');
      setTimeout(()=>{
        this.isFetched=true
      },500)
    });

    const currentUserData = localStorage.getItem('currentUser')
    if(!currentUserData){
      window.location.href='http://localhost:4200/login'
    }
    
  }

  search(){
    this.filteredLeaves = this.allLeaves.filter((leave)=> {
        let combinedLeave = leave.name + leave.status + leave.type + leave.endDate + leave.startDate
        return combinedLeave.toLowerCase().includes(this.searchInput.toLowerCase())
    })
  }
}
