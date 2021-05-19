import { Component} from '@angular/core';

@Component({
  selector: 'app-timetable',
  templateUrl: './timetable.component.html',
  styleUrls: ['./timetable.component.css']
})
export class TimetableComponent {
  displayedColumns = ['stunde', 'zeit', 'fach', 'lehrkraft', 'raum'];
  dataSource = ELEMENT_DATA;
}

export interface StundenPlan {
  stunde: number;
  zeit: string;
  fach: string;
  lehrkraft: string;
  raum: string;
}

const ELEMENT_DATA: StundenPlan[] = [
  {stunde: 1, zeit: '08:00 - 08:50', fach: 'AM', lehrkraft: 'BO', raum: 'EG27'},
  {stunde: 2, zeit: '08:50 - 09:40', fach: 'AM', lehrkraft: 'BO', raum: 'EG27'},
  {stunde: 3, zeit: '09:40 - 10:30', fach: 'AM', lehrkraft: 'BO', raum: 'EG27'},
  {stunde: 4, zeit: '10:45 - 11:35', fach: 'AM', lehrkraft: 'BO', raum: 'EG27'},
  {stunde: 5, zeit: '11:35 - 12:25', fach: 'AM', lehrkraft: 'BO', raum: 'EG27'},
  {stunde: 6, zeit: '12:25 - 13:15', fach: 'AM', lehrkraft: 'BO', raum: 'EG27'},
  {stunde: 7, zeit: '08:00 - 08:50', fach: 'AM', lehrkraft: 'BO', raum: 'EG27'},
  {stunde: 8, zeit: '08:50 - 09:40', fach: 'AM', lehrkraft: 'BO', raum: 'EG27'},
  {stunde: 9, zeit: '09:40 - 10:30', fach: 'AM', lehrkraft: 'BO', raum: 'EG27'},
  {stunde: 10, zeit: '10:45 - 11:35', fach: 'AM', lehrkraft: 'BO', raum: 'EG27'},
  {stunde: 11, zeit: '09:40 - 10:30', fach: 'AM', lehrkraft: 'BO', raum: 'EG27'},
  {stunde: 12, zeit: '10:45 - 11:35', fach: 'AM', lehrkraft: 'BO', raum: 'EG27'},
];
