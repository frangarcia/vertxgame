var eb = new EventBus(
      window.location.protocol + '//' + window.location.hostname + ':' + window.location.port + '/eventbus');

var team1 = 0;
var team2 = 0;

$( document ).ready(function() {
    $.ajax({
        url: "/api/votes",
        contentType: "application/json",
        dataType: 'json',
        success: function(result){
            team1 = result.team1;
            team2 = result.team2;
            updateChart()
        }
    })
});    



var ctx = document.getElementById('myChart').getContext('2d');
var myChart = new Chart(ctx, {
    type: 'pie',
    data: {
        labels: ['Team1', 'Team2'],
        datasets: [{
            label: '# of Votes',
            data: [team1, team2],
            backgroundColor: [
                'rgba(40, 167, 69, 1)',
                'rgba(220, 53, 69, 1)'
            ],
            borderColor: [
                'rgba(40, 167, 69, 1)',
                'rgba(220, 53, 69, 1)'
            ],
            borderWidth: 1
        }]
    }
});

eb.onopen = function () {
	// listen to draw events
	eb.registerHandler('feedback', function (err, msg) {
    	switch (msg.body.type) {
    		case "team1":team1++;
    					break
    		case "team2":team2++;
                        break
    	}
    	updateChart();
        updateEventList(msg.body.id, msg.body.type);
    });
};

function updateChart() {
	myChart.data.datasets[0].data = [team1, team2];
    myChart.update(0);
}

function updateEventList(id, type){
    $('#eventList').prepend('<li>'+id+' voted for '+type+'</li>');
    $('#lastEvent').html(id+' voted for '+type+' at '+new Date().getTime());
}
