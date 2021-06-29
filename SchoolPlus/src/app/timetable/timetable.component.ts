import { Component, OnInit } from '@angular/core';
import {Timetable} from "../beans/timetable";
import {TimetableService} from "../services/timetable.service";
import {HttpClient} from "@angular/common/http";

@Component({
  selector: 'app-timetable',
  templateUrl: './timetable.component.html',
  styleUrls: ['./timetable.component.css']
})
export class TimetableComponent implements OnInit{

  private timetableUrl!: string;
  displayedColumns = ['index', 'time', 'subject', 'room', 'teacher', 'activityType'];
  timetable: Timetable[];

  constructor(private http: HttpClient) {
    this.timetable = []
    this.timetableUrl = 'http://localhost:8080/gettimetable?date=';

  }

  onDateChanged(date: unknown)
  {
    this.http.get<Timetable[]>(this.timetableUrl + date).subscribe(value => {
      this.timetable = value;
    });
  }

  weekendsDatesFilter = (d: Date | null): boolean => {
    const day = (d || new Date()).getDay();
    // Prevent Saturday and Sunday from being selected.
    return day !== 0 && day !== 6;
  }

  ngOnInit(): void {
    if (!localStorage.getItem('refresh')) {
      localStorage.setItem('refresh', 'no reload')
      location.reload()
    } else {
      localStorage.removeItem('refresh')
    }
  }

}
