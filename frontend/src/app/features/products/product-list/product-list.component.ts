import { Component, OnInit, inject, signal } from '@angular/core';
import { RouterLink } from '@angular/router';
import { CommonModule } from '@angular/common';
import { ProductService, Producto } from '../../../core/services/product.service';
import { AuthService } from '../../../core/services/auth.service';

@Component({
  selector: 'app-product-list',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './product-list.component.html',
  styleUrl: './product-list.component.css'
})
export class ProductListComponent implements OnInit {
  private readonly productService = inject(ProductService);
  private readonly authService = inject(AuthService);

  productos = signal<Producto[]>([]);
  loading = signal(false);
  errorMessage = signal('');
  currentUser = this.authService.getCurrentUser();

  ngOnInit(): void {
    this.loadProductos();
  }

  loadProductos(): void {
    this.loading.set(true);
    this.errorMessage.set('');
    this.productService.getAll().subscribe({
      next: data => {
        this.productos.set(data);
        this.loading.set(false);
      },
      error: () => {
        this.errorMessage.set('Error al cargar los productos.');
        this.loading.set(false);
      }
    });
  }

  deleteProducto(id: number): void {
    if (!confirm('¿Está seguro que desea eliminar este producto?')) return;
    this.productService.delete(id).subscribe({
      next: () => this.loadProductos(),
      error: () => this.errorMessage.set('Error al eliminar el producto.')
    });
  }

  logout(): void {
    this.authService.logout();
  }

  formatPrecio(precio: number): string {
    return new Intl.NumberFormat('es-SV', { style: 'currency', currency: 'USD' }).format(precio);
  }
}
