$(document).ready(function() {
	
	// dialog option
	var opt = {
		autoOpen : false,
		modal : true
	};

	$('#removeOrder').dialog(opt);
	$('#removeOrder').dialog('option', 'title', 'Remove Post Agent');
	
	$('#itemsDetail').dialog(opt);
	$('#itemsDetail').dialog('option', 'title', 'Items Detail');
	$('#itemsDetail').dialog('option', 'width', '400');
	$('#itemsDetail').dialog('option', 'height', '400');
	
	// remove button action
	$('#btn_remove').on('click', function() {
		var ids = [];
		$('.scrollTable').find('.id').each(function() {
			if ($(this).is(':checked')) {
				ids.push($(this).val());
			}
		});

		if (ids.length != 0)  {
			console.log(ids);
			$('#removeOrder input[name="orderArray"]').val(ids);
			$('#removeOrder').dialog('open');
		} else {
			infoNotice('Nothing Selected !');
		}
		
	});
	
	var infoNotice = function(msg) {
		$('#infomsg').text(msg);
		$('#infomsg').slideDown();
		$('#infomsg').delay(500).fadeOut('fast');
	};
	
	// show detail of each order
	$('.btn_detail').on('click', function() {
		var $row = $(this).closest('tr');
		
		var curOrderId = $row.find('.id').val();
		
		$.ajax({
			url: '/order/items',
			type: 'post',
			data: {orderId:curOrderId},
			success: function(response) {
				detailView(response);
			}
		});
	});
	
	var detailView = function (data) {
		var items = JSON.parse(data);
		$.each(items, function(i,item) {
			console.log(item);
			$('#dvb').append('<tr><td>'+item.name+'</td><td>'+item.quantity+'</td><td>'+item.price+'</td><td>'+item.price_sold+'</td></tr>');
		});
		$('#itemsDetail').dialog('open');
	};
	
});