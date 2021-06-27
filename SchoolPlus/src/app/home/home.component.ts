import {Component, OnInit, ViewChild} from '@angular/core';
import { map } from 'rxjs/operators';
import { Breakpoints, BreakpointObserver } from '@angular/cdk/layout';
import {TimetableService} from "../services/timetable.service";
import {Timetable} from "../beans/timetable";
import {GlobalVariables} from "../../global-variables";
import {NavComponent} from "../nav/nav.component";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit{

  displayedColumns = ['hour', 'time', 'subject', 'room', 'teacher', 'activityType'];
  timetable: Timetable[];

  constructor(private timetableService: TimetableService) {
    this.timetable = [];
  }

  ngOnInit() {

    if (!localStorage.getItem('foo')) {
      localStorage.setItem('foo', 'no reload')
      location.reload()
    } else {
      localStorage.removeItem('foo')
    }

    this.timetableService.findAll().subscribe(value => {
      this.timetable = value;
      console.log(this.timetable);
    })
  }


}
