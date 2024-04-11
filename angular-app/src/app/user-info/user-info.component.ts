import { Component, OnInit } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {AuthService} from "../auth/auth.service";
import {WebApiService} from "../api/web-api.service";
import { User } from '../models/user';
import { UserService } from '../services/user.service';

@Component({
  selector: 'app-user-info',
  templateUrl: './user-info.component.html',
  styleUrls: ['./user-info.component.css']
})
export class UserInfoComponent implements OnInit {

  message: string = 'null';
  user: any ;

  constructor(private authService: AuthService, private webApiService: WebApiService, private http: HttpClient, private userService:UserService) { }

  ngOnInit(): void {
    this.message = this.authService.getUsername();
    this.userService.getUser().subscribe(res => { this.user = res});
    this.webApiService.getUserInfo().subscribe({
      next: data => {
        console.log(data.profile.username)
        this.message += ", " + data;
      }, error: err => {
        console.log(err);
      }
    });

  }

}
