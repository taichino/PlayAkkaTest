var util = require('util'),
	request = require('request-promise'),
	express = require('express');

var app = express();

app.get('/github/stats', function(req, res) {
	var reqs = req.query.repos.split(',').map(function(repo) {
		var opts = {
			uri: util.format('https://api.github.com/repos/iheartradio/%s/languages', repo),
			headers: {
				'User-Agent': 'demoapp',
				'Authorization': 'token bc030cfc8d83f80bc0316912928840a84328f3d4'
			},
			json: true
		};
		return request(opts); // this is promiss
	});
	
	Promise.all(reqs)
		.then(function(results) {
			var total = {};
			results.map(function(stats) {
				Object.keys(stats).map(function(key) {
					total[key] = total[key] + stats[key] || stats[key];    // Note: undefined + number = NaN
				});
			});
			res.send(total);
		})
		.catch(function(err) {
			res.send(err);
		});
});

app.listen(3000, function() {
	console.log('app started');
});
