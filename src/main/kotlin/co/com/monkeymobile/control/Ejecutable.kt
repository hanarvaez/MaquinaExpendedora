package co.com.monkeymobile.control

import co.com.monkeymobile.modelo.MaquinaExpendedora
import co.com.monkeymobile.modelo.Producto
import co.com.monkeymobile.vista.Vista
import java.lang.NumberFormatException

class Ejecutable {

    companion object {

        private const val CANTIDAD_INICIAL = 10

        @JvmStatic
        fun main(args: Array<String>) {
            val maquinaExpendedora = llenarMaquinaExpendedora()
            val vista = Vista()
            var continuar = true

            vista.mensajeBienvenida()
            do {
                val seleccion = vista.menuPrincipal()

                when (validarNumeroEntero(seleccion)) {
                    0 -> {
                        vista.mensajeDespedida()
                        continuar = false
                    }

                    1 -> {
                        vista.listarProductos(maquinaExpendedora.productos)
                    }

                    2 -> {
                        val codigoDeProducto = vista.preguntarCodigoDeProducto()
                        val productoBuscado = maquinaExpendedora.buscarProducto(codigoDeProducto)
                        vista.verDetalleDeProducto(productoBuscado)
                    }

                    3 -> {
                        iniciarVenta(vista, maquinaExpendedora)
                    }

                    else -> vista.mensajeOpcionInvalida()
                }
            } while (continuar)
        }

        private fun iniciarVenta(vista: Vista, maquinaExpendedora: MaquinaExpendedora) {
            maquinaExpendedora.iniciarVenta()
            var agregarMasProductos = true
            do {
                val codigoDeProducto = vista.preguntarCodigoDeProducto()
                val cantidad = validarNumeroEntero(vista.preguntarCantidadDeProducto())
                if (cantidad > 0) {
                    val agregadoAlCarrito = maquinaExpendedora.agregarAlCarrito(codigoDeProducto, cantidad)
                    vista.mensajeAgregadoAlCarrito(agregadoAlCarrito)
                } else {
                    vista.mensajeAgregadoAlCarrito(false)
                }

                val seleccion = vista.mensajeAgregarMasProductos()
                when (validarNumeroEntero(seleccion)) {
                    1 -> {
                        // No hacer nada
                    }

                    2 -> {
                        val total = maquinaExpendedora.totalAPagar()
                        if (total == null) {
                            vista.mensajeErrorEnVenta()
                        } else {
                            val cadenaMontoRecibido = vista.mensajeTotalPagar(total)
                            val montoRecibido = validarNumeroEntero(cadenaMontoRecibido)
                            if (montoRecibido >= total) {
                                maquinaExpendedora.descontarProductos()
                                val devuelta = maquinaExpendedora.calcularDevuelta(total, montoRecibido)
                                vista.mensajeDevuelta(devuelta ?: 0)
                            } else {
                                vista.mensajeErrorEnVenta()
                            }
                        }
                        agregarMasProductos = false
                        maquinaExpendedora.finalizarVenta()
                    }

                    else -> vista.mensajeOpcionInvalida()
                }
            } while (agregarMasProductos)
        }

        private fun validarNumeroEntero(valor: String) = try {
            valor.toInt()
        } catch (e: NumberFormatException) {
            -1
        }

        private fun llenarMaquinaExpendedora() = MaquinaExpendedora(
            mutableMapOf(
                Producto("0000", 500, "Bombon", "Delicioso y dulce") to CANTIDAD_INICIAL,
                Producto("0001", 1000, "Chocorramo", "Ponque cubierto de chocolate") to CANTIDAD_INICIAL,
                Producto("0002", 1500, "Salchichas", "Tipo viena") to CANTIDAD_INICIAL,
                Producto("0003", 2000, "Jugo Hit (nara)", "De naranja pina") to CANTIDAD_INICIAL,
                Producto("0004", 2000, "Jugo Hit (mora)", "De mora") to CANTIDAD_INICIAL,
                Producto("0005", 1900, "Submarino", "De fresa") to CANTIDAD_INICIAL,
                Producto("0006", 1000, "Chiclets", "Adams, de menta") to CANTIDAD_INICIAL
            )
        )
    }
}