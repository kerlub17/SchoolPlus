/**
 *
 * @author Daniel Moucha
 */

import {Component, OnInit, ViewChild} from '@angular/core';
import { map } from 'rxjs/operators';
import { Breakpoints, BreakpointObserver } from '@angular/cdk/layout';
import {TimetableService} from "../services/timetable.service";
import {Timetable} from "../beans/timetable";
import {GlobalVariables} from "../../global-variables";
import {NavComponent} from "../nav/nav.component";
import {Task} from "../beans/task";
import {HttpClient} from "@angular/common/http";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit{

  private taskUrl!: string;
  displayedColumns = ['index', 'time', 'subject', 'room', 'teacher', 'activityType'];
  displayedColumnsTasks = ['name', 'subject', 'date', 'type', 'done', 'note', 'time'];
  timetable: Timetable[];
  tasktable: Task[];

  constructor(private timetableService: TimetableService, private http: HttpClient) {
    this.timetable = []
    this.tasktable = []
    this.taskUrl = 'http://localhost:8080/gettasks?date=today';
  }

  /**
   * get data for timetable from service and reload
   */

  ngOnInit() {

    if (!localStorage.getItem('refresh')) {
      localStorage.setItem('refresh', 'no reload')
      location.reload()
    } else {
      localStorage.removeItem('refresh')
    }

    this.timetableService.findAll().subscribe(value => {
      this.timetable = value;
      console.log(this.timetable);
    })

    this.http.get<Task[]>(this.taskUrl).subscribe(value => {
      this.tasktable = value;
    });
  }


}
