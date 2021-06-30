/**
 *
 * @author Daniel Moucha
 */

import { Component, OnInit } from '@angular/core';
import {Timetable} from "../beans/timetable";
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

  /**
   * nach auswählen von Datum entsprechenden Stundenplan per http-requst vom backend holen
   * @param date
   */
  onDateChanged(date: unknown)
  {
    this.http.get<Timetable[]>(this.timetableUrl + date).subscribe(value => {
      this.timetable = value;
    });
  }


  /**
   * Funktion um Wochenende beim Date-Picker nicht auswählen zu können
   * @param d
   */

  weekendsDatesFilter = (d: Date | null): boolean => {
    const day = (d || new Date()).getDay();
    // Prevent Saturday and Sunday from being selected.
    return day !== 0 && day !== 6;
  }

  /**
   * Seite reloaden um aktuelle timetable zu zeigen
   */

  ngOnInit(): void {
    if (!localStorage.getItem('refresh')) {
      localStorage.setItem('refresh', 'no reload')
      location.reload()
    } else {
      localStorage.removeItem('refresh')
    }
  }

}
