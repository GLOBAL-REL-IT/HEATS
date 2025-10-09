var options = {
  chart: {
    height: 300,
    type: 'area',
    toolbar: {
      show: false,
    },
    dropShadow: {
      enabled: true,
      opacity: 0.2,
      blur: 5,
      left: -10,
      top: 10
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
    name: 'Unity Admin',
    data: [3100, 4000, 2800, 5100, 4200, 1090, 1000]
  }, {
    name: 'Unity Dashboard',
    data: [1100, 3200, 4500, 3200, 3400, 5200, 4100]
  }],
  colors: ["#3a86ff", "#8338ec"],
  fill: {
    type: 'gradient',
    gradient: {
      shadeIntensity: 1,
      opacityFrom: 0.7,
      opacityTo: 0.2,
      stops: [0, 90, 100]
    }
  },
  grid: {
    borderColor: "#d8dee6",
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
      bottom: 0,
      left: 30
    },
  },
  xaxis: {
    type: 'datetime',
    categories: ["2018-09-19T00:00:00", "2018-09-19T01:30:00", "2018-09-19T02:30:00", "2018-09-19T03:30:00", "2018-09-19T04:30:00", "2018-09-19T05:30:00", "2018-09-19T06:30:00"],
  },
  markers: {
    size: 3,
    opacity: 0.9,
    colors: ["#3a86ff", "#8338ec"],
    strokeColor: "#fff",
    strokeWidth: 2,
    hover: {
      size: 7,
    }
  },
  tooltip: {
    x: {
      format: 'dd/MM/yy'
    },
    theme: 'dark'
  }
}

var chart = new ApexCharts(
  document.querySelector("#basic-area-spline-graph"),
  options
);

chart.render();
