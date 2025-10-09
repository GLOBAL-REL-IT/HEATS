var options = {
  chart: {
    height: 268,
    type: 'area',
    toolbar: {
      show: false,
    },
  },
  dropShadow: {
    enabled: true,
    opacity: 0.1,
    blur: 5,
    left: -10,
    top: 10
  },
  dataLabels: {
    enabled: false
  },
  stroke: {
    curve: 'straight',
    width: 3
  },
  series: [{
    name: 'Orders',
    data: [10, 40, 15, 40, 35, 96, 69]
  }],
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
  },
  xaxis: {
    categories: ['Sun', 'Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat'],
  },
  yaxis: {
    labels: {
      show: false,
    }
  },
  colors: ['#334eb4'],
  markers: {
    size: 0,
    opacity: 0.3,
    colors: ['#334eb4'],
    strokeColor: "#ffffff",
    strokeWidth: 2,
    hover: {
      size: 7,
    }
  },
  fill: {
    type: "gradient",
    gradient: {
      type: "vertical",
      shadeIntensity: 1,
      inverseColors: !1,
      opacityFrom: .8,
      opacityTo: .08,
      stops: [50, 100]
    }
  },
  tooltip: {
    y: {
      formatter: function (val) {
        return + val + "k"
      }
    }
  },
}

var chart = new ApexCharts(
  document.querySelector("#graph1"),
  options
);

chart.render();