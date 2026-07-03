package org.taller;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.taller.Producto.getListaProductos;
import static org.taller.Producto.getNombreProductos;
import static org.taller.Respuesta.SI;


public class Pedido {
    private List<Producto> pedidos = new ArrayList<>();
    private List<Pedido> pedidosPendientes = new ArrayList<>();
    private MetodosPago metodoDePago;
    private String estadoDePedido = "Pendiente";

    public Pedido(List<Producto> pedidos) {
        this.pedidos = pedidos;
    }

    public void agregarProductoAFactura() {
        Respuesta respuestaUsuario = SI;
        Scanner entrada = new Scanner(System.in);
        int posicion;
        do {
            posicion = 0;
            if (respuestaUsuario == SI) {
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
            }
            System.out.println("¿Desea ingresar otro producto a su pedido?");
            respuestaUsuario = Respuesta.valueOf(entrada.nextLine());
        } while (respuestaUsuario == Respuesta.SI);
        finalizarCompra();
    }

    public void finalizarCompra() {
        Scanner entrada = new Scanner(System.in);
        System.out.println("\n--- Factura---");
        for (Producto factura : pedidos) {
            System.out.println("Producto:" + factura.getNombre() + " Precio:$" + factura.getPrecio());
        }

        System.out.println("Elija el metodo de pago:");
        metodoDePago = MetodosPago.valueOf(entrada.nextLine());
        pedidosPendientes.add(new Pedido(new ArrayList<>(pedidos)));

    }

    public int calcularTotal(){
        int total = 0;
        for(Producto precio : pedidos){
            total+= precio.getPrecio();
        }
        return total;
    }
}
