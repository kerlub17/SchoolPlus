import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {HomeComponent} from "./home/home.component";
import {TasksComponent} from "./tasks/tasks.component";
import {TimetableComponent} from "./timetable/timetable.component";

const routes: Routes = [
  {path: 'home',component:HomeComponent},
  {path: 'tasks',component:TasksComponent},
  {path: 'timetable',component:TimetableComponent},
  {path: '**', redirectTo: '/home'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
