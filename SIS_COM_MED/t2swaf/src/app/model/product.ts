import { Category } from './category';
import { Family } from './family';
import { Laboratory } from './laboratory';

export interface Product {
  idProduct?: number;
  name: string;
  description: string;
  presentation: string;
  unitPrice: number;
  stock: number;
  expired: boolean;
  category: Category;
  family: Family;
  laboratory: Laboratory;
}