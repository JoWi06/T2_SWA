import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { ProductService } from '../../service/product';
import { Product } from '../../model/product';

@Component({
  selector: 'app-product-list',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './product-list.html',
  styleUrl: './product-list.css'
})
export class ProductListComponent implements OnInit {
  products: Product[] = []; 
  showDeleteModal: boolean = false;
  selectedProductId?: number; 

  constructor(
    private productService: ProductService,
    private cdr: ChangeDetectorRef 
  ) {}

  ngOnInit(): void {
    this.loadProducts();
  }

  loadProducts(): void {
    this.productService.getAllProducts().subscribe({
      next: (data) => {
        console.log("Productos cargados desde el backend:", data);
        this.products = data; 
        this.cdr.detectChanges(); 
      },
      error: (err) => {
        console.error('Error al traer datos:', err);
      }
    });
  }

  openModal(id: number): void { 
    this.selectedProductId = id;
    this.showDeleteModal = true; 
    this.cdr.detectChanges(); 
  }

  closeModal(): void { 
    this.showDeleteModal = false; 
    this.selectedProductId = undefined;
    this.cdr.detectChanges(); 
  }

  confirmDelete(): void { 
    if (this.selectedProductId) {
      this.productService.deleteProduct(this.selectedProductId).subscribe({
        next: () => {
          console.log("Producto eliminado con éxito");
          this.showDeleteModal = false;
          this.loadProducts(); // Recarga la tabla de inmediato
        },
        error: (err) => {
          console.error('Error al intentar eliminar el producto:', err);
          this.closeModal();
        }
      });
    }
  }
}