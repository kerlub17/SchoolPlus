/**
 *
 * @author Daniel Moucha
 */

import { Component, OnInit } from '@angular/core';
import {NgForm} from "@angular/forms";
import {HttpClient} from "@angular/common/http";
import {Router} from "@angular/router";

@Component({
  selector: 'app-newtask',
  templateUrl: './newtask.component.html',
  styleUrls: ['./newtask.component.css']
})
export class NewtaskComponent implements OnInit {

  readonly ROOT_URL = 'http://localhost:8080/newtask?';

  constructor(private http: HttpClient, private router: Router) { }

  selectedArt!: string;
  selectedDone!: string;
  selectedNote!: string;
  posts!: Object;
  taskQuery: String = "";

  /**
   * Felder für die dropdown-menüs
   */

  arts = [
    { id: 1, value: "Aufgabe" },
    { id: 2, value: "Test" },
    { id: 1, value: "Schularbeit" },
    { id: 1, value: "PLF" },
    { id: 1, value: "Stunden-WH" }];

  done = [
    { id: 1, value: 'nein' },
    { id: 2, value: 'ja' }];

  note = [
    { id: 1, value: 1 },
    { id: 2, value: 2 },
    { id: 3, value: 3 },
    { id: 4, value: 4 },
    { id: 5, value: 5 },
    { id: 6, value: 0},]

  /**
   * Values aus newtask.component.html holen und damit einen http-request ans backend senden, um einen neuen task anzulegen
   * @param values
   * @param form
   */

  onNewTask(values: any, form: NgForm) {
    this.http.get(this.ROOT_URL + 'name=' + values.name.toString()
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

  ngOnInit() {

  }

  /**
   * Beim Klick auf den Cancel-Button zurück zu den tasks navigieren
   */

  onCancel() {
    this.router.navigateByUrl('/tasks');
  }


}
