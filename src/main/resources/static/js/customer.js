$(document).ready(function() {
	// dialog option
	var opt = {
		autoOpen : false,
		modal : true,
		minWidth: 320
	};

	// init three Customer form
	$('#createCustomer').dialog(opt);
	$('#createCustomer').dialog('option', 'title', 'New Customer');

	$('#editCustomer').dialog(opt);
	$('#editCustomer').dialog('option', 'title', 'Edit Customer');
	
	$('#removeCustomer').dialog(opt);
	$('#removeCustomer').dialog('option', 'title', 'Remove Customer');

	$('#addAddress').dialog(opt);
	$('#addAddress').dialog('option', 'title', 'Add Address');
	
	// create button action
	$('#btn_create').click(function() {
		$('#createCustomer').dialog('open');
	});
	
	// edit button action
	$('.rowbtn').click(function() {
		var $row = $(this).closest('tr');
		console.log($row.find('.id').val());
		
		var names = $row.find('.firstLastName').text().split(' ');
		var lines = $row.find('.lineOneTwo').text().split(' ');
		
		$('#editCustomer input[name="id"]').val($row.find('.id').val());
		$('#editCustomer input[name="firstName"]').val(names[0]);
		$('#editCustomer input[name="lastName"]').val(names[1]);
		$('#editCustomer input[name="phone"]').val($row.find('.phone').text());
		$('#editCustomer input[name="email"]').val($row.find('.email').text());
		$('#editCustomer input[name="lineOne"]').val(lines[0]);
		$('#editCustomer input[name="lineTwo"]').val(lines[1]);
		$('#editCustomer input[name="city"]').val($row.find('.city').text());
		$('#editCustomer input[name="county"]').val($row.find('.county').text());
		$('#editCustomer input[name="country"]').val($row.find('.country').text());
		
		$('#editCustomer').dialog('open');
	});
	
	// remove button action
	$('#btn_remove').click(function() {
		var ids = [];
		$('.id').each(function() {
			if ($(this).is(':checked')) {
				ids.push($(this).val());
			}
		});
		
		if (ids.length != 0 ) {
			$('#removeCustomer input[name="customerArray"]').val(ids);
			$('#removeCustomer').dialog('open');
		} else {
			infoNotice('No customer to Remove !');
		}
		
	});
	
	// add address button action
	$('#btn_addr').click(function() {
		var ids = [];
		$('.id').each(function(){
			if($(this).is(':checked')) {
				ids.push($(this).val());
			}
		});
		
		if (ids.length != 0) {
			$('#addAddress input[name="customerId"]').val(ids[0]);
			console.log($('#addAddress input[name="customerId"]').val());
			$('#addAddress').dialog('open');
		} else {
			infoNotice('No customer selected !');
		}
	});
	
	// create form handle
	$('#createCustomerForm').submit(function(e) {
		
		$('#createCustomerForm').find('input').each(function() {
			if ($.trim($(this).val()) === '' || $.trim($(this).val()) == null) {
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