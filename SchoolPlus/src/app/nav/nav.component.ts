/**
 *
 * @author Daniel Moucha
 */

import {AfterViewInit, Component, OnInit} from '@angular/core';
import { BreakpointObserver, Breakpoints } from '@angular/cdk/layout';
import { Observable } from 'rxjs';
import { Router } from '@angular/router';

import { map, shareReplay } from 'rxjs/operators';
import {GlobalVariables} from "../../global-variables";
import {HttpClient} from "@angular/common/http";

@Component({
  selector: 'app-nav',
  templateUrl: './nav.component.html',
  styleUrls: ['./nav.component.css']
})
export class NavComponent implements OnInit{

  readonly ROOT_URL = 'http://localhost:8080/logout';
  logoutText!: Object;
  isDarkTheme: boolean = false;
  loggedOutQuery: string = "";
  gv = GlobalVariables.getInstance();
  isHandset$: Observable<boolean> = this.breakpointObserver.observe(Breakpoints.Handset)
    .pipe(
      map(result => result.matches),
      shareReplay()
    );

  constructor(private breakpointObserver: BreakpointObserver, private router: Router, private http: HttpClient) {}

  ngOnInit(){
    this.isDarkTheme = localStorage.getItem('theme')==="Dark" ? true:false;

  }

  /**
   * zum Speichern des Status vom DarkTheme
   */
  storeThemeSelection(){
    localStorage.setItem('theme', this.isDarkTheme ? "Dark" : "Light");
  }

  /**
   * einen http-request an das backend senden, um den user auszuloggen - danach zum login-Fenser navigieren
   */

  logout()
  {
    if(confirm("Do you really want to logout?")) {
      this.http.get(this.ROOT_URL).subscribe(value => {
        this.logoutText = value;
        this.loggedOutQuery = JSON.stringify(this.logoutText);

        localStorage.setItem('loggedOutText', this.loggedOutQuery);
        localStorage.setItem('loggedIn', "false");
        this.router.navigateByUrl('/login');
      })
    }

  }


}
