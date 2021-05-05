import { Component } from '@angular/core';
import { map } from 'rxjs/operators';
import { Breakpoints, BreakpointObserver } from '@angular/cdk/layout';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent {
  /** Based on the screen size, switch from standard to one column per row */
  cards = this.breakpointObserver.observe(Breakpoints.Handset).pipe(
    map(({ matches }) => {
      if (matches) {
        return [
          { title: 'Alle Aufgaben', cols: 5, rows: 1 },
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
