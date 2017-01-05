$(document).ready(function() {
	
	// dialog option
	var opt = {
		autoOpen : false,
		modal : true
	};

	// init three post form
	$('#createCustomerDulp').dialog(opt);
	$('#createCustomerDulp').dialog('option', 'title', 'New Customer');
	
	$('#createItemDulp').dialog(opt);
	$('#createItemDulp').dialog('option', 'title', 'New item');
	
	$('#createPostDulp').dialog(opt);
	$('#createPostDulp').dialog('option', 'title', 'New Post Agent');
	
	$('#btn_dulp_customer').click(function() {
		$('#createCustomerDulp').dialog('open');
	});
	
	$('#btn_dulp_item').click(function() {
		$('#createItemDulp').dialog('open');
	});

	$('#btn_dulp_post').click(function() {
		$('#createPostDulp').dialog('open');
	});
	
	// drag and drop
	$('.mycard').draggable({
		cursor : "copy",
		helper : "clone"
	});

	$('#resultPad').droppable({
		drop : function(event, ui) {
			var cid = ui.draggable.attr('id');
			
			var customerStr = 'customer';
			var postAgentStr = 'pa';
			var itemStr = 'item';
			
			$('.mycard').each(function() {
				if ($(this).attr('id') === cid) {
					
					// order customer
					if ($('.selcuscard').length ==0 ) {
					
						var cln = $(this).clone().removeClass('mycard');
					
						if (cid.indexOf(customerStr) !== -1) {
							cln.addClass('selcuscard');

							cln.append('<input class="genBtnRm" type="button" value="remove" />');
							cln.appendTo('#resultPad');
						}
					}
					
					// order post_agent
					if ($('.selpacard').length == 0) {
						
						var cln = $(this).clone().removeClass('mycard');
					
						if (cid.indexOf(postAgentStr) !== -1) {
							cln.addClass('selpacard');
							cln.append('<span>Shipping Fee: <input type="text" id="shipping_fee" style="width:70px" />€</span>');
							cln.append('<input class="genBtnRm" type="button" value="remove" />');
							cln.appendTo('#resultPad');
						}
					}
					
					// order item
					var itemArr = [];
					
					$('.selitcard').each(function() {
						
						if (jQuery.inArray(this.id, itemArr) == -1) {
							itemArr.push(this.id);
						}
					});
					
					if (jQuery.inArray($(this).attr('id'), itemArr) == -1) {
						
						var cln = $(this).clone().removeClass('mycard');
						
						var price = $(this).find('.it-price').text();
						
						if (cid.indexOf(itemStr) !== -1) {
							cln.addClass('selitcard');
							cln.append('<span>Quantity:&nbsp;</span>');
							cln.append('<span><input class="it_inp item-num" type="text" value="1"/></span>');
							cln.append('<span><input id="it_add" value="+" type="button"/></span>');
							cln.append('<span><input id="it_rm" value="-" type="button"/></span>');
							cln.append('<span>Original Price: </span><input id="it_sold" value="'+ price +'" class="it_inp" type="text" />');
							cln.append('<span>€</span>');
							cln.append('<input class="genBtnRm" type="button" value="remove" />');
							cln.prependTo('#resultPad');
						}
					}
				}
			});
		}
	});
	
	
	// remove select div itself
	$('#resultPad').on('click','.genBtnRm',function() {
		$(this).parent().remove();
	});
	
	$('#resultPad').on('click','#it_add',function() {
		
		var $quantitybox = $(this).parent().parent().find('input.item-num:text');
		var temp = parseInt($quantitybox.val());
		temp = temp + 1;
		$quantitybox.val(temp);
		console.log($quantitybox.val());
	});
	
	$('#resultPad').on('click','#it_rm',function() {
		
		var $quantitybox = $(this).parent().parent().find('input.item-num:text');
		var temp = parseInt($quantitybox.val());
		if (temp > 1) {
			temp = temp - 1;
		}
		$quantitybox.val(temp);
		console.log($quantitybox.val());
	});
	
	// order submit
	$('#btn_submit').click(function(e) {
		var ucard = $('.selcuscard').length;
		var itcard = $('.selitcard').length;
		var pacard = $('.selpacard').length;
		
		var obj = {
				"customer":{},
				"postAgent":{},
				"items":[]
		};
		
		var ordcus = $('.selcuscard').attr('id').replace('customer','');
		var ordpa = $('.selpacard').attr('id').replace('pa','');
		var trackNum = $('#track_num').val();
		var shippingFee = $('#shipping_fee').val();
		
		obj.customer = {id:ordcus};
		obj.postAgent = {id:ordpa,trackNum:trackNum,shipping_fee:shippingFee};
		
		$('.selitcard').each(function() {
			var tid = $(this).attr('id').replace('item','');
			var tquantity=$(this).find('input.item-num:text').val();
			var tprice=$(this).find('.it-price').text();
			var tsold=$(this).find('#it_sold').val();
			
			obj.items.push({
				id:tid,
				quantity:tquantity,
				price:tprice,
				price_sold:tsold
				
			});
			
		});
		
		if (Object.keys(obj.customer).length === 0 || Object.keys(obj.postAgent).length ===0
				|| obj.items.length === 0 || trackNum.length === 0 || shippingFee.length === 0) {
			return 0;
		}
		
		var jsonStr = JSON.stringify(obj);
		
		console.log(jsonStr);	
		$.ajax({
			url: "http://sample-env-1.qy4cf6pmpk.eu-west-1.elasticbeanstalk.com/agent/data",
			type: "post",
			contentType: "application/text; charset=utf-8",
		    dataType: "text",
			data: jsonStr,
			success: function(msg) {
				console.log('success');
			},
			error: function(xhr, textStatus, error) {
				console.log('error: ' + xhr.statusText + ';' + textStatus + ';' + error);
			}
		});
		clearResultPad();
	});
	
	$('#btn').click(function() {

		var inval = $('#tags').val();
		inval = "";

		$.ajax({
			url : "http://sample-env-1.qy4cf6pmpk.eu-west-1.elasticbeanstalk.com/agent/data",
			type : "get",
			dataType : "json",
			data : "",
			success : function(msg) {
				var ava = msg;
				$('#myview').empty();
				for (index = 0; index < ava.length; index++) {
					$('#myview').append('<li>' + ava[index] + '</li>');
				}

			},
			error : function(xhr, ajaxOptions, thrownError) {
				alert(xhr.status);
			}
		});
	});
	
	var clearResultPad = function() {
		$('div').remove('.selcuscard');
		$('div').remove('.selpacard');
		$('div').remove('.selitcard');
		$('#track_num').val('');
	};

});