$(document).ready(function() {

	var opt = {
		autoOpen : false,
		modal : true,
		minWidth : 320
	};
	
	$('#createItem').dialog(opt);
	$('#createItem').dialog('option','title','Add New Item');
	
	$('#editItem').dialog(opt);
	$('#editItem').dialog('option','title','Edit Item');
	
	$('#removeItem').dialog(opt);
	$('#removeItem').dialog('option','title','Remove Item');

	$('#btn_create').click(function() {
		$('#createItem').dialog('open');
	});
	
	$('#btn_remove').click(function() {
		var rmArray = [];
		
		$('.scrollTable').find('.id').each(function() {
			
			if ($(this).is(':checked')) {
				rmArray.push($(this).val());
			}
		});
		
		if (rmArray.length != 0) {
			console.log(rmArray);
			$('#removeItem').dialog('open');
		} else {
			infoNotice('Nothing to remove !');
		}
	});
	
	$('.rowbtn').click(function() {
		var $row = $(this).closest('tr');
		
		$('#editItem input[name="id"]').val($row.find('.id').val());
		$('#editItem input[name="name"]').val($row.find('.name').text());
		$('#editItem input[name="category"]').val($row.find('.category').text());
		$('#editItem input[name="price"]').val($row.find('.price').text());
		$('#editItem input[name="brand"]').val($row.find('.brand').text());
		
		$('#editItem').dialog('open');
	});
	
	// form input not null
	$('#createItemForm').submit(function(e) {
		
		$('#createItemForm').find('input').each(function() {
			
			if ($(this).val() == null || $(this).val() == '') {
				e.preventDefault();
			}
		});
	});
	
	
	var infoNotice = function(msg) {
		$('#infomsg').text(msg);
		$('#infomsg').slideDown();
		$('#infomsg').delay(500).fadeOut('fast');
	};
});