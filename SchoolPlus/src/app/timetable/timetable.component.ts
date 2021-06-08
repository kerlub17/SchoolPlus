import {Component, OnInit} from '@angular/core';
import {TimetableService} from "../services/timetable.service";
import {Timetable} from "../beans/timetable";

@Component({
  selector: 'app-timetable',
  templateUrl: './timetable.component.html',
  styleUrls: ['./timetable.component.css']
})
export class TimetableComponent implements OnInit{
  displayedColumns = ['hour', 'time', 'subject', 'room', 'teacher'];
  dataSource :any;

  timetable: Timetable[];

  constructor(private timetableService: TimetableService) {
    this.timetable = [];
  }

  ngOnInit() {
    this.timetableService.findAll().subscribe(value => {
      this.timetable = value;
      console.log(this.timetable);
      this.dataSource = this.timetable;
    })
  }
}

/*
const ELEMENT_DATA: StundenPlan[] = [
  {zeit: '08:00 - 08:50', fach: 'AM', lehrkraft: 'BO', raum: 'EG27'},
  {zeit: '08:50 - 09:40', fach: 'AM', lehrkraft: 'BO', raum: 'EG27'},
  {zeit: '09:40 - 10:30', fach: 'AM', lehrkraft: 'BO', raum: 'EG27'},
  {zeit: '10:45 - 11:35', fach: 'AM', lehrkraft: 'BO', raum: 'EG27'},
  {zeit: '11:35 - 12:25', fach: 'AM', lehrkraft: 'BO', raum: 'EG27'},
  {zeit: '12:25 - 13:15', fach: 'AM', lehrkraft: 'BO', raum: 'EG27'},
  {zeit: '08:00 - 08:50', fach: 'AM', lehrkraft: 'BO', raum: 'EG27'},
  {zeit: '08:50 - 09:40', fach: 'AM', lehrkraft: 'BO', raum: 'EG27'},
  {zeit: '09:40 - 10:30', fach: 'AM', lehrkraft: 'BO', raum: 'EG27'},
  {zeit: '10:45 - 11:35', fach: 'AM', lehrkraft: 'BO', raum: 'EG27'},
  {zeit: '09:40 - 10:30', fach: 'AM', lehrkraft: 'BO', raum: 'EG27'},
  {zeit: '10:45 - 11:35', fach: 'AM', lehrkraft: 'BO', raum: 'EG27'},
];*/
