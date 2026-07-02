package org.taller;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Producto{

    private String nombre;
    private  int precio;
    private static List<String> NombreProductos = new ArrayList<>();
    private static List<Producto> objetoProducto = new ArrayList<>();


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    public List<String> getNombreProductos() {
        return NombreProductos;
    }

    public void setProducto(List<String> producto) {
        this.NombreProductos = NombreProductos;
    }

    public Producto(String nombre, int precio ) {
        this.nombre = nombre;
        this.precio = precio;

    }

    public static void agregarProducto(){

        Scanner sc = new Scanner(System.in);
        System.out.println(" Ingrese el nombre del nuevo producto");
        String nombreProducto = sc.nextLine();

        for(String producto : NombreProductos) {
            while (nombreProducto.equalsIgnoreCase(producto)) {
                System.out.println("Este nombre ya esta en uso, intente de nuevo");
                nombreProducto = sc.nextLine();

                if (!nombreProducto.equalsIgnoreCase(producto)) break;
            }
        }

        System.out.println("Ingrese el precio del nuevo producto");
        int precio= sc.nextInt();

        System.out.println("Si desea cambiar el precio presione 1, de lo contrario presione 2 ");
        int cambioPrecio = sc.nextInt();

        if(cambioPrecio == 1){
            System.out.println("Ingrese el precio del producto");
            precio = sc.nextInt();
        }

        objetoProducto.add(new Producto(nombreProducto, precio));
        NombreProductos.add(nombreProducto);


    }
}


