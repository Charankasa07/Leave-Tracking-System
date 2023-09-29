import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ApplyLeaveComponent } from './apply-leave/apply-leave.component';
import { EditLeaveComponent } from './edit-leave/edit-leave.component';
import { EmployeeDashboardComponent } from './employee-dashboard/employee-dashboard.component';
import { HomeComponent } from './home/home.component';
import { LeaveHistoryComponent } from './leave-history/leave-history.component';
import { LoginComponent } from './login/login.component';
import { ManagerDashboardComponent } from './manager-dashboard/manager-dashboard.component';
import { NewRequestsComponent } from './new-requests/new-requests.component';
import { OverviewComponent } from './overview/overview.component';
import { RegistrationComponent } from './registration/registration.component';
import { SettingsComponent } from './settings/settings.component';
import { TrackLeavesComponent } from './track-leaves/track-leaves.component';
import { LtsComponent } from './lts/lts.component';
import { NotFoundComponent } from './not-found/not-found.component';

const routes: Routes = [
  {
    path:'',
    component:HomeComponent,
  },
  {
    path:'login',
    component:LoginComponent,
  },
  {
    path:'register',
    component:RegistrationComponent,
  },
  {
    path:'employee',
    component:EmployeeDashboardComponent,
    children:[
      {
        path:'apply-leave',
        component:ApplyLeaveComponent
      },
      {
        path:'track-leaves',
        component:TrackLeavesComponent,
      },
      {
        path:'edit-leave/:id',
        component:EditLeaveComponent,
      },
      {
        path:'leave-history',
        component:LeaveHistoryComponent,
      },
      {
        path:'',
        redirectTo:'leave-history',
        pathMatch:'full'
      }
    ]
  },
  {
    path:'manager',
    component:ManagerDashboardComponent,
    children:[
      {
        path:'new-requests',
        component:NewRequestsComponent,
      },
      {
        path:'overview',
        component:OverviewComponent,
      },
      {
        path:'settings',
        component:SettingsComponent,
      },
      {
        path:'',
        redirectTo:'new-requests',
        pathMatch:'full'
      }
    ]
  },
  {
    path:'**',
    component:NotFoundComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
