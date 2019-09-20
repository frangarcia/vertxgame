var eb = new EventBus(
      window.location.protocol + '//' + window.location.hostname + ':' + window.location.port + '/eventbus');

var clicksTeam1 = 0;
var clicksTeam2 = 0;

$('#team1').mousedown(function(e){
	sendFeedback('team1');
	$('#clicksTeam1').html(clicksTeam1);
});
$('#team2').mousedown(function(e){
	sendFeedback('team2');
  $('#clicksTeam2').html(clicksTeam2);
});

var id = Math.round($.now() * Math.random()).toString();

$('#userId').html(id);

function sendFeedback(type) {
  if (type=="team1") {
    clicksTeam1++
  } else {
    clicksTeam2++
  }
	eb.publish('feedback', {
	    'id': id,
	    'type': type
	  });
}
