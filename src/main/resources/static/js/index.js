$(document).ready(function() {

	var tabs = [ 'aorder', 'acustomer', 'apost', 'aitem', 'areport', 'alogout', 'aorderDetail','afeedback' ];
	var ctrls = [ 'orders', 'customers', 'postagents', 'items', 'report', 'logoutPage', 'orderDetails','feedback'];

	var ck = $.cookie("curPage");

	if (ck == null) {
		$('#mcontent').load('dashboard');
		$('#apost').addClass('myactive');
		$.cookie('curPage', ctrls[0]);
		console.log($.cookie('curPage'));
	} else {
		var index = 0;
		for (i = 0; i < ctrls.length; i++) {
			if (ck === ctrls[i]) {
				index = i;
			}
		}
		// load page
		$('#mcontent').load(ck);
		// remove all myactive
		$('nav li').each(function() {
			$(this).removeClass('myactive');
		});
		// add myactive to the selected bar item
		$('#' + tabs[index]).addClass('myactive');
	}

	// sidebar click action
	$('.nav li').click(function() {
		$(this).siblings('li').removeClass('myactive');
		$(this).addClass('myactive');

		switch ($(this).attr('id')) {

		case tabs[0]:
			$('#mcontent').load(ctrls[0]);
			$.cookie('curPage', ctrls[0]);
			break;
		case tabs[1]:
			$('#mcontent').load(ctrls[1]);
			$.cookie('curPage', ctrls[1]);
			break;
		case tabs[2]:
			$('#mcontent').load(ctrls[2]);
			$.cookie('curPage', ctrls[2]);
			break;
		case tabs[3]:
			$('#mcontent').load(ctrls[3]);
			$.cookie('curPage', ctrls[3]);
			break;
		case tabs[4]:
			$('#mcontent').load(ctrls[4]);
			$.cookie('curPage', ctrls[4]);
			break;
		case tabs[5]:
			$('#mcontent').load(ctrls[5]);
			$.cookie('curPage', ctrls[5]);
			break;
		case tabs[6]:
			$('#mcontent').load(ctrls[6]);
			$.cookie('curPage', ctrls[6]);
			break;
		case tabs[7]:
			$('#mcontent').load(ctrls[7]);
			$.cookie('curPage', ctrls[7]);
			break;
		}

	});
	
});