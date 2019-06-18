let datos = null;

$(function () {

    function cargarDatos(id) {

        $.ajax({
            url: '/cargar-facturas/' + id,
            dataType: 'json',
            success: function (data) {
                datos = data;
            }
        });
    }

    function getPath() {
        return $('#url').val();
    }

    let body = $('body');
    $('.eliminar').click(function () {
        return confirm('¿Eliminar?');
    });

    $('#volver').click(function () {
        history.back()
    });
    
    $('#buscar').autocomplete({
        source: function (request, response) {
            $.ajax({
                url: getPath() + '/facturas/cargar-productos/' + request.term,
                datatype: 'json',
                data: {
                    term: request.term
                },
                success: function (data) {
                    response($.map(data, function (item) {
                        return {
                            value: item.nombre,
                            precio: item.precio,
                            id: item.id
                        }
                    }));
                }
            });
        },
        select: function (event, ui) {
            if (Helper.hasProducto(ui.item.id)) {
                Helper.incrementarCantidad(ui.item.id, ui.item.precio);
                return false;
            }
            let linea = $('#plantillaItemsFactura').html();
            linea = linea.replace(/{ID}/g, ui.item.id);
            linea = linea.replace(/{NOMBRE}/g, ui.item.value);
            linea = linea.replace(/{PRECIO}/g, ui.item.precio);
            $('#items tbody').append(linea);
            Helper.calcularImporte(ui.item.id, ui.item.precio, 1);
            Helper.hasFilas();
            $('#buscar').val('');
            return false;
        }
    });

    body.on('input', '.cantidad', function () {
        let id = this.id.replace(/\D/g, '');
        Helper.calcularImporte(id, $('#precio_' + id).text(), this.value)
    });

    body.on('click', '.eliminar_', function () {
        let id = this.id.replace(/\D/g, '');
        Helper.eliminarFila(id);
    });

    body.on('mouseover', '#listar .row_', function () {
        let id = this.id.replace(/\D/g, '');
        cargarDatos(id);
        $("#row_" + id).tooltip({
            content: datos,
            show: true
        });
    })
});
