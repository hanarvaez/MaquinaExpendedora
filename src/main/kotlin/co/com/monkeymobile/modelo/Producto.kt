package co.com.monkeymobile.modelo

data class Producto(
    val id: String,
    val precio: Int = 0,
    val nombre: String? = null,
    val descripcion: String? = null
) {
    override fun equals(other: Any?) = other?.let { if (it is Producto) it.id == id else false } ?: false

    override fun hashCode() = id.hashCode()
}