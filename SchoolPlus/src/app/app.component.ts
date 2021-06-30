import {Component, OnInit} from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent{
  title = 'SchoolPlus';

  /**
   * überprüfen ob User eingeloggt ist, um navbar anzuzeigen
   */

  query = localStorage.getItem('loggedIn');
  isLoggedIn$: boolean = (this.query === 'true');



}
