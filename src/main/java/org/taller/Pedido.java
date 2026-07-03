package org.taller;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.taller.MetodosPago.PAYPAL;
import static org.taller.MetodosPago.TARGETA_DE_CREDITO;
import static org.taller.Producto.getListaProductos;
import static org.taller.Producto.getNombreProductos;
import static org.taller.Respuesta.NO;
import static org.taller.Respuesta.SI;


public class Pedido {
    private List<Producto> pedidos = new ArrayList<>();
    private static List<Pedido> pedidosPendientes = new ArrayList<>();
    private MetodosPago metodoDePago;
    private String estadoDePedido = "Pendiente";
    private int totalPedido;

    public Pedido() {

    }

    public Pedido(List<Producto> pedidos, MetodosPago metodoDePago) {
        this.pedidos = pedidos;
        this.metodoDePago = metodoDePago;
    }

    public List<Producto> getPedidos() {
        return pedidos;
    }

    public void setEstadoDePedido(String estado) {
        this.estadoDePedido = estado;
    }

    public int getTotalPedido() {
        return totalPedido;
    }

    public  static List<Pedido> getPedidosPendientes() {
        return pedidosPendientes;
    }

    public  void agregarProductoAFactura() {
        Respuesta respuestaUsuario = SI;
        Scanner entrada = new Scanner(System.in);
        int posicion;
        do {
            posicion = 0;
            if (respuestaUsuario == SI) {
               Producto.mostrarListaProductos();
                System.out.println("Ingrese el nombre del producto a añadir en el pedido:");
                String producto = entrada.nextLine();

                for (String nombre : getNombreProductos()) {

                    if (producto.equalsIgnoreCase(nombre)) {
                        pedidos.add(getListaProductos().get(posicion));
                    }
                    posicion++;
                }
            } do {
                System.out.println("¿Desea ingresar otro producto a su pedido? SI/NO");
                respuestaUsuario = Respuesta.valueOf(entrada.nextLine());

                if(respuestaUsuario != SI && respuestaUsuario != NO){
                    System.out.println("Respuesta invalida. por favor intente de nuevo");
                }
            }while( respuestaUsuario != SI && respuestaUsuario != NO);

        } while (respuestaUsuario == Respuesta.SI);
        finalizarCompra();
    }

    public void finalizarCompra() {
        Scanner entrada = new Scanner(System.in);
        System.out.println("\n--- Factura---");
        for (Producto factura : pedidos) {
            System.out.println("Producto:" + factura.getNombre() + " Precio:$" + factura.getPrecio());
        }

        int total = calcularTotal();
        System.out.println("El valor total del pedido es de:" + total);

        System.out.println("Elija el metodo de pago: (TARGETA DE CREDITO / PAYPAL)");
        metodoDePago = MetodosPago.valueOf(entrada.nextLine());
        while(metodoDePago != TARGETA_DE_CREDITO && metodoDePago != PAYPAL ){
            System.out.println("Error: Escribe la opción correctamente (TARGETA DE CREDITO / PAYPAL)");
            metodoDePago = MetodosPago.valueOf(entrada.nextLine());
        }

        if (metodoDePago == metodoDePago.PAYPAL) {
            metodoDePago.PAYPAL.pagoPaypal();
            System.out.println("¿Desea aplicar un descuento?");
            Respuesta respuestaUsuario = Respuesta.valueOf(entrada.nextLine());
            if (respuestaUsuario == SI) {
                System.out.println("1:Descuento porcentual \n 2:Descuento Parcial");
                int numero = entrada.nextInt();
                if (numero == 1) {
                    DescuentoPorcentual desc = new DescuentoPorcentual();
                    int precioFinal = desc.aplicarDescuento(new Pedido(new ArrayList<>(pedidos), metodoDePago));
                    this.totalPedido = precioFinal;
                } else if (numero == 2) {
                    DescuentoParcial desc = new DescuentoParcial();
                    int precioFinal = desc.aplicarDescuento(new Pedido(new ArrayList<>(pedidos), metodoDePago));
                    this.totalPedido = precioFinal;
                }
            }
        } else {
            TARGETA_DE_CREDITO.pagoTargeta();
            System.out.println("¿Desea aplicar un descuento?");
            Respuesta respuestaUsuario = Respuesta.valueOf(entrada.nextLine());
            if (respuestaUsuario == SI) {
                System.out.println("1:Descuento porcentual \n 2:Descuento Parcial");
                int numero = entrada.nextInt();
                if (numero == 1) {
                    DescuentoPorcentual desc = new DescuentoPorcentual();
                    int precioFinal = desc.aplicarDescuento(new Pedido(new ArrayList<>(pedidos), metodoDePago));
                    this.totalPedido = precioFinal;
                } else if (numero == 2) {
                    DescuentoParcial desc = new DescuentoParcial();
                    int precioFinal = desc.aplicarDescuento(new Pedido(new ArrayList<>(pedidos), metodoDePago));
                    this.totalPedido = precioFinal;
                }
            }

        }
        pedidosPendientes.add(new Pedido(new ArrayList<>(pedidos), metodoDePago));

    }

    public int calcularTotal() {
        int total = 0;
        for (Producto precio : pedidos) {
            total += precio.getPrecio();
        }
        this.totalPedido = total;
        return total;
    }
}
