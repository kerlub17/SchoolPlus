import { DataSource } from '@angular/cdk/collections';
import { map } from 'rxjs/operators';
import { Observable, of as observableOf, merge } from 'rxjs';

// TODO: Replace this with your own data model type
export interface TimetableItem {
  stunde: number;
  zeit: string;
  fach: string;
  lehrkraft: string;
  raum: string;
}

// TODO: replace this with real data from your application
const EXAMPLE_DATA: TimetableItem[] = [
  {stunde: 1, zeit: '08:00 - 08:50', fach:'AM', lehrkraft:'BO', raum:'4BHIF'},
  {stunde: 2, zeit: '08:50 - 09:40', fach:'AM', lehrkraft:'BO', raum:'4BHIF'},
  {stunde: 3, zeit: '09:40 - 10:30', fach:'AM', lehrkraft:'BO', raum:'4BHIF'},

];

/**
 * Data source for the Timetable view. This class should
 * encapsulate all logic for fetching and manipulating the displayed data
 * (including sorting, pagination, and filtering).
 */
export class TimetableDataSource extends DataSource<TimetableItem> {
  data: TimetableItem[] = EXAMPLE_DATA;

  constructor() {
    super();
  }

  /**
   * Connect this data source to the table. The table will only update when
   * the returned stream emits new items.
   * @returns A stream of the items to be rendered.
   */
  connect(): Observable<TimetableItem[]> {
      return merge(observableOf(this.data))
        .pipe(map(() => {
          return this.data;
        }));
  }

  /**
   *  Called when the table is being destroyed. Use this function, to clean up
   * any open connections or free any held resources that were set up during connect.
   */
  disconnect(): void {}
}
