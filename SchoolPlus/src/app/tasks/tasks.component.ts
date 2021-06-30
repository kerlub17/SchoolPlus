/**
 *
 * @author Daniel Moucha
 */

import {Component, OnInit, ViewChild} from '@angular/core';
import {Router} from "@angular/router";
import {MatTableDataSource} from "@angular/material/table";
import {MatPaginator} from "@angular/material/paginator";
import {MatSort} from "@angular/material/sort";
import {TasksService} from "../services/tasks.service";
import {Task} from "../beans/task";
import { MatDialog } from '@angular/material/dialog';
import {HttpClient} from "@angular/common/http";

@Component({
  selector: 'app-tasks',
  templateUrl: './tasks.component.html',
  styleUrls: ['./tasks.component.css']
})
export class TasksComponent implements OnInit {
//"id":9,"name":"mathe-HÜ","subject":"Mathe","date":"20210630","type":"Hausaufgabe","done":false,"note":0,"time":120
  displayedColumns = ['name', 'subject', 'date', 'type', 'done', 'note', 'time', 'actions'];
  tasktable: Task[];
  dataSource!: MatTableDataSource<Task>;
  date: string = "";
  posts!: Object;
  readonly ROOT_URL = 'http://localhost:8080/removetask?id=';


  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  constructor(private taskService: TasksService, public dialog: MatDialog, private router: Router, private http: HttpClient) {
    this.tasktable = [];

  }

  /**
   * durch http-request an backend alle tasks bekommen + sortieren & paginator
   */

  ngOnInit(): void {
    this.taskService.findAll().subscribe(value => {
      this.tasktable = value;
      console.log(this.tasktable)
      this.dataSource = new MatTableDataSource(this.tasktable);
      this.dataSource.paginator = this.paginator;
      this.dataSource.sort = this.sort;
    })
  }


  /**
   * tasks auf eingegeben filter überprüfen
   * @param event
   */

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();

    if (this.dataSource.paginator) {
      this.dataSource.paginator.firstPage();
    }
  }

  /**
   * navigieren zur seite für das Erstellen eines neuen Tasks
   */

  createTask() {
    this.router.navigateByUrl('/newtask');
  }

  /**
   * confirm dialog für delete --> anschließend Funktion 'deletetasks' mit id des tasks vom backend per http-request ansprechen,
   * um den task zu löschen
   * @param element
   */
  onDelete(element: Object) {
    if(confirm("Diese Aufgabe wirklich löschen?")) {
      this.http.get(this.ROOT_URL + element).subscribe(value => {
        this.posts = value;
      });

      window.location.reload();
    }

  }

  /**
   * navigieren zur seite für das bearbeiten eines Tasks mit übergebenen Werten
   * @param id
   * @param name
   * @param subject
   * @param date
   * @param type
   * @param done
   * @param note
   * @param time
   */

  //l.id, l.name, l.subject, l.date, l.type, l.done, l.note, l.time
  onUpdate(id: Object, name: Object, subject: Object, date: Object, type: Object, done: Object, note: Object, time: Object,) {
    localStorage.setItem('id', id.toString());
    localStorage.setItem('name', name.toString());
    localStorage.setItem('subject', subject.toString());
    localStorage.setItem('date', date.toString());
    localStorage.setItem('type', type.toString());
    localStorage.setItem('done', done.toString());
    localStorage.setItem('note', note.toString());
    localStorage.setItem('time', time.toString());

    this.router.navigateByUrl('/updatetask');

  }
}

