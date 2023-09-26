import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Leave, UserLogin, UserRegister } from './User';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class BackendService {

  constructor(private http : HttpClient) { }

  url = "http://localhost:8080/"

  onRegister(user : UserRegister) : Observable<any>{
      return this.http.post<UserRegister>(this.url+"register",user)
  }
  onLogin(user : UserLogin) : Observable<any>{
    return this.http.post<UserLogin>(this.url + "login",user);
  }

  

  getNewRequests() : Observable<any> {
    return this.http.get<any>(this.url+"manager/new-requests")
  }

  reactToLeave(id : Number , action : String,message : any):Observable<any>{
    return this.http.post<any>(this.url + `manager/leave/${id}/${action}`,message);
  }

  getOverviewLeaves() : Observable<any>{
    return this.http.get<any>(this.url + "manager/overview")
  }

  updateLeaveCount(count : Number) : Observable<any>{
    return this.http.post<Number>(this.url + `manager/${count}`,null);
  }



  applyLeave(leave : Leave) : Observable<any>{
    return this.http.post<any>(this.url + "employee/apply-leave",leave)
  }

  getAllLeaves(email : string) : Observable<any>{
    return this.http.get<any>(this.url + `employee/leaves/${email}`)
  }

  editLeave(id : Number , leave : Leave) : Observable<any>{
    return this.http.post<any>(this.url + `employee/edit-leave/${id}`,leave);
  }

  getLeave(id:Number):Observable<any>{
    return this.http.get<any>(this.url + `employee/get-leave/${id}`)
  }

  deleteLeave(id : Number) : Observable<any> {
    return this.http.delete<any>(this.url + `employee/leave/${id}`)
  }
}
