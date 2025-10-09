// Option 1
var options1 = {
  series: [{
    name: 'Litecoin',
    data: [2, 4, 3, 7, 9, 8, 9, 10, 8, 11, 12, 14, 12, 13, 15, 14, 16, 18, 15, 17, 14, 13, 19, 16, 17, 21, 20, 23, 22, 25, 27, 26]
  }],
  chart: {
    type: 'area',
    height: 120,
    sparkline: {
      enabled: true
    }
  },
  colors: ['#ac5cdd'],
  stroke: {
    curve: 'straight',
    width: 1,
  },
  fill: {
    type: "gradient",
    gradient: {
      type: "vertical",
      shadeIntensity: 1,
      inverseColors: !1,
      opacityFrom: .3,
      opacityTo: .07,
      stops: [15, 90]
    }
  },
  tooltip: {
    y: {
      formatter: function (val) {
        return "$" + val
      }
    }
  },
};

var chart1 = new ApexCharts(document.querySelector("#sparklineLine1"), options1);
chart1.render();


// Option 2
var options2 = {
  series: [{
    name: 'Ethereum',
    data: [2, 4, 3, 7, 9, 8, 9, 10, 8, 11, 12, 14, 12, 13, 15, 14, 16, 18, 15, 17, 14, 13, 19, 16, 17, 21, 20, 23, 22, 25, 27, 26]
  }],
  chart: {
    type: 'area',
    height: 120,
    sparkline: {
      enabled: true
    }
  },
  colors: ['#57c012'],
  stroke: {
    curve: 'straight',
    width: 1,
  },
  fill: {
    type: "gradient",
    gradient: {
      type: "vertical",
      shadeIntensity: 1,
      inverseColors: !1,
      opacityFrom: .3,
      opacityTo: .07,
      stops: [15, 90]
    }
  },
  tooltip: {
    y: {
      formatter: function (val) {
        return "$" + val
      }
    }
  },
};

var chart2 = new ApexCharts(document.querySelector("#sparklineLine2"), options2);
chart2.render();

// Option 3
var options3 = {
  series: [{
    name: 'Avalanche',
    data: [2, 4, 3, 7, 9, 8, 9, 10, 8, 11, 12, 14, 12, 13, 15, 14, 16, 18, 15, 17, 14, 13, 19, 16, 17, 21, 20, 23, 22, 25, 27, 26]
  }],
  chart: {
    type: 'area',
    height: 120,
    sparkline: {
      enabled: true
    }
  },
  colors: ['#a0323d'],
  stroke: {
    curve: 'straight',
    width: 1,
  },
  fill: {
    type: "gradient",
    gradient: {
      type: "vertical",
      shadeIntensity: 1,
      inverseColors: !1,
      opacityFrom: .3,
      opacityTo: .07,
      stops: [15, 90]
    }
  },
  tooltip: {
    y: {
      formatter: function (val) {
        return "$" + val
      }
    }
  },
};

var chart3 = new ApexCharts(document.querySelector("#sparklineLine3"), options3);
chart3.render();