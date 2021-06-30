import { Component, OnInit } from '@angular/core';
import {NgForm} from "@angular/forms";
import {HttpClient} from "@angular/common/http";
import {Router} from "@angular/router";

interface Note {
  id: number;
  value: number;
}

@Component({
  selector: 'app-updatetask',
  templateUrl: './updatetask.component.html',
  styleUrls: ['./updatetask.component.css']
})
export class UpdatetaskComponent implements OnInit {

  readonly ROOT_URL = 'http://localhost:8080/updatetask?';

  constructor(private http: HttpClient, private router: Router) { }

  selectedNote!: string;
  posts!: Object;
  taskQuery: String = "";
  newid: String | null = "";
  newname: String | null = "";
  newsubject: String | null = "";
  newdate: String | null = "";
  newdate2: String | undefined = "";
  newtype: String | null = "";
  newdone: String | null = "";
  newnote: String | null = "";
  newtime: String | null = "";
  noteNumber: number = 0;
  typeString: string = "";
  typeNumber: number = 0;
  doneString: string = "";
  doneNumber: number = 0;
  dateToPass!: Date;

  arts = [
    { id: 1, value: "Aufgabe" },
    { id: 2, value: "Test" },
    { id: 1, value: "Schularbeit" },
    { id: 1, value: "PLF" },
    { id: 1, value: "Stunden-WH" },];

  done = [
    { id: 1, value: "nein" },
    { id: 2, value: "ja" }];

  note: Note[] = [
    { id: 1, value: 1 },
    { id: 2, value: 2 },
    { id: 3, value: 3 },
    { id: 4, value: 4 },
    { id: 5, value: 5 },
    { id: 6, value: 0},]

  /**
   * Task mit neu übergebenen Werten updaten
   * @param values
   * @param form
   */

  onUpdateTask(values: any, form: NgForm) {
    this.http.get(this.ROOT_URL + 'id=' + this.newid
      + '&name=' + values.name.toString()
      + '&subject=' + values.fach.toString()
      + '&date=' + values.datum
      + '&type=' + values.art
      + '&done=' + values.done
      + '&note=' + values.note
      + '&time=' + values.zeit.toString()).subscribe(value => {
      this.posts = value;
    })
    this.router.navigateByUrl('/tasks');
  }

  /**
   * holen der aktuellen Werte des Tasks um sie in der html-form beim ansurfen bereits anzuzeigen
   */

  ngOnInit() {
    this.newid = localStorage.getItem('id');
    this.newname = localStorage.getItem('name');
    this.newsubject = localStorage.getItem('subject');
    this.newdate = localStorage.getItem('date');
    this.newtype = localStorage.getItem('type');
    this.newdone = localStorage.getItem('done');
    this.newnote = localStorage.getItem('note');
    this.newtime = localStorage.getItem('time');

    switch (this.newnote) {
      case '0':
        this.noteNumber = 5;
        break;
      case '1':
        this.noteNumber = 0;
        break;
      case '2':
        this.noteNumber = 1;
        break;
      case '3':
        this.noteNumber = 2;
        break;
      case '4':
        this.noteNumber = 3;
        break;
      case '5':
        this.noteNumber = 4;
        break;
    }

    switch(this.newtype?.toLowerCase())
    {
      case "aufgabe":
        this.typeNumber = 0;
        break;
      case "test":
        this.typeNumber = 1;
        break;
      case "schularbeit":
        this.typeNumber = 2;
        break;
      case "plf":
        this.typeNumber = 3;
        break;
      case "stunden-wh":
        this.typeNumber = 4;
        break;
    }

    switch(this.newdone?.toUpperCase())
    {
      case "NEIN":
        this.doneNumber = 0;
        break;
      case "JA":
        this.doneNumber = 1;
        break;
    }

    this.noteNumber = this.note[this.noteNumber].value;
    this.typeString = this.arts[this.typeNumber].value;
    this.doneString = this.done[this.doneNumber].value;
    console.log(this.doneString);
    this.newdate2 = this.newdate?.replace('.', '/').replace('.', '/');
    let date = new Date("" + this.newdate2);
    this.dateToPass = date;
  }


  /**
   * Beim Klick auf den Button 'cancel' zurück zu den tasks navigieren
   */
  onCancel() {
    this.router.navigateByUrl('/tasks');
  }


}
