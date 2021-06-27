import {AfterViewInit, Component, OnInit} from '@angular/core';
import { BreakpointObserver, Breakpoints } from '@angular/cdk/layout';
import { Observable } from 'rxjs';
import { Router } from '@angular/router';
import { ChangeDetectorRef } from '@angular/core';


import { map, shareReplay } from 'rxjs/operators';
import {GlobalVariables} from "../../global-variables";
import {HttpClient} from "@angular/common/http";

@Component({
  selector: 'app-nav',
  templateUrl: './nav.component.html',
  styleUrls: ['./nav.component.css']
})
export class NavComponent implements OnInit, AfterViewInit{

  isDarkTheme: boolean = false;
  isLoggedIn$: boolean = false;
  gv = GlobalVariables.getInstance();
  isHandset$: Observable<boolean> = this.breakpointObserver.observe(Breakpoints.Handset)
    .pipe(
      map(result => result.matches),
      shareReplay()
    );

  constructor(private breakpointObserver: BreakpointObserver, private router: Router, private cdRef:ChangeDetectorRef) {}

  ngOnInit(){
    this.isDarkTheme = localStorage.getItem('theme')==="Dark" ? true:false;

  }

  ngAfterViewInit(): void {
    this.isLoggedIn$ = this.gv.status;
    console.log(this.gv.status + "nav1");
    console.log(this.isLoggedIn$);
    this.cdRef.detectChanges();
    if (this.isLoggedIn$)
    {this.router.navigateByUrl('/home');}

  }

  storeThemeSelection(){
    localStorage.setItem('theme', this.isDarkTheme ? "Dark" : "Light");
  }


}
