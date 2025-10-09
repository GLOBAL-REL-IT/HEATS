var options = {
  chart: {
    height: 300,
    type: 'area',
    stacked: true,
    toolbar: {
      show: false,
    },
    dropShadow: {
      enabled: true,
      opacity: 0.2,
      blur: 5,
      left: -7,
      top: 7
    },
    events: {
      selection: function (chart, e) {
        console.log(new Date(e.xaxis.min))
      }
    },
  },
  dataLabels: {
    enabled: false
  },
  stroke: {
    curve: 'smooth',
    width: 3
  },
  series: [{
    name: 'iPhone',
    data: generateDayWiseTimeSeries(new Date('11 Feb 2019 GMT').getTime(), 20, {
      min: 10,
      max: 60
    })
  }, {
    name: 'Samsung',
    data: generateDayWiseTimeSeries(new Date('11 Feb 2019 GMT').getTime(), 20, {
      min: 10,
      max: 20
    })
  }, {
    name: 'Pixel',
    data: generateDayWiseTimeSeries(new Date('11 Feb 2019 GMT').getTime(), 20, {
      min: 10,
      max: 15
    })
  }],
  colors: ["#4361ee", "#3a86ff", "#38b6ff", "#5e60ce", "#7209b7", "#f72585"],
  fill: {
    type: 'gradient',
    gradient: {
      shadeIntensity: 1,
      opacityFrom: 0.7,
      opacityTo: 0.3,
      stops: [0, 90, 100]
    }
  },
  grid: {
    borderColor: "#e0e6ed",
    strokeDashArray: 5,
    xaxis: {
      lines: {
        show: true
      }
    },
    yaxis: {
      lines: {
        show: false,
      }
    },
    padding: {
      top: 0,
      right: 0,
      bottom: 20,
      left: 30
    },
  },
  legend: {
    position: 'bottom',
    horizontalAlign: 'center'
  },
  xaxis: {
    type: 'datetime',
    labels: {
      style: {
        colors: "#5c6c85",
        fontSize: '12px'
      }
    }
  },
  yaxis: {
    labels: {
      style: {
        colors: "#5c6c85",
        fontSize: '12px'
      }
    }
  },
  tooltip: {
    theme: 'dark',
    x: {
      format: 'dd MMM yyyy'
    }
  }
}

var chart = new ApexCharts(
  document.querySelector("#basic-area-stack-graph"),
  options
);

chart.render();

function generateDayWiseTimeSeries(baseval, count, yrange) {
  var i = 0;
  var series = [];
  while (i < count) {
    var x = baseval;
    var y = Math.floor(Math.random() * (yrange.max - yrange.min + 1)) + yrange.min;

    series.push([x, y]);
    baseval += 86400000;
    i++;
  }
  return series;
}