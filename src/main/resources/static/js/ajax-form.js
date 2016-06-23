function convertirEnAjaxForm(idPopup) {

	$('#' + idPopup + ' .enviar-form').click(function () {
		var currentForm = $(this).closest("form");
		function handleResponse(datos) {
			
			// Si el resultado fue exito, recargamos la página
			if (datos.resultado == 'EXITO') {
				document.location = $('#' + idPopup + ' form').data('url-exito')
					+ '?mensaje=' + datos.mensajeExito;
			}
			// Si fue fracaso, mostramos los mensajes de error.
			else {
				datos.errores.forEach(function (error) {
					console.log(error);
					var field = error.field;
					if (!error.field){
						field = error.arguments[error.arguments.length -1];
					}
					
					currentForm.find('#fg-' + field)
						.addClass('has-error') // Añadimos la clase has-error
						.find('.help-block') // Añadirmos al span help-block el mensaje
						.text(error.defaultMessage);
					
				});
			}
		}	
		
		$('.alert').hide()
		var formData = currentForm.serialize();
		currentForm.find("div[id^=fg-]").removeClass('has-error');
		currentForm.find(".help-block").text('');
		 $.ajax(currentForm.attr('action')
					, {
						data: formData, // Cadena con los datos introducidos
						method: 'POST', // Método esperado en el servidor
						datatType: 'json', // Formato de recepción de los datos
						success: handleResponse // Función que será llamada cuando se complete la llamada al servidor
					});
	});
}
