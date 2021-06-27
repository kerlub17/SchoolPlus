import { Component, OnInit } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import { Router } from "@angular/router";
import {Observable} from "rxjs";
import {GlobalVariables} from "../../global-variables";
import {NgForm} from "@angular/forms";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent{

  readonly ROOT_URL = 'http://localhost:8080/auth?';

  posts!: Object
  loggedInQuery: string = "";
  wrongInput: boolean = false;


  constructor(private http: HttpClient, private router: Router) { }

  onLogin(values: any, form: NgForm)
  {
    /*let gv = GlobalVariables.getInstance();*/
    console.warn(values.username)
    console.warn(values.password)

    this.http.get(this.ROOT_URL + 'username=' + values.username.toString() + '&password=' + values.password.toString()).subscribe(value => {
      this.posts = value;
      this.loggedInQuery = JSON.stringify(this.posts);

      console.log(this.loggedInQuery);
      if(this.loggedInQuery.includes("error"))
      {
        console.log("nope");
        this.wrongInput = true;
        //gv.status = false;
        localStorage.setItem('loggedIn', "false");
        form.resetForm();
      }
      else
      {
        console.log("yes");
        this.wrongInput = false;
        /*console.log(gv.status);
        gv.status = true;*/
        localStorage.setItem('loggedIn', "true");
        //console.log(gv.status);
        this.router.navigateByUrl('/home');

      }
    })
  }

}
