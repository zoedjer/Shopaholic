// Set the dimensions of the canvas / graph
var margin = {top: 20, right: 20, bottom: 70, left: 40},
    width = 760 - margin.left - margin.right,
    height = 540 - margin.top - margin.bottom;

// Parse the date / time
var parseDate = d3.time.format("%Y-%m-%d").parse;

var x = d3.scale.ordinal().rangeRoundBands([0, width], .05);

var y = d3.scale.linear().range([height, 0]);

var xAxis = d3.svg.axis()
    .scale(x)
    .orient("bottom")
    .tickFormat(d3.time.format("%Y-%m-%d"));

var yAxis = d3.svg.axis()
    .scale(y)
    .orient("left")
    .ticks(10);

// Adds the svg canvas
var svg = d3.select("#graph").append("svg").attr("width",
		width + margin.left + margin.right).attr("height",
		height + margin.top + margin.bottom).append("g").attr("transform",
		"translate(" + margin.left + "," + margin.top + ")");

// Get the data
//d3.json("data", function(error, data) {
	data = JSON.parse(root);

	data.forEach(function(d) {
		d.date = parseDate(d.date);
		d.profit = +d.profit;
	});

	x.domain(data.map(function(d) { return d.date; }));
	y.domain([0, d3.max(data, function(d) { return d.profit; })]);

	svg.append("g")
    	.attr("class", "x axis")
    	.attr("transform", "translate(0," + height + ")")
    	.call(xAxis)
      .selectAll("text")
    	.style("text-anchor", "end")
    	.attr("dx", "-.8em")
    	.attr("dy", "-.55em")
    	.attr("transform", "rotate(-90)" );

	svg.append("g")
	    .attr("class", "y axis")
	    .call(yAxis)
	  .append("text")
	    .attr("transform", "rotate(-90)")
	    .attr("y", 6)
	    .attr("dy", ".71em")
	    .style("text-anchor", "end")
	    .text("Profit (€)");

	svg.selectAll("bar")
	    .data(data)
	  .enter().append("rect")
	    .style("fill", "steelblue")
	    .attr("x", function(d) { return x(d.date); })
	    .attr("width", x.rangeBand())
	    .attr("y", function(d) { return y(d.profit); })
	    .attr("height", function(d) { return height - y(d.profit); });
	
	svg.selectAll("bar")
    	.data(data)
      .enter().append("text")
      	.text(function(d) {return d.profit})
      	.attr("x", function(d) { return x(d.date); })
	    .attr("width", x.rangeBand())
	    .attr("y", function(d) { return y(d.profit); })
	    .attr("height", function(d) { return height - y(d.profit); });
      	

//});