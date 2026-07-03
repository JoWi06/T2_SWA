import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { ProductService } from '../../service/product';
import { MaintenanceService } from '../../service/maintenance';
import { Category } from '../../model/category';
import { Family } from '../../model/family';
import { Laboratory } from '../../model/laboratory';

@Component({
  selector: 'app-product-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterModule],
  templateUrl: './product-form.html',
  styleUrls: ['./product-form.css']
})
export class ProductFormComponent implements OnInit {
  productForm!: FormGroup;
  isEditMode = false;
  productId?: number;

  categories: Category[] = [];
  families: Family[] = [];
  laboratories: Laboratory[] = [];

  constructor(
    private fb: FormBuilder,
    private productService: ProductService,
    private maintenanceService: MaintenanceService,
    private router: Router,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.initForm();
    this.loadDropdowns();

    this.productId = this.route.snapshot.params['id'];
    if (this.productId) {
      this.isEditMode = true;
      this.productService.getProduct(this.productId).subscribe({
        next: (prod) => this.productForm.patchValue(prod),
        error: (err) => console.error('Error al recuperar producto para editar:', err)
      });
    }
  }

  initForm(): void {
    this.productForm = this.fb.group({
      idProduct: [null],
      name: ['', [Validators.required, Validators.maxLength(100)]],
      description: ['', Validators.required],
      presentation: ['', Validators.required],
      stock: [0, [Validators.required, Validators.min(0)]],
      unitPrice: [0, [Validators.required, Validators.min(0)]],
      expired: [false, Validators.required],
      category: [null, Validators.required],
      family: [null, Validators.required],
      laboratory: [null, Validators.required]
    });
  }

  loadDropdowns(): void {
    this.maintenanceService.getCategories().subscribe(res => this.categories = res);
    this.maintenanceService.getFamilies().subscribe(res => this.families = res);
    this.maintenanceService.getLaboratories().subscribe(res => this.laboratories = res);
  }

  save(): void {
    if (this.productForm.invalid) return;

    const productData = this.productForm.value;

    if (this.isEditMode && this.productId) {
      this.productService.updateProduct(this.productId, productData).subscribe({
        next: () => this.router.navigate(['/product']),
        error: (err) => console.error(err)
      });
    } else {
      this.productService.createProduct(productData).subscribe({
        next: () => this.router.navigate(['/product']),
        error: (err) => console.error(err)
      });
    }
  }

  compareObjects(o1: any, o2: any): boolean {
    if (!o1 || !o2) return false;
    return o1.idCategory === o2.idCategory || 
           o1.idFamily === o2.idFamily || 
           o1.idLaboratory === o2.idLaboratory;
  }
}