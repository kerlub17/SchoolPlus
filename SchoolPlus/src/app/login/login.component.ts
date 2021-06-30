/**
 *
 * @author Daniel Moucha
 */

import { Component, OnInit } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import { Router } from "@angular/router";
import {NgForm} from "@angular/forms";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit{

  readonly ROOT_URL = 'http://localhost:8080/auth?';

  posts!: Object;
  loggedInQuery: string = "error";
  wrongInput: boolean = false;
  isLoadingResults = false;

  constructor(private http: HttpClient, private router: Router) { }

  /**
   * nach dem Click auf 'Login' http-request an backend senden und überprüfen ob die daten (username&password) korrekt sind
   * -- falls ja, weiterleiten zum Dashboard
   * @param values
   * @param form
   */
  onLogin(values: any, form: NgForm)
  {
    this.isLoadingResults = true;

    this.http.get(this.ROOT_URL + 'username=' + values.username.toString() + '&password=' + values.password.toString()).subscribe(value => {
      this.posts = value;
      this.loggedInQuery = JSON.stringify(this.posts);

      console.log(this.loggedInQuery);
      if(this.loggedInQuery.includes("error"))
      {
        console.log("nope");
        this.wrongInput = true;
        localStorage.setItem('loggedIn', "false");
        form.resetForm();
        this.isLoadingResults = false;
      }
      else
      {
        console.log("yes");
        this.wrongInput = false;
        localStorage.setItem('loggedIn', "true");
        this.isLoadingResults = false;
        this.router.navigateByUrl('/home');

      }
    })
  }

  ngOnInit(): void {
    if (!localStorage.getItem('refresh')) {
      localStorage.setItem('refresh', 'no reload')
      location.reload()
    } else {
      localStorage.removeItem('refresh')
    }

    if(this.loggedInQuery.includes("error"))
    {
      localStorage.setItem('loggedIn', "false");
    }
  }

}
