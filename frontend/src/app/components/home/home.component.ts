import { Component, OnInit } from '@angular/core';
import { UserService } from '../../services/user.service';
import { ItemService } from '../../services/item.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss',
})
export class HomeComponent implements OnInit {
  content?: string;
  data: any;
  constructor(
    private userService: UserService,
    private itemService: ItemService
  ) {}

  ngOnInit(): void {
    this.userService.getPublicContent().subscribe({
      next: (data) => {
        this.content = data;
      },
      error: (err) => {
        if (err.error) {
          try {
            const res = JSON.parse(err.error);
            this.content = res.message;
          } catch {
            this.content = `Error with status: ${err.status} - ${err.statusText}`;
          }
        } else {
          this.content = `Error with status: ${err.status}`;
        }
      },
    });

    this.itemService.getAllItems().subscribe({
      next: (data) => {
        this.data = data;
        console.log('data');
      },
      error: (err) => {
        if (err.error) {
          try {
            const res = JSON.parse(err.error);
            this.data = res.message;
          } catch {
            this.data = `Error with status: ${err.status} - ${err.statusText}`;
          }
        } else {
          this.data = `Error with status: ${err.status}`;
        }
      },
    });
  }
}
