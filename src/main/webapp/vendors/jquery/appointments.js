/*	$(document).ready(function() { 
		$('#owners').change(
			function() {
				$.getJSON('/petclinic/appointment/pets', {
					ownerID : $(this).val(),
					ajax : 'true'
				}, function(data) {
					var html = '<option value="-1" selected disabled>Select Pet</option>';
					var len = data.length;
					for ( var i = 0; i < len; i++) {
						html += '<option value="' + data[i].id + '">'
								+ data[i].name + '</option>';
					}
					html += '</option>';
	 
					$('#pets').html(html);
				});
			});
	});*/