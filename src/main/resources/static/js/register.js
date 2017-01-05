$(document).ready(function() {
	$('#userEmail').blur(function() {
		var tmpemail = $('#userEmail').val();
		
		if (tmpemail == null || tmpemail === '') {
			return;
		}
		
		$.ajax({
			url:'/verifyUser',
			type: 'Post',
			data: {userEmail:$('#userEmail').val()},
			dataType: 'text',
			success: function(response){
				console.log(response);
				if (response == 'true') {
					console.log('exist');
					$('#emailSpan').text('Existing Email!');
					$('#fs').attr('disabled', true);
				} else {
					console.log('not exists');
					$('#emailSpan').text('');
					$('#fs').attr('disabled', false);
				}
			}
		});
	});
	
	$('#pswd').keyup(function() {
		var ps = $('#pswd').val();
		var isValid = ps.match(/^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])[0-9a-zA-Z]{6,}$/);
		
		console.log(isValid);
		
		if (isValid == null) {
			$('#pswdSpan').text('At least 6, Upper-Lowercase, no special charater!');
			$('#fs').attr('disabled', true);
		} else {
			$('#pswdSpan').text('');
			$('#fs').attr('disabled', false);
		}
		
		if (ps === '') {
			$('#pswdSpan').text('');
		}
	});
	
	$('#repswd').keyup(function() {
		var ps = $('#pswd').val();
		var reps = $('#repswd').val();
		
		if (ps != reps) {
			$('#repswdSpan').text('Password Not Match!');
			$('#fs').attr('disabled', true);
		} else {
			$('#repswdSpan').text('');
			$('#fs').attr('disabled', false);
		}
	});
	
	
});