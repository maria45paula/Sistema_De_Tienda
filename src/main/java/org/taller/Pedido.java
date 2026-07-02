package org.taller;

import java.util.List;
import java.util.Scanner;

import static org.taller.Producto.getListaProductos;
import static org.taller.Producto.getNombreProductos;

public class Pedido {
    private List<Producto> pedidos;

    public Pedido(Producto producto) {
        this.pedidos.add(producto);
    }

    public void agregarProductoAFactura(Respuesta respuestaUsuario) {
        Scanner entrada = new Scanner(System.in);
        int posicion = 0;

        if (respuestaUsuario == Respuesta.SI) {
            System.out.println("\n--- Lista de Productos ---");
            for (Producto prod : getListaProductos()) {
                System.out.println("Producto: " + prod.getNombre() + " | Precio: $" + prod.getPrecio());
            }
            System.out.println("Ingrese el producto a añadir en el pedido:");
            String producto = entrada.nextLine();

            for (String nombre : getNombreProductos()) {

                if (producto.equalsIgnoreCase(nombre)) {
                    pedidos.add(getListaProductos().get(posicion));
                }
                posicion++;
            }
        } else if (respuestaUsuario == Respuesta.NO) {
            finalizarCompra();
        }
    }

    public static void finalizarCompra() {

    }
}
