import { Routes } from '@angular/router';
import { ProductListComponent } from './component/product-list/product-list';
import { ProductFormComponent } from './component/product-form/product-form';

export const routes: Routes = [
  { path: 'product', component: ProductListComponent },
  { path: 'product/new', component: ProductFormComponent },
  { path: 'product/edit/:id', component: ProductFormComponent },
  { path: '', redirectTo: 'product', pathMatch: 'full' },
  { path: '**', redirectTo: 'product' }
];