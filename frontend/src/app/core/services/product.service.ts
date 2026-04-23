import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';

export interface TipoProducto {
  id: number;
  nombre: string;
}

export interface Producto {
  id?: number;
  nombre: string;
  descripcion: string;
  precio: number;
  stock: number;
  tipoProductoId: number;
  tipoProductoNombre?: string;
  activo?: boolean;
}

@Injectable({ providedIn: 'root' })
export class ProductService {
  private readonly http = inject(HttpClient);

  getAll(): Observable<Producto[]> {
    return this.http.get<Producto[]>(`${environment.apiUrl}/productos`);
  }

  getById(id: number): Observable<Producto> {
    return this.http.get<Producto>(`${environment.apiUrl}/productos/${id}`);
  }

  create(producto: Producto): Observable<Producto> {
    return this.http.post<Producto>(`${environment.apiUrl}/productos`, producto);
  }

  update(id: number, producto: Producto): Observable<Producto> {
    return this.http.put<Producto>(`${environment.apiUrl}/productos/${id}`, producto);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${environment.apiUrl}/productos/${id}`);
  }

  getTiposProducto(): Observable<TipoProducto[]> {
    return this.http.get<TipoProducto[]>(`${environment.apiUrl}/tipo-producto`);
  }
}
