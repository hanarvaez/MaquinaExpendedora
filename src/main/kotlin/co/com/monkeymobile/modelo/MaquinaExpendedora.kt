package co.com.monkeymobile.modelo

/**
 * Clase de maquina expendedora. Tiene un mapa de productos de la forma <Producto, Cantidad disponible del producto>
 */
class MaquinaExpendedora(val productos: MutableMap<Producto, Int>) {

    private var venta: Venta? = null

    fun iniciarVenta() {
        venta = Venta()
    }

    fun finalizarVenta() {
        venta = null
    }

    /**
     * Agrega la cantidad deseada de productos al carrito de compras
     */
    fun agregarAlCarrito(idProducto: String, cantidadDeseada: Int = 1): Boolean {
        val productoBuscado = buscarProducto(idProducto)
        return if (productoBuscado == null) {
            false
        } else {
            if (hayStock(productoBuscado, cantidadDeseada)) {
                venta?.let {
                    for (i in 1..cantidadDeseada) {
                        it.agregarProducto(productoBuscado)
                    }
                    true
                } ?: false
            } else {
                false
            }
        }
    }

    /**
     * Resta del inventario lo que haya en un carrito de compras ya pago
     */
    fun descontarProductos() {
        venta?.let {venta ->
            venta.carritoDeCompras.forEach { producto ->
                val stock = productos[producto]
                stock?.let { if(it > 0 )productos[producto] = it - 1 }
            }
        }
    }

    fun totalAPagar() = venta?.calcularTotal()

    fun calcularDevuelta(totalVenta: Int, montoRecibido: Int) = venta?.calcularDevuelta(totalVenta, montoRecibido)

    /**
     * Busca un producto con id dado
     * @return el producto con el id que haga match, null en caso contrario
     */
    fun buscarProducto(idProducto: String): Producto? {
        val productoBuscado = Producto(idProducto)
        var resultadoBusqueda: Producto? = null
        productos.keys.forEach { if (it == productoBuscado) resultadoBusqueda = it }
        return resultadoBusqueda
    }

    /**
     * Dice si hay o no existencia de algun producto teniendo en cuenta la cantidad deseada
     * @return true si hay las unidades requeridas del producto, false en caso contrario
     */
    private fun hayStock(producto: Producto, cantidadRequerida: Int = 1) =
        productos[producto]?.let { it >= cantidadRequerida } ?: false
}