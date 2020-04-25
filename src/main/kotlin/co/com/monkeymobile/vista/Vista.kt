package co.com.monkeymobile.vista

import co.com.monkeymobile.modelo.Producto
import java.util.Scanner

class Vista {

    private val lectorCadenas = Scanner(System.`in`)

    fun mensajeBienvenida() = println("********** BIENVENIDO **********")

    fun menuPrincipal(): String {
        println(
            "Digite la opción que desea ejecutar:\n" +
                    "1. Ver productos disponibles\n" +
                    "2. Ver detalle de un producto\n" +
                    "3. Iniciar una compra\n" +
                    "0. Salir"
        )
        return read()
    }

    fun preguntarCodigoDeProducto(): String {
        println("Digite el codigo del producto")
        return read()
    }

    fun preguntarCantidadDeProducto(): String {
        println("Digite las unidades deseadas del producto que desea comprar")
        return read()
    }

    /**
     * Retorna en forma de texto los productos disponibles de la maquina
     */
    fun listarProductos(productos: MutableMap<Producto, Int>) {
        var salida = ""
        if (productos.isEmpty()) {
            salida = "La maquina esta vacia"
        } else {
            salida += "Productos disponibles:\n"
            productos.forEach {
                salida += "id: ${it.key.id} | nombre: ${it.key.nombre}  | precio: ${it.key.precio}  | disponibles: ${it.value}\n"
            }
            salida += "\n"
        }
        println(salida)
    }

    fun verDetalleDeProducto(producto: Producto?) {
        val salida = producto?.let { "nombre: ${it.nombre} | descripcion: ${it.descripcion} | precio: ${it.precio}\n" }
            ?: "El producto ingresado no existe\n"
        println(salida)
    }

    fun mensajeAgregarMasProductos(): String {
        println(
            "Desea agregar otro producto?\n" +
                    "1. Si\n" +
                    "2. No\n"
        )
        return read()
    }

    fun mensajeAgregadoAlCarrito(agregado: Boolean) =
        println("El producto ${if (agregado) "" else "*** NO *** "}ha sido agregado al carrito\n")

    fun mensajeTotalPagar(valor: Int): String {
        println(
            "Su valor total a pagar es $valor\n" +
                    "Ingrese el monto con el que cancelara:"
        )
        return read()
    }

    fun mensajeDevuelta(devuelta: Int) = println(
        "Su devuelta es $devuelta\n" +
                "Gracias por su compra!\n\n"
    )

    fun mensajeOpcionInvalida() = println("La opcion ingresada no es valida\n")

    fun mensajeErrorInventario() = println("No hay suficientes unidades del producto. No se puede efectuar la venta\n")

    fun mensajeErrorEnVenta() = println("Ha ocurrido un error, transaccion abortada\n")

    fun mensajeDespedida() = println("Gracias por usar la aplicacion, vuelva pronto!\n")

    private fun read() = lectorCadenas.nextLine().trim()
}