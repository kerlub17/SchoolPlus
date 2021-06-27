import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import {Observable} from "rxjs";
import {Timetable} from "../beans/timetable";

@Injectable({
  providedIn: 'root'
})
export class TimetableService {
  private timetableUrl: string;

  constructor(private http: HttpClient) {
    this.timetableUrl = 'http://localhost:8080/gettimetable?date=20210630';
  }

  public findAll(): Observable<Timetable[]> {
    return this.http.get<Timetable[]>(this.timetableUrl);
  }

  public save(timetable: Timetable) {
    return this.http.post<Timetable>(this.timetableUrl, timetable);
  }
}
