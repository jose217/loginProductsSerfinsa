import { Component, OnInit, inject } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { CommonModule } from '@angular/common';
import { ProductService, Producto, TipoProducto } from '../../../core/services/product.service';

@Component({
  selector: 'app-product-form',
  standalone: true,
  imports: [ReactiveFormsModule, CommonModule, RouterLink],
  templateUrl: './product-form.component.html',
  styleUrl: './product-form.component.css'
})
export class ProductFormComponent implements OnInit {
  private readonly fb = inject(FormBuilder);
  private readonly productService = inject(ProductService);
  private readonly router = inject(Router);
  private readonly route = inject(ActivatedRoute);

  productoForm: FormGroup = this.fb.group({
    nombre: ['', [Validators.required, Validators.minLength(2), Validators.maxLength(150)]],
    descripcion: ['', [Validators.maxLength(500)]],
    precio: [null, [Validators.required, Validators.min(0)]],
    stock: [null, [Validators.required, Validators.min(0)]],
    tipoProductoId: [null, Validators.required]
  });

  tiposProducto: TipoProducto[] = [];
  isEditMode = false;
  productoId: number | null = null;
  loading = false;
  errorMessage = '';

  get nombreControl() { return this.productoForm.get('nombre')!; }
  get precioControl() { return this.productoForm.get('precio')!; }
  get stockControl() { return this.productoForm.get('stock')!; }
  get tipoControl() { return this.productoForm.get('tipoProductoId')!; }

  ngOnInit(): void {
    this.loadTipos();
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.isEditMode = true;
      this.productoId = +id;
      this.loadProducto(this.productoId);
    }
  }

  loadTipos(): void {
    this.productService.getTiposProducto().subscribe({
      next: tipos => this.tiposProducto = tipos,
      error: () => this.errorMessage = 'Error al cargar los tipos de producto.'
    });
  }

  loadProducto(id: number): void {
    this.loading = true;
    this.productService.getById(id).subscribe({
      next: producto => {
        this.productoForm.patchValue(producto);
        this.loading = false;
      },
      error: () => {
        this.errorMessage = 'Error al cargar el producto.';
        this.loading = false;
      }
    });
  }

  onSubmit(): void {
    if (this.productoForm.invalid) {
      this.productoForm.markAllAsTouched();
      return;
    }
    this.loading = true;
    this.errorMessage = '';
    const producto: Producto = this.productoForm.value;

    const request = this.isEditMode && this.productoId
      ? this.productService.update(this.productoId, producto)
      : this.productService.create(producto);

    request.subscribe({
      next: () => {
        this.loading = false;
        this.router.navigate(['/products']);
      },
      error: () => {
        this.errorMessage = 'Error al guardar el producto. Verifique los datos.';
        this.loading = false;
      }
    });
  }
}
