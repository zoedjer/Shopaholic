$(document).ready(function() {

	// dialog option
	var opt = {
		autoOpen : false,
		modal : true
	};

	// init three post form
	$('#createPost').dialog(opt);
	$('#createPost').dialog('option', 'title', 'New Post Agent');

	$('#removePost').dialog(opt);
	$('#removePost').dialog('option', 'title', 'Remove Post Agent');

	$('#editPost').dialog(opt);
	$('#editPost').dialog('option', 'title', 'Edit Post Agent');

	// open new post agent popup
	$('#btn_create').on('click', function() {
		$('#createPost').dialog('open');
	});

	// declare edit form function
	var editPost = function($id, $name, $website) {

		$('#editPost input[name="postId"]').val($id);
		$('#editPost input[name="postName"]').val($name);
		$('#editPost input[name="postWebsite"]').val($website);
		$('#editPost').dialog('open');
	}

	// action performed while clicking sidebar item
	$('.rowbtn').on('click', function() {
		var $row = $(this).closest('tr');
		var curRow = [];

		curRow[0] = $row.find('.id').val();
		curRow[1] = $row.find('.name').text();
		curRow[2] = $row.find('.website').text();

		console.log(curRow);

		editPost(curRow[0], curRow[1], curRow[2]);
	});

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
			$('#removePost input[name="postArray"]').val(ids);
			$('#removePost').dialog('open');
		} else {
			infoNotice('Nothing Selected !');
		}
		
	});
	
	$('#createPostAgentForm').submit(function(e) {
		
		$('#createPostAgentForm').find('input').each(function() {
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