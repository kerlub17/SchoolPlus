/**
 * Service um durch einen http-request ans backend alle tasks zu bekommen
 * (wird in `tasks.component.ts' aufgerufen)
 * @author Daniel Moucha
 */

import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Task} from "../beans/task";

@Injectable({
  providedIn: 'root'
})
export class TasksService {
  private taskUrl: string;

  constructor(private http: HttpClient) {
    this.taskUrl = 'http://localhost:8080/gettasks';
  }

  public findAll(): Observable<Task[]> {
    return this.http.get<Task[]>(this.taskUrl);
  }

  public save(tasktable: Task) {
    return this.http.post<Task>(this.taskUrl, tasktable);
  }
}
