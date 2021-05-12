import { AfterViewInit, Component, ViewChild } from '@angular/core';
import { MatTable } from '@angular/material/table';
import { TimetableDataSource, TimetableItem } from './timetable-datasource';

@Component({
  selector: 'app-timetable',
  templateUrl: './timetable.component.html',
  styleUrls: ['./timetable.component.css']
})
export class TimetableComponent implements AfterViewInit {
  @ViewChild(MatTable) table!: MatTable<TimetableItem>;
  dataSource: TimetableDataSource;

  /** Columns displayed in the table. */
  displayedColumns = ['stunde', 'zeit', 'fach', 'lehrkraft', 'raum'];

  constructor() {
    this.dataSource = new TimetableDataSource();
  }

  ngAfterViewInit(): void {
    this.table.dataSource = this.dataSource;
  }
}
