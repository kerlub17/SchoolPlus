import { Component } from '@angular/core';
import { map } from 'rxjs/operators';
import { Breakpoints, BreakpointObserver } from '@angular/cdk/layout';

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

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent {

  displayedColumns = ['stunde', 'zeit', 'fach', 'lehrkraft', 'raum'];
  dataSource = ELEMENT_DATA;

  /** Based on the screen size, switch from standard to one column per row */
  cards = this.breakpointObserver.observe(Breakpoints.Handset).pipe(
    map(({ matches }) => {
      if (matches) {
        return [
          { title: 'Alle Aufgaben', cols: 5, rows: 1},
          { title: 'Heutiger Stundenplan', cols: 5, rows: 1 },
          { title: 'Abgaben', cols: 5, rows: 1 },
          { title: 'Prüfungen', cols: 5, rows: 1 }
        ];
      }

      return [
        { title: 'Alle Aufgaben', cols: 3, rows: 1 },
        { title: 'Abgaben', cols: 2, rows: 1 },
        { title: 'Heutiger Stundenplan', cols: 3, rows: 1 },
        { title: 'Prüfungen', cols: 2, rows: 1 }
      ];
    })
  );

  constructor(private breakpointObserver: BreakpointObserver) {}
}
