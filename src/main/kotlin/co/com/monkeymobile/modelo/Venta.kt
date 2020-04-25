package co.com.monkeymobile.modelo

class Venta {

    /**
     * Lista de los productos vendidos. Algo asi como lo que esta en el carrito de compras
     */
    val carritoDeCompras = mutableListOf<Producto>()

    fun agregarProducto(producto: Producto) = carritoDeCompras.add(producto)

    /**
     * Suma el valor de lo que este en el carrito de compras
     */
    fun calcularTotal(): Int {
        var total = 0
        carritoDeCompras.forEach { total += it.precio }
        return total
    }

    fun calcularDevuelta(totalVenta: Int, montoRecibido: Int) = montoRecibido - totalVenta
}

