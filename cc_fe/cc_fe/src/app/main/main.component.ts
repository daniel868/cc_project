import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {ROLE} from "../model/role";
import {Store} from "@ngrx/store";
import {AppState} from "../common/state/app.reducer";
import {map} from "rxjs";

@Component({
  selector: 'app-main',
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.scss']
})
export class MainComponent implements OnInit {

  isAdmin: boolean = false;

  constructor(private store: Store<AppState>) {
  }


  ngOnInit(): void {
    this.store.select('authState')
      .pipe(
        map(state => state.roles)
      ).subscribe(roles => {
      if (!!roles) {
        this.isAdmin = !!roles.find(role => role.roleName === 'ADMIN')
      }
    })
  }

}
