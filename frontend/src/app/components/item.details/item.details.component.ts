import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ItemService } from '../../services/item.service';
import { StorageService } from '../../services/storage.service';

@Component({
  selector: 'app-item.details',
  templateUrl: './item.details.component.html',
  styleUrl: './item.details.component.scss',
})
export class ItemDetailsComponent implements OnInit {
  item: any;

  constructor(
    private route: ActivatedRoute,
    private itemService: ItemService,
    private storageService: StorageService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.route.params.subscribe((params) => {
      const itemId = params['id'];
      const userId = this.storageService.getUser().id;
      this.item = this.itemService.getItemById(itemId);
    });
  }
}
