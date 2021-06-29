import {AfterViewInit, Component, OnInit, ViewChild} from '@angular/core';
import {Router} from "@angular/router";
import {MatTableDataSource} from "@angular/material/table";
import {MatPaginator} from "@angular/material/paginator";
import {MatSort} from "@angular/material/sort";
import {TasksService} from "../services/tasks.service";
import {Task} from "../beans/task";
import {MatDialog, MatDialogConfig} from "@angular/material/dialog";

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


  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  constructor(private taskService: TasksService, private dialog: MatDialog, private router: Router) {
    this.tasktable = [];

  }

  ngOnInit(): void {
    this.taskService.findAll().subscribe(value => {
      this.tasktable = value;
      console.log(this.tasktable)
      this.dataSource = new MatTableDataSource(this.tasktable);
      this.dataSource.paginator = this.paginator;
      this.dataSource.sort = this.sort;
    })
  }

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();

    if (this.dataSource.paginator) {
      this.dataSource.paginator.firstPage();
    }
  }

  createTask()
  {
    this.router.navigateByUrl('/newtask');
  }

  edit(element: Object) {

  }

  delete(element: Object) {

  }
}

