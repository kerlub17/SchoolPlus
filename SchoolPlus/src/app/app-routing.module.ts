import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {HomeComponent} from "./home/home.component";
import {TasksComponent} from "./tasks/tasks.component";
import {LoginComponent} from "./login/login.component";
import {NavComponent} from "./nav/nav.component";
/**
 * Klasse zum erstellen aller Paths zum navigieren im Browser
 * @author Daniel Moucha
 */

import {NewtaskComponent} from "./newtask/newtask.component";
import {TimetableComponent} from "./timetable/timetable.component";
import {UpdatetaskComponent} from "./updatetask/updatetask.component";

const routes: Routes = [
  {path: 'home',component:HomeComponent},
  {path: 'tasks',component:TasksComponent},
  {path: 'login',component:LoginComponent},
  {path: 'nav', component:NavComponent},
  {path: 'newtask',component:NewtaskComponent},
  {path: 'timetable', component:TimetableComponent},
  {path: 'updatetask', component:UpdatetaskComponent},
  {path: '**', redirectTo: '/login'}

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
