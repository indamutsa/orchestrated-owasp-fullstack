import { Component, OnInit } from '@angular/core';
import { ItemService } from '../../services/item.service';
import { StorageService } from '../../services/storage.service';
import { Router } from '@angular/router';

interface Item {
  id: number;
  name: string;
  description: string;
}

declare var bootstrap: any;

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss',
})
export class HomeComponent implements OnInit {
  content?: string;
  items?: any[] = [];
  userId: any;
  private roles: string[] = [];
  isLoggedIn = false;
  addItemModal: any;
  deleteModal: any;
  updateItemModal: any;
  selectedItem: any = { name: '', description: '' };

  constructor(
    private itemService: ItemService,
    private storageService: StorageService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.isLoggedIn = this.storageService.isLoggedIn();
    this.userId = this.storageService.getUser().id;
    this.itemService.fetchAllItemsByUser(this.userId);
    this.itemService.items$.subscribe((items) => {
      this.items = items;
    });
  }

  ngAfterViewInit() {
    this.deleteModal = new bootstrap.Modal(
      document.getElementById('deleteConfirmationModal'),
      {
        keyboard: false,
      }
    );

    this.updateItemModal = new bootstrap.Modal(
      document.getElementById('updateItemModal'),
      {
        keyboard: false,
      }
    );
    this.addItemModal = new bootstrap.Modal(
      document.getElementById('addItemModal'),
      {
        keyboard: false,
      }
    );
  }

  gotToItemDetails(itemId: string): void {
    this.router.navigate(['/item', itemId]);
  }

  onAddItem() {
    this.addItemModal.show();
  }

  onDeleteButtonClick() {
    this.deleteModal.show();
  }

  onConfirmDelete() {
    // Perform the deletion here
    console.log('Item deleted');
    this.deleteModal.hide();
  }

  onUpdateButtonClick(item: Item) {
    this.selectedItem = item;
    this.updateItemModal.show();
  }

  onConfirmUpdate() {
    // Perform the update here
    console.log('Item updated:', this.selectedItem);
    this.updateItemModal.hide();
  }
}
