class Helper {

    static calcularImporte(id, precio, cantidad) {
        $('#importe_' + id).text(parseFloat(precio) * cantidad);
        this.obtenerTotal();
    }

    static hasProducto(id) {
        let resultado = false;
        $('input[name="item_id[]"]').each(function () {
            if (parseInt(id) === parseInt($(this).val())) resultado = true;
        });
        return resultado;
    }

    static incrementarCantidad(id, precio) {
        let input = $('#cantidad_' + id);
        let cantidad = input.val() ? parseInt(input.val()) : 0;
        input.val(++cantidad);
        this.calcularImporte(id, precio, cantidad);
    }

    static eliminarFila(id) {
        $('#row_' + id).remove();
        this.obtenerTotal();
        this.hasFilas();
    }

    static obtenerTotal() {
        let total = 0;
        $('.row_ .importe').each(function () {
            if (!this.id.includes('ID')) {
                total += parseFloat($(this).text());
            }
        });
        $('#total').text(total);
    }

    static hasFilas() {
        if ($('.row_').length > 1) {
            $('#pedro').removeAttr('disabled');
        } else {
            $('#pedro').prop('disabled', 'disabled');
        }
    }
}
