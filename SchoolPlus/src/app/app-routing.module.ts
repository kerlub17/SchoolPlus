import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {HomeComponent} from "./home/home.component";
import {TasksComponent} from "./tasks/tasks.component";
import {LoginComponent} from "./login/login.component";
import {NavComponent} from "./nav/nav.component";

const routes: Routes = [
  {path: 'home',component:HomeComponent},
  {path: 'tasks',component:TasksComponent},
  {path: 'login',component:LoginComponent},
  {path: 'nav', component:NavComponent},
  {path: '**', redirectTo: '/login'}

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
